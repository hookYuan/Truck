package com.yuan.map.citylist.module;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.yuan.basemodule.common.other.RxUtil;
import com.yuan.map.R;
import com.yuan.map.citylist.util.CharacterParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/8/1.
 * 读取解析本地XMl中city数据，根据city数据生成city集合
 * 在这里可以更改数据源，通过回调回传数据
 */
public class CityManager {
    private ArrayList<CityBean> list;
    ICityReadyListener cityReadyListener;//通知数据准备完毕
    private Context context;

    public CityManager(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    private void parseXML() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.citys);
        // 利用ANDROID提供的API快速获得pull解析器
        XmlPullParser pullParser = Xml.newPullParser();
        // 设置需要解析的XML数据
        try {
            pullParser.setInput(inputStream, "UTF-8");
            // 取得事件
            int event = pullParser.getEventType();
            // 若为解析到末尾
            while (event != XmlPullParser.END_DOCUMENT) // 文档结束
            {
                String nodeName = pullParser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT: // 文档开始

                        break;
                    case XmlPullParser.START_TAG: // 标签开始
                        if ("area".equals(nodeName)) {
                            String city = pullParser.getAttributeValue(0);
                            String code = pullParser.getAttributeValue(1);
                            list.add(new CityBean(city, code));
                        }
                        break;
                }
                event = pullParser.next(); // 下一个标签
            }
        } catch (XmlPullParserException e) {
            Log.i("manager", "xml解析异常" + e.getMessage());
        } catch (IOException e) {

        }
    }

    /**
     * 用比较器先对原始数据进行排序
     */
    private void character() {
        //给Data排序
        //将汉字转化成拼
        for (int i = 0; i < list.size(); i++) {
            String s1 = CharacterParser.getInstance().getSelling(list.get(i).getName());
            if (s1.length() != 0) {
                list.get(i).setSortLetters(s1.charAt(0) + "");
                list.get(i).setPinyin(s1);
            }
        }
        PinyinComparator pinyinComparator = new PinyinComparator();
        Collections.sort(list, pinyinComparator);
    }

    public void setCityReadyListener(ICityReadyListener mCityReadyListener) {
        this.cityReadyListener = mCityReadyListener;
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                //异步解析XML文件
                parseXML();
                character();
                e.onNext(1);
            }
        }).compose(RxUtil.<Integer>io_main())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        cityReadyListener.onCityReadyListener(list);
                    }
                });
    }

    public interface ICityReadyListener {
        void onCityReadyListener(ArrayList<CityBean> list);
    }
}

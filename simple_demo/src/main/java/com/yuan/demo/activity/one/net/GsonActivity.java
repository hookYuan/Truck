package com.yuan.demo.activity.one.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GsonActivity extends MVPActivity implements ISwipeBack {


    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;

    NetBean<DateBean> data;
    NetBean<List<DateBean>> dataList;

    @Override
    protected void initData(Bundle savedInstanceState) {
        getTitleBar().setToolbar("Gson解析")
                .setLeftIcon(R.drawable.ic_base_back_white);

        //字符串
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"这是一条消息\",\n" +
                        "    \"data\": \"data是一个字符串\"\n" +
                        "}";
//                NetBean<String> data = new ParseUtil<String>(json1).jsonParse(json1);
//                ToastUtil.showShort(mContext, data.getData());
            }
        });

        //对象
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"这是一条消息\",\n" +
                        "    \"data\": {\"name\":\"这是一个对象\"}\n" +
                        "}";

                ParseUtil parseUtil = new ParseUtil<DateBean>(json1) {
                    @Override
                    public NetBean jsonParse(String json) {
                        data = super.jsonParse(json);
                        return data;
                    }
                };
                ToastUtil.showShort(mContext, data.getData().getName());
            }
        });
        //数组
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"这是一条消息\",\n" +
                        "    \"data\": [{\"name\":\"这是一个数组\"}]\n" +
                        "}";
                ParseUtil parseUtil = new ParseUtil<DateBean>(json1) {
                    @Override
                    public NetBean jsonParse(String json) {
                        dataList = super.jsonParse(json);
                        return dataList;
                    }
                };
                ToastUtil.showShort(mContext, dataList.getData().get(0).getName());
            }
        });
        //这是多的字段
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"这是一条消息\",\n" +
                        "    \"name\":\"这是多的字段\",\n" +
                        "    \"data\": [{\"name\":\"这是多的字段的解析\"}]\n" +
                        "}";
                ParseUtil parseUtil = new ParseUtil<DateBean>(json1) {
                    @Override
                    public NetBean jsonParse(String json) {
                        dataList = super.jsonParse(json);
                        return dataList;
                    }
                };
                ToastUtil.showShort(mContext, dataList.getData().get(0).getName());
            }
        });
        //数组数据为空
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"数组数据为空\",\n" +
                        "    \"name\":\"这是多的字段\",\n" +
                        "    \"data\": []\n" +
                        "}";
                ParseUtil parseUtil = new ParseUtil<DateBean>(json1) {
                    @Override
                    public NetBean jsonParse(String json) {
                        dataList = super.jsonParse(json);
                        return dataList;
                    }
                };
                ToastUtil.showShort(mContext, dataList.getMessage());
            }
        });
        //data数据为空
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"data数据为空\",\n" +
                        "    \"name\":\"这是多的字段\",\n" +
                        "    \"data\": null\n" +
                        "}";
                ParseUtil parseUtil = new ParseUtil<DateBean>(json1) {
                    @Override
                    public NetBean jsonParse(String json) {
                        dataList = super.jsonParse(json);
                        return dataList;
                    }
                };
                ToastUtil.showShort(mContext, dataList.getMessage());
            }
        });
        //data数据类型不匹配
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json1 = "{\n" +
                        "    \"code\": 200,\n" +
                        "    \"message\": \"这是一条消息\",\n" +
                        "    \"name\":\"这是多的字段\",\n" +
                        "    \"data\": [{\"name\":{\"nick\":\"昵称\",\"user\":\"用户名\"}}]\n" +
                        "}";
                ParseUtil parseUtil = new ParseUtil<DateBean>(json1) {
                    @Override
                    public NetBean jsonParse(String json) {
                        dataList = super.jsonParse(json);
                        return dataList;
                    }
                };
                ToastUtil.showShort(mContext, dataList.getMessage());
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gson;
    }

}

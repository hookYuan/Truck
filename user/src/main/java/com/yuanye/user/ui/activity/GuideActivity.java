package com.yuanye.user.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tmall.ultraviewpager.UltraViewPager;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuanye.user.R;
import com.yuanye.user.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页(第一次安装的时候展示)
 * Created by YuanYe on 2017/8/11.
 */

@Route(path = "/user/ui/activity/GuideActivity")
public class GuideActivity extends MVPActivity {

    private UltraViewPager ultra_viewpager;

    @Override
    public int getLayoutId() {
        return R.layout.u_act_guide;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initVew();

    }

    private void initVew() {
        ViewPager ultraViewPager = (ViewPager) findViewById(R.id.ultra_viewpager);
//        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager
        List<String> str = new ArrayList<>();
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425311069&di=41b3850a5bafc3247ea8226bc671af0f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b36c5850c27ca801219c77488bb0.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1506415016&di=991d342246f0d2586ee26d67f9935417&src=http://img.zcool.cn/community/01060d56cfcb026ac7252ce6bb00b2.png");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1506415016&di=b11255d9148f4dcb772ba691c9699e2f&src=http://img.zcool.cn/community/01ee9e58342911a8012060c8e30a24.png");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1506415016&di=b11255d9148f4dcb772ba691c9699e2f&src=http://img.zcool.cn/community/01ee9e58342911a8012060c8e30a24.png");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1506415016&di=b11255d9148f4dcb772ba691c9699e2f&src=http://img.zcool.cn/community/01ee9e58342911a8012060c8e30a24.png");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1506415016&di=b11255d9148f4dcb772ba691c9699e2f&src=http://img.zcool.cn/community/01ee9e58342911a8012060c8e30a24.png");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        str.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506425376398&di=13bf30abbea2ae7565679eb3c994a593&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012ed057b5c6950000018c1bc53d42.jpg");
        PagerAdapter adapter = new GuideAdapter(str);
        ultraViewPager.setAdapter(adapter);
        //内置indicator初始化
//        ultraViewPager.initIndicator();
//        //设置indicator样式
//        ultraViewPager.getIndicator()
//                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
//                .setFocusColor(Color.GREEN)
//                .setNormalColor(Color.WHITE)
//                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
//        //设置indicator对齐方式
//        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//        //构造indicator,绑定到UltraViewPager
//        ultraViewPager.getIndicator().build();

        //设定页面循环播放
//        ultraViewPager.setInfiniteLoop(false);
//        //设定页面自动切换  间隔2秒
//        ultraViewPager.setAutoScroll(5000);
    }

    @Override
    protected ETitleType showToolBarType() {
        return ETitleType.NO_TITLE;
    }
}

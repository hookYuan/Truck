package com.yuan.user.view.splash.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tmall.ultraviewpager.UltraViewPager;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.user.R;
import com.yuan.user.view.splash.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页(第一次安装的时候展示)
 * Created by YuanYe on 2017/8/11.
 */

@Route(path = "/user/view/splash/ui/GuideActivity")
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
        //UltraPagerAdapter 绑定子view到UltraViewPager
        List<Integer> str = new ArrayList<>();
        str.add(R.mipmap.ic_launcher);
        str.add(R.mipmap.ic_launcher);
        str.add(R.mipmap.ic_launcher);
        PagerAdapter adapter = new GuideAdapter(str);
        ultraViewPager.setAdapter(adapter);
    }

    @Override
    protected ETitleType showToolBarType() {
        return ETitleType.NO_TITLE;
    }
}

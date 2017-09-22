package com.yuan.demo.activity.one.swipeback;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.demo.myapplication.R;

@Route(path = "/swipback/SwipBackActivity")
public class SwipBackActivity extends ExtraActivity implements ISwipeBack {

    @Override
    public int getLayoutId() {
        return R.layout.activity_swip_back;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar().setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar("右滑返回");
    }

}

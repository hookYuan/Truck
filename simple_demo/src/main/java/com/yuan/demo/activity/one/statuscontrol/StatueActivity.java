package com.yuan.demo.activity.one.statuscontrol;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.common.view.XStateController;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.IStateController;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.demo.myapplication.R;

/**
 * Created by YuanYe on 2017/7/12.
 */

@Route(path = "/statusControl/StatueActivity")
public class StatueActivity extends ExtraActivity implements IStateController, ISwipeBack, View.OnClickListener {
    XStateController controller;

    @Override
    public int getLayoutId() {
        return R.layout.list_layout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar().setToolbar("状态控制Activity").setLeftIcon(R.drawable.ic_base_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(1002,getIntent());
                finish();
            }
        });
        findViewById(R.id.rtv_status_01).setOnClickListener(this);
        findViewById(R.id.rtv_status_02).setOnClickListener(this);
        findViewById(R.id.rtv_status_03).setOnClickListener(this);
        findViewById(R.id.rtv_status_04).setOnClickListener(this);
    }

    @Override
    public XStateController getStateView() {
        controller = (XStateController) findViewById(R.id.contentLayout);
        return controller;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_status_01:
                controller.showContent();
                break;
            case R.id.rtv_status_02:
                controller.showEmpty();
                break;
            case R.id.rtv_status_03:
                controller.showLoading();
                break;
            case R.id.rtv_status_04:
                controller.showError();
                break;
        }
    }
}

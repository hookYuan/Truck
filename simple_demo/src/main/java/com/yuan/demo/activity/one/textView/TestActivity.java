package com.yuan.demo.activity.one.textView;

import android.os.Bundle;

import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;
import com.yuan.demo.presenter.PTest;

public class TestActivity extends MVPActivity<PTest> {

    @Override
    protected void initData(Bundle savedInstanceState) {
        getP().getPersonList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }
}

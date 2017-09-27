package com.yuan.demo.activity.one.shape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;

/**
 * 减少shape文件引用
 */
public class ShapeActivity extends MVPActivity {

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_shape;
    }
}

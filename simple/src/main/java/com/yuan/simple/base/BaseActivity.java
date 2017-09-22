package com.yuan.simple.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.simple.R;

/**
 * Created by YuanYe on 2017/8/15.
 */

public abstract class BaseActivity extends ExtraActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBar().setTitleBarColor(ContextCompat.getColor(mContext, R.color.white))
                .setFontColor(ContextCompat.getColor(mContext, R.color.colorFont33))
                .setStatusBarColor(Color.parseColor("#22000000"));

    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }
}

package com.yuan.basemodule.ui.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.ui.base.activity.BaseActivity;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;

/**
 * Created by YuanYe on 2017/9/19.
 */

public abstract class MVPActivity<T extends XPresenter> extends ExtraActivity {
    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = TUtil.getT(MVPActivity.this, 0);
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    protected T getP() {
        return presenter;
    }
}

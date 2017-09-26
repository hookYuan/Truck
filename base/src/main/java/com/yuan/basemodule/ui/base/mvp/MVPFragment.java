package com.yuan.basemodule.ui.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.ui.base.fragment.BaseFragment;
import com.yuan.basemodule.ui.base.fragment.ExtraFragment;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;

/**
 * Created by YuanYe on 2017/9/19.
 */

public abstract class MVPFragment<T extends XPresenter> extends LazyFragement {

    private T presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = TUtil.getT(MVPFragment.this, 0);
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    protected T getP() {
        return presenter;
    }
}

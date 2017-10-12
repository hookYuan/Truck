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
        presenter = TUtil.getT(MVPFragment.this, 0);
        if (presenter != null) {
            presenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    protected T getP() {
        if (presenter == null) {
            try {
                throw new NullPointerException("使用presenter,MVPActivity泛型不能为空");
            } catch (NullPointerException e) {
                throw e;
            }
        }
        return presenter;
    }
}

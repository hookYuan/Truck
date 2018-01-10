package com.yuan.basemodule.ui.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by YuanYe on 2017/9/19.
 */

public abstract class MVPFragment<T extends XPresenter> extends LazyFragement {

    private T presenter;
    boolean useEvent = false;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = TUtil.getT(MVPFragment.this, 0);
        if (presenter != null) {
            presenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
        if (useEvent&&!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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


    /**
     * 开启Event
     * 必须在initData中或之前开启
     * 必须在开启Event中的勒种使用Subscribe接收注解
     */
    protected void openEvent() {
        useEvent = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEvent&&!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}

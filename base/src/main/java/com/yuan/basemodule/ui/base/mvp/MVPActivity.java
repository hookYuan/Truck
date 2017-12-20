package com.yuan.basemodule.ui.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by YuanYe on 2017/9/19.
 * 2017/12/20 加入EventBus
 */
public abstract class MVPActivity<T extends XPresenter> extends ExtraActivity {
    private T presenter;
    private boolean useEvent = false;

    public static void open(Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = TUtil.getT(MVPActivity.this, 0);
        if (presenter != null) {
            presenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
        if (useEvent) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 开启Event
     * 必须在initData中或之前开启
     * 必须在开启Event中的勒种使用Subscribe接收注解
     */
    protected void openEvent() {
        useEvent = true;
    }

    public T getP() {
        if (presenter == null) {
            try {
                throw new NullPointerException("使用presenter,MVPActivity泛型不能为空");
            } catch (NullPointerException e) {
                throw e;
            }
        }
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEvent) {
            EventBus.getDefault().unregister(this);
        }
    }
}

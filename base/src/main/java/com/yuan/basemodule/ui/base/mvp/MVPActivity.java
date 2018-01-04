package com.yuan.basemodule.ui.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by YuanYe on 2017/9/19.
 */

public abstract class MVPActivity<T extends XPresenter> extends ExtraActivity {
    private T presenter;

    boolean useEvent = false;

    public static void open(Class clazz) {
        open(clazz, null);
    }

    public static void open(Class clazz, Map<String, Object> object) {
        Intent intent = new Intent(mContext, clazz);
        if (object != null) {
            for (Map.Entry<String, Object> entry : object.entrySet()) {
                if (entry.getValue() instanceof String
                        || entry.getValue() instanceof Integer
                        || entry.getValue() instanceof Float
                        || entry.getValue() instanceof Long
                        || entry.getValue() instanceof Boolean) {
                    intent.putExtra(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Bundle) {
                    intent.putExtras((Bundle) entry.getValue());
                }
            }
        }
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = TUtil.getT(MVPActivity.this, 0);
        if (presenter != null) {
            presenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
        if (useEvent && !EventBus.getDefault().isRegistered(this)) {
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
        if (useEvent && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

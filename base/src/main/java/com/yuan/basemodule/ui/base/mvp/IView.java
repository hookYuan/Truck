package com.yuan.basemodule.ui.base.mvp;

import android.support.annotation.LayoutRes;

/**
 * Created by YuanYe on 2017/7/11.
 * 该接口主要用于约束Activity和Fragment的基本方法
 */
public interface IView {
    @LayoutRes
    int getLayoutId();
}

package com.yuan.basemodule.ui.base.extend;

import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by YuanYe on 2017/7/12.
 * <p>
 * 接口说明： 用于刷新的接口
 */

public interface IRefresh<T extends View> {
    //初始化刷新控件
    T getRefreshView();

    //下拉刷新
    void onRefresh();

    //上拉加载
    void onLoad();
}

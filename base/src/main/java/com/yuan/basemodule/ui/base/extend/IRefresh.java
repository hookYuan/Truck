package com.yuan.basemodule.ui.base.extend;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by YuanYe on 2017/7/12.
 *
 * 接口说明： 用于刷新的接口
 */

public interface IRefresh<T extends SmartRefreshLayout> {
    //初始化刷新控件
    T getRefreshView();
    //下拉刷新
    void onRefresh();
    //上拉加载
    void onLoad();
}

package com.yuan.basemodule.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuan.basemodule.ui.base.extend.IRefresh;
import com.yuan.basemodule.ui.base.extend.IStateController;
import com.yuan.basemodule.ui.base.helper.HRefresh;
import com.yuan.basemodule.ui.base.helper.HStateController;

/**
 * Created by YuanYe on 2017/8/9.
 */

public abstract class ExtraFragment extends TitleFragment {

    private IRefresh iRefresh;//下拉刷新功能
    private IStateController iStateController; //状态控制器

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = super.onCreateView(inflater, container, savedInstanceState);
        managerExtra();
        return mview;
    }

    /**
     * 管理Fragment的扩展功能
     */
    private void managerExtra() {
        //根据不同情况，控制不同的执行顺序
        if (this instanceof IRefresh) {
            //初始化刷新控件
            iRefresh = (IRefresh) this;
            HRefresh.setRefreshLayout(iRefresh.getRefreshView(), iRefresh);
        }
        if (this instanceof IStateController) {
            //初始化状态控制器
            iStateController = (IStateController) this;
            HStateController.init(iStateController.getStateView());
        }

    }
}

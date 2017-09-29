package com.yuan.basemodule.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yuan.basemodule.ui.base.helper.HRefresh;
import com.yuan.basemodule.ui.base.helper.HStateController;
import com.yuan.basemodule.ui.base.helper.HSwipeBack;
import com.yuan.basemodule.ui.base.extend.IRefresh;
import com.yuan.basemodule.ui.base.extend.IStateController;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;

import butterknife.ButterKnife;

/**
 * Created by YuanYe on 2017/7/12.
 * <p>
 * 扩展功能基类，面对接口初始化
 * 如需使用扩展功能，应该继承该类
 */
public abstract class ExtraActivity extends TitleActivity {

    private ISwipeBack iSwipBack;//实现滑动返回功能
    private IRefresh iRefresh;//下拉刷新功能
    private IStateController iStateController; //状态控制器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerExtra();
        initData(savedInstanceState);
    }

    /**
     * 管理Activity的扩展功能
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

        if (this instanceof ISwipeBack) {
            //初始化滑动返回
            iSwipBack = (ISwipeBack) this;
            HSwipeBack.init(this);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (iStateController != null)
            HSwipeBack.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iStateController != null)
            HSwipeBack.onDestroy(this);
    }

}

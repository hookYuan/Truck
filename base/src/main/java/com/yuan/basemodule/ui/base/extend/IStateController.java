package com.yuan.basemodule.ui.base.extend;

import com.yuan.basemodule.common.view.XStateController;

/**
 * Created by YuanYe on 2017/7/12.
 * 界面状态控制器
 */

public interface IStateController<T extends XStateController> {
    T getStateView();
}

package com.yuan.basemodule.ui.base.helper;

import android.view.View;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.view.XStateController;

/**
 * Created by YuanYe on 2017/7/12.
 * 状态控制器
 */

public class HStateController {
    /**
     * 初始化，设置基本状态
     */
    public static void init(XStateController view){
        XStateController contentLayout =  view;
        contentLayout.emptyView(View.inflate(view.getContext(), R.layout.status_empty,null));
        contentLayout.loadingView(View.inflate(view.getContext(), R.layout.status_loading,null));
        contentLayout.errorView(View.inflate(view.getContext(), R.layout.status_erro,null));
    }
}

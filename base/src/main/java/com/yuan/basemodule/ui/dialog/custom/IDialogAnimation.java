package com.yuan.basemodule.ui.dialog.custom;

import android.graphics.Color;
import android.view.View;

/**
 * Created by YuanYe on 2017/9/20.
 */

public interface IDialogAnimation {
    /**
     * @param showView 弹窗展示的View
     * @param bgView   整个弹窗父布局
     * @return 返回动画执行时长
     */
    int inAnimation(View showView, View bgView); //进场动画

    /**
     * @param showView 弹窗展示的View
     * @param bgView   整个弹窗父布局
     */
    int outAnimation(View showView, View bgView); //出场动画
}

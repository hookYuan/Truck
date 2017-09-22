package com.yuan.basemodule.ui.title;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by YuanYe on 2017/7/29.
 * 自定义Toolbar
 * <p>
 * 整体控制Titlebar的初始化顺序
 * 1.设置标题、图标、点击事件
 * 2.1--设置主题颜色
 * 3.设置titlebar整体高度，判断statusbar是否显示
 * 4.设置动画效果
 */
public class TitleBar extends TitleAnimationHelper<TitleBar> {

    public TitleBar(Context _context, @Nullable AttributeSet attrs) {
        super(_context, attrs);
    }

}

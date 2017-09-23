package com.yuan.basemodule.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.kit.SysTool;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.title.ETitleTheme;
import com.yuan.basemodule.ui.title.TitleBar;

/**
 * Created by YuanYe on 2017/9/7.
 * Activity中默认添加进入Title,继承该类默认使用Title
 */
public abstract class TitleActivity extends BaseActivity {

    private TitleBar titleBar;
    private View layoutView; //内容View

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //控制Toolbar的是否显示
        View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        SysTool.StatusBarUtil.darkMode(this);
        switch (showToolBarType()) {
            case NO_TITLE:
                //不显示toolbar(全屏显示)
                if (layoutView == null) {
                    setContentView(getLayoutId());
                } else {
                    setContentView(layoutView);
                }
                break;
            case SIMPLE_TITLE:
                setSimpleTitle(rootView);
                break;
            case OVERLAP_TITLE:  //显示透明toolbar
                setOverlap(rootView);
                break;
        }
    }

    /**
     * 设置普通标题
     */
    private void setSimpleTitle(View rootView) {
        //默认显示不透明titlebar
        View parent = LayoutInflater.from(mContext).inflate(R.layout.layout_title_simple, (ViewGroup) rootView, false);
        titleBar = (TitleBar) parent.findViewById(R.id.titleBar);
        View child = null;
        if (layoutView == null) {
            child = LayoutInflater.from(mContext).inflate(getLayoutId(), (ViewGroup) parent, false);
        } else {
            child = layoutView;
        }
        ((ViewGroup) parent).addView(child,0);
        setContentView(parent);
        child.setFitsSystemWindows(false); //让padding有效
        child.setPadding(child.getPaddingLeft(), child.getPaddingTop() + getTitleBar().getTitleBarHeight() +
                        getTitleBar().getStatusBarHeight(),
                child.getPaddingRight(), child.getPaddingBottom());
    }

    /**
     *   默认透明背景，黑色文字
     */
    private void setOverlap(View rootView) {
        View parent = LayoutInflater.from(this).inflate(R.layout.layout_title_simple, (ViewGroup) rootView, false);
        titleBar = (TitleBar) parent.findViewById(R.id.titleBar);
        View child = null;
        if (layoutView == null) {
            child = LayoutInflater.from(this).inflate(getLayoutId(), (ViewGroup) parent, false);
        } else {
            child = layoutView;
        }
        ((ViewGroup) parent).addView(child,0);
        setContentView(parent);
        titleBar.setDefaultTheme(ETitleTheme.DARK_TRANSPARENT);
    }


    protected TitleBar getTitleBar() {
        if (titleBar == null) {
            Log.e("TitleBar", "TitleBar没有初始化，请选择TitleBar模式");
            return null;
        }
        return titleBar;
    }

    /**
     * 控制Activity是否显示toolbar,重写该方法可修改
     */
    protected ETitleType showToolBarType() {
        return ETitleType.SIMPLE_TITLE;
    }

    /**
     * 可以对getLayoutID的内容包装后统一返回
     * @param layoutView
     */
    protected void setLayoutView(View layoutView) {
        this.layoutView = layoutView;
    }
}

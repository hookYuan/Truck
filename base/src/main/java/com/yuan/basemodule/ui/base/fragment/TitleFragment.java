package com.yuan.basemodule.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.kit.SysTool;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.title.ETitleTheme;
import com.yuan.basemodule.ui.title.TitleBar;

import butterknife.ButterKnife;

/**
 * Created by YuanYe on 2017/9/7.
 */

public abstract class TitleFragment extends StatedFragment {

    private TitleBar titleBar;
    private View layoutView; //内容View

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = super.onCreateView(inflater, container, savedInstanceState);
        switch (showToolBarType()) {
            case NO_TITLE:
                //不显示toolbar(全屏显示)
                if (layoutView != null) {
                    mview = layoutView;
                }
                break;
            case SIMPLE_TITLE:
                setSimpleTitle(container);
                break;
            case OVERLAP_TITLE:  //显示透明toolbar
                setOverlap(container);
                break;
        }
        ButterKnife.bind(this, mview);
        return mview;
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
        ((ViewGroup) parent).addView(child, 0);
        titleBar.setStatuBarHeight(SysTool.StatusBarUtil.getStatusBarHeight(mContext));
        child.setFitsSystemWindows(false); //让padding有效
        child.setPadding(child.getPaddingLeft(), child.getPaddingTop() + getTitleBar().getTitleBarHeight() +
                        getTitleBar().getStatusBarHeight(),
                child.getPaddingRight(), child.getPaddingBottom());
        mview = parent;
    }

    /**
     * 默认透明背景，黑色文字
     */
    private void setOverlap(View rootView) {
        View parent = LayoutInflater.from(mContext).inflate(R.layout.layout_title_simple, (ViewGroup) rootView, false);
        titleBar = (TitleBar) parent.findViewById(R.id.titleBar);
        View child = null;
        if (layoutView == null) {
            child = LayoutInflater.from(mContext).inflate(getLayoutId(), (ViewGroup) parent, false);
        } else {
            child = layoutView;
        }
        ((ViewGroup) parent).addView(child, 0);
        mview = parent;
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
     *
     * @param layoutView
     */
    protected void setLayoutView(View layoutView) {
        this.layoutView = layoutView;
    }

}

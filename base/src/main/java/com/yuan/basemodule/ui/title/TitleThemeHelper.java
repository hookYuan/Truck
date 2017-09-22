package com.yuan.basemodule.ui.title;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.common.kit.SysTool;

/**
 * Created by YuanYe on 2017/9/1.
 * 设置titleBar的主题,高度效果
 */
public class TitleThemeHelper<T extends TitleThemeHelper> extends TitleContentHelper<TitleThemeHelper> {

    private T child;


    protected TitleThemeHelper(Context _context, @Nullable AttributeSet attrs) {
        super(_context, attrs);
        child = (T) this;
        init();
    }

    /**
     * 初始化各个控件的默认状态
     */
    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置悬浮titleBar
            rootView.setTranslationZ(Kits.Dimens.dpToPx(context, floatZ));
            setStatuBarHeight(SysTool.StatusBarUtil.getStatusBarHeight(context));
        }
        //设置状态栏颜色
        setDefaultTheme(ETitleTheme.LIGHT_PRIMARY);
    }

    /**
     * -------------------------------------toolbar默认主题样式组合---------------------------------------------
     **/
    public T setDefaultTheme(ETitleTheme type) {
        switch (type) {
            case LIGHT_PRIMARY:
                setTitleAndStatusBgColor(ContextCompat.getColor(context, R.color.colorPrimary));
                setFontColor(ContextCompat.getColor(context, R.color.white));
                break;
            case LIGHT_TRANSPARENT:
                setTitleAndStatusBgColor(ContextCompat.getColor(context, R.color.transparent));
                setTitleBarColor(ContextCompat.getColor(context, R.color.transparent));
                setStatusBarColor(ContextCompat.getColor(context, R.color.transparent));
                setFontColor(ContextCompat.getColor(context, R.color.white));
                break;
            case DARK_PRIMARY:
                setTitleAndStatusBgColor(ContextCompat.getColor(context, R.color.colorPrimary));
                setFontColor(ContextCompat.getColor(context, R.color.colorFont33));
                break;
            case DARK_TRANSPARENT:
                setTitleAndStatusBgColor(ContextCompat.getColor(context, R.color.transparent));
                setTitleBarColor(ContextCompat.getColor(context, R.color.transparent));
                setStatusBarColor(ContextCompat.getColor(context, R.color.transparent));
                setFontColor(ContextCompat.getColor(context, R.color.colorFont33));
                break;
        }
        return child;
    }

    /**
     * -------------------------------------设置toolbar颜色---------------------------------------------
     **/
    public T setFontColor(@ColorInt int textColor) {
        leftTextView.setTextColor(textColor);
        centerTextView.setTextColor(textColor);
        rightTextView.setTextColor(textColor);
        return child;
    }

    public T setTitleBarColor(@ColorInt int backgroundColor) {
        if (titleRootView != null) {
            titleRootView.setBackgroundColor(backgroundColor);
        }
        return child;
    }

    public T setStatusBarColor(@ColorInt int backgroundColor) {
        if (statusBarView != null && backgroundColor != -1) {
            statusBarView.setBackgroundColor(backgroundColor);
        }
        return child;
    }

    public T setTitleAndStatusBgColor(@ColorInt int backgroundColor) {
        if (rootView != null) {
            rootView.setBackgroundColor(backgroundColor);
        }
        return child;
    }

    //TODO 标题背景可以设置图片

    /**
     * -------------------------------------设置toolbar高度---------------------------------------------
     **/
    public T setStatuBarHeight(int height) {
        //版本判断，5.0以上有效
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Log.e(TAG, "系统版本不支持StatueBar");
            return child;
        }
        if (height >= 0 && statusBarView != null) {
            statusBarView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        }
        return child;
    }

    public T setTitleBarHeight(int height) {
        if (height >= 0 && titleRootView != null) {
            titleRootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        }
        return child;
    }

    public int getStatusBarHeight() {
        int h = 0;
        if (statusBarView != null) {
            h = statusBarView.getLayoutParams().height;
        }
        return h;
    }

    public int getTitleBarHeight() {
        int h = 0;
        if (titleRootView != null) {
            h = titleRootView.getLayoutParams().height;
        }
        return h;
    }
}

package com.yuan.basemodule.ui.title;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by YuanYe on 2017/9/1.
 * 有关Titlbar动画相关效果
 */
public class TitleAnimationHelper<T extends TitleAnimationHelper> extends TitleThemeHelper<TitleThemeHelper> {

    private T child;

    protected TitleAnimationHelper(Context _context, @Nullable AttributeSet attrs) {
        super(_context, attrs);
        child = (T) this;
    }

    /**
     * -------------------------------------toolbar平移动画---------------------------------------------
     * TODO 需要在activity初始化完成后调用，rootview.getHeight（）可能为空
     **/
    public T setAnimationTitleBarIn() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -rootView.getHeight(), 0);
        animation.setDuration(300);//设置动画持续时间
        rootView.setAnimation(animation);
        animation.startNow();
        rootView.setVisibility(View.VISIBLE);
        return child;
    }

    public T setAnimationTitleBarOut() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -rootView.getHeight());
        animation.setDuration(300);//设置动画持续时间
        rootView.setAnimation(animation);
        animation.startNow();
        rootView.setVisibility(View.GONE);
        return child;
    }

    public T restoreAnimationTitle() {
        if (rootView.getVisibility() == View.GONE) {
            //显示title
            rootView.setVisibility(VISIBLE);
        }
        return child;
    }
}

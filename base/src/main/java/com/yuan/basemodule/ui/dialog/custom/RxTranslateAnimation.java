package com.yuan.basemodule.ui.dialog.custom;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by YuanYe on 2017/9/20.
 * <p>
 * 默认RxDialog平移动画效果，并且设置背景效果
 */
public class RxTranslateAnimation implements IDialogAnimation {


    public static final int leftToRight = 0;//左向右平移
    public static final int rightToLeft = 1;//右向左平移
    public static final int topToBottom = 2; //上向下平移
    public static final int bottomToTop = 3;//下向上平移

    private int rxOrientation;

    private int animationTime = 100; //动画执行时长

    //设置动画平移方向
    public RxTranslateAnimation(int orientation) {
        this.rxOrientation = orientation;
    }


    @Override
    public int inAnimation(View dialogView, final View bgView) {
        TranslateAnimation animation = null;
        switch (rxOrientation) {
            case leftToRight:
                animation = new TranslateAnimation(-dialogView.getWidth(),0.0f, 0.0f, 0.0f);
                break;
            case rightToLeft:
                animation = new TranslateAnimation(dialogView.getWidth(), 0.0f, 0.0f, 0.0f);
                break;
            case topToBottom:
                animation = new TranslateAnimation(0.0f, 0.0f, -dialogView.getHeight(), 0.0f);
                break;
            case bottomToTop:
                animation = new TranslateAnimation(0.0f, 0.0f, dialogView.getHeight(), 0.0f);
                break;
        }
        animation.setDuration(animationTime);//设置动画持续时间
        dialogView.setAnimation(animation);
        animation.startNow();

        //背景的（渐变）动画
        ValueAnimator valueAnimator = ValueAnimator.ofInt(10, 99);
        valueAnimator.setDuration(animationTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                String color = value + "000000";
                bgView.setBackgroundColor(Color.parseColor("#" + color));
                if (value == 10) {
                    bgView.setVisibility(View.VISIBLE);
                }
            }
        });
        valueAnimator.start();
        return animationTime;
    }

    @Override
    public int outAnimation(View dialogView, final View bgView) {

        TranslateAnimation animation = null;
        switch (rxOrientation) {
            case leftToRight:
                animation = new TranslateAnimation(0.0f, -dialogView.getWidth(), 0.0f, 0.0f);
                break;
            case rightToLeft:
                animation = new TranslateAnimation(0.0f, dialogView.getWidth(), 0.0f, 0.0f);
                break;
            case topToBottom:
                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -dialogView.getHeight());
                break;
            case bottomToTop:
                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, dialogView.getHeight());
                break;
        }
        animation.setDuration(animationTime);//设置动画持续时间
        dialogView.setAnimation(animation);
        animation.startNow();

        //背景的动画
        ValueAnimator valueAnimator = ValueAnimator.ofInt(99, 10);
        valueAnimator.setDuration(animationTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                String color = value + "000000";
                bgView.setBackgroundColor(Color.parseColor("#" + color));
                if (value == 10) {
                    bgView.setVisibility(View.GONE);
                }
            }
        });
        valueAnimator.start();
        return animationTime;
    }
}

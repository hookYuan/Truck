package com.yuan.basemodule.ui.dialog.custom;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by YuanYe on 2017/9/20.
 * <p>
 * 默认RxDialog缩放动画效果，自带背景效果
 */
public class RxScaleAnimation implements IDialogAnimation {

    private int animationTime = 100; //动画执行时长

    @Override
    public int inAnimation(View dialogView, final View bgView) {
        android.view.animation.ScaleAnimation animation = new android.view.animation.ScaleAnimation(0.3f, 1.05f, 0.3f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(animationTime);//设置动画持续时间
        dialogView.startAnimation(animation);

        //背景的（渐变）动画
        ValueAnimator valueAnimator = ValueAnimator.ofInt(10, 99);
        valueAnimator.setDuration(animationTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                String color = value + "000000";
                bgView.setBackgroundColor(Color.parseColor("#" + color));
            }
        });
        valueAnimator.start();
        return animationTime;
    }

    @Override
    public int outAnimation(View dialogView, final View bgView) {
        android.view.animation.ScaleAnimation animation = new android.view.animation.ScaleAnimation(1.05f, 0.3f, 1.05f, 0.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(animationTime);//设置动画持续时间
        dialogView.startAnimation(animation);

        //背景的动画
        ValueAnimator valueAnimator = ValueAnimator.ofInt(99, 10);
        valueAnimator.setDuration(animationTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                String color = value + "000000";
                bgView.setBackgroundColor(Color.parseColor("#" + color));
            }
        });
        valueAnimator.start();
        return animationTime;
    }
}

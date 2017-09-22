package com.yuan.album.util;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

import com.yuan.album.R;

/**
 * 作者：yuanYe创建于2016/12/29
 * QQ：962851730
 * 弹窗显示popupWindow的设置公共类
 */

public class PopupWindowUtil {
    /**
     * 显示popupWindow
     * @param view 加载的布局文件
     * @param parent 显示的位置
     */
    public static void showPopwindow(View view, View parent) {
        PopupWindow popupWindow = null;
        if (popupWindow != null && popupWindow.isShowing()) {
            //关闭popupWindow
            popupWindow.dismiss();
        }else if(popupWindow==null){
            // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
            popupWindow = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,true);

            //设置弹出的popupWindow不遮挡软键盘
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            //设置点击外部让popupWindow消失
            popupWindow.setFocusable(true);//可以试试设为false的结果
            popupWindow.setOutsideTouchable(true); //点击外部消失

            //必须设置的选项
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            // 实例化一个ColorDrawable颜色为半透明
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });
            // 设置popWindow的显示和消失动画（这里的动画是由下往上）
            popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
            //将window视图显示在点击按钮下面
//            popupWindow.showAsDropDown(toView, toView.getHeight(), 0);
            popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,parent.getHeight());
            Log.i("yuanye","222222222222222");
        }else {
            //将window视图显示在点击按钮下面
            popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//            popupWindow.showAsDropDown(toView, toView.getHeight(), 0);
            popupWindow.showAtLocation(parent,  Gravity.BOTTOM,0,parent.getHeight());
        }
    }

    private static boolean isShowMyWindow = false;
    /**
     * 显示自定义的弹窗
     * view的宽高由布局决定(主要是自定义控件弹出的动画效果)
     */
    public static void showMyWindow(final View popView){
        final View parent = (View) popView.getParent();
        parent.setBackgroundColor(Color.parseColor("#88000000"));
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowMyWindow){
                    //隐藏
                    TranslateAnimation animation2 = new TranslateAnimation(0, 0, 0, popView.getHeight());
                    animation2.setDuration(300);//设置动画持续时间
                    popView.setAnimation(animation2);
                    animation2.startNow();
                    popView.setVisibility(View.GONE);

                    //背景的动画
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(99,10);
                    valueAnimator.setDuration(300);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            parent.setBackgroundColor(Color.parseColor("#"+value+"000000"));
                            if(value==10){
                                parent.setVisibility(View.GONE);
                            }
                        }
                    });
                    valueAnimator.start();
                    isShowMyWindow = false;
                }
            }
        });
        if(isShowMyWindow){ //隐藏
            TranslateAnimation animation2 = new TranslateAnimation(0, 0, 0, popView.getHeight());
            animation2.setDuration(300);//设置动画持续时间
            popView.setAnimation(animation2);
            animation2.startNow();
            popView.setVisibility(View.GONE);
            //背景的动画
            ValueAnimator valueAnimator = ValueAnimator.ofInt(99,10);
            valueAnimator.setDuration(300);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    parent.setBackgroundColor(Color.parseColor("#"+value+"000000"));
                    if(value==10){
                        parent.setVisibility(View.GONE);
                    }
                }
            });
            valueAnimator.start();
            isShowMyWindow = false;
        }else { //显示弹窗
            TranslateAnimation animation2 = new TranslateAnimation(0, 0, popView.getHeight(), 0);
            animation2.setDuration(300);//设置动画持续时间
            popView.setAnimation(animation2);
            animation2.startNow();
            popView.setVisibility(View.VISIBLE);
            //背景的动画
            ValueAnimator valueAnimator = ValueAnimator.ofInt(10,99);
            valueAnimator.setDuration(300);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    parent.setBackgroundColor(Color.parseColor("#"+value+"000000"));
                    if(value==10){
                        parent.setVisibility(View.VISIBLE);
                    }
                }
            });
            valueAnimator.start();
            isShowMyWindow = true;
        }
    }
}











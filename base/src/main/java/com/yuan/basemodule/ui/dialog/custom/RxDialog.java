package com.yuan.basemodule.ui.dialog.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.other.Views;

import io.reactivex.Observable;

/**
 * Created by YuanYe on 2017/9/20.
 * 自定义Dialog弹窗
 */
public class RxDialog {

    private RxDialogParams.Builder builder;

    private RxDialogParams dialogParams;

    private Activity activity;

    private LinearLayout parent; //自定义Dialog的父布局样式
    private View dialogView;
    private
    @LayoutRes
    int dialogID;//dialogView资源ID

    private ViewGroup rootViewGroup = null;
    private boolean isShowing = false;//弹窗是否正在显示

    public RxDialog(View view, RxDialogParams.Builder builder) {
        //RxDialog默认设置选项
        this.dialogView = view;
        activity = (Activity) view.getContext();
        this.builder = builder;
    }

    /**
     * 构造函数
     *
     * @param view 这里的View为设置宽高数据的View
     */
    public RxDialog(View view) {
        this.dialogView = view;
        activity = (Activity) view.getContext();
        builder = new RxDialogParams.Builder();
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, R.color.halfTransparent));
        builder.outsideCancle(true);
        builder.gravity(Gravity.CENTER);
    }

    public RxDialog(Context context, @LayoutRes int dialogID) {
        this.dialogID = dialogID;
        activity = (Activity) context;
        builder = new RxDialogParams.Builder();
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, R.color.halfTransparent));
        builder.outsideCancle(true);
        builder.gravity(Gravity.CENTER);
    }

    /**
     * 创建父布局
     * <p>
     * 指定弹窗相较于整个屏幕的位置，重Gravity取值
     * gravity优先级低于直接指定X和y坐标
     */
    private void createPopView() {
        if (parent == null) {
            //获取跟布局decorView
            rootViewGroup = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            //在根布局下添加全局布局
            parent = new LinearLayout(activity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            parent.setLayoutParams(layoutParams);
            parent.setGravity(dialogParams.getGravity()); //默认中间弹窗

            if (dialogParams.getBackgroundDrawable() != null) { //设置背景
                parent.setBackground(dialogParams.getBackgroundDrawable());
            }
            rootViewGroup.addView(parent);
            parent.setVisibility(View.GONE);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogParams.isOutsideCancle()) { //点击外部取消
                        dismiss();
                    }
                }
            });
        }
        createChildView();
    }

    /**
     * 创建子布局
     */
    private void createChildView() {
        //添加DialogView布局到parent
        dialogView = Views.inflate(parent, dialogID);
        parent.addView(dialogView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogView.getLayoutParams();

        //设置弹窗最大宽高不能超出屏幕
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //获取DialogView的宽高
        int mWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int mHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        dialogView.measure(mWidth, mHeight);

        int sHeight = params.height;
        int sWidth = params.width;
        //判断dialogView是否设置具体宽高，如未指定宽高，设置默认宽高

        //防止DialogView超出屏幕
//        if (sHeight > height) {
//            params.height = height;
//        }
//        if (sWidth > width) {
//            params.width = width;
//        }

        if (dialogParams.getExternalGravity() == Gravity.BOTTOM
                || dialogParams.getExternalGravity() == Gravity.RIGHT) {
            //根据x,y坐标，确定dialogView的位置
            params.setMargins(dialogParams.getPosX() + dialogParams.getMarginLeft(),
                    dialogParams.getPosY() + dialogParams.getMarginTop(),
                    0 + dialogParams.getMarginRight(),
                    0 + dialogParams.getMarginRight());
        } else if (dialogParams.getExternalGravity() == Gravity.TOP) {
            params.setMargins(dialogParams.getPosX() + dialogParams.getMarginLeft(),
                    dialogParams.getPosY() - sHeight + dialogParams.getMarginTop(),
                    0 + dialogParams.getMarginRight(),
                    0 + dialogParams.getMarginRight());
        } else if (dialogParams.getExternalGravity() == Gravity.LEFT) {
            params.setMargins(dialogParams.getPosX() - sWidth + dialogParams.getMarginLeft(),
                    dialogParams.getPosY() + dialogParams.getMarginTop(),
                    0 + dialogParams.getMarginRight(),
                    0 + dialogParams.getMarginRight());
            dialogView.setLayoutParams(params);
        }

        if (!(dialogView instanceof AdapterView)) {
            //空实现点击事件，拦击事件的传递
            dialogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    /**
     * *****************************以下为Dialog基本配置参数***********************************************************
     */
    public RxDialog setGravity(int gravity) { //设置Gravity
        builder.gravity(gravity);
        return this;
    }

    public RxDialog setBackgroundColor(@ColorRes int bgColor) { //设置弹窗背景颜色
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, bgColor));
        return this;
    }

    public RxDialog setBackgroundDrawable(Drawable drawable) { //设置弹窗背景图片
        builder.backgroundDrawable(drawable);
        return this;
    }

    public RxDialog setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
        builder.marginLeft(marginLeft);
        builder.marginTop(marginTop);
        builder.marginRight(marginRight);
        builder.marginBottom(marginBottom);
        return this;
    }

    public RxDialog setAnimation(IDialogAnimation animation) {
        builder.animation(animation);
        return this;
    }

    /**
     * *****************************以下为Dialog基于View弹窗位置***********************************************************
     * 注意一下方法需要在Activity创建完毕后调用，否则位置无法生效
     */
    public RxDialog setViewLeft(View targetView) { //展示在targetView左侧
        //测量view起点坐标
        final int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        builder.gravity(Gravity.CENTER_VERTICAL);
        builder.externalGravity(Gravity.LEFT);
        builder.posX(location[0]).posY(0);
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, android.R.color.transparent));
        return this;
    }

    public RxDialog setViewRight(View targetView) { //展示在targetView右侧
        //测量view起点坐标
        final int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        builder.gravity(Gravity.CENTER_VERTICAL);
        builder.externalGravity(Gravity.RIGHT);
        builder.posX(location[0] + targetView.getWidth()).posY(0);
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, android.R.color.transparent));
        return this;
    }


    public RxDialog setViewTop(View targetView) { //展示在targetView顶部
        //测量view起点坐标
        final int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        builder.gravity(Gravity.CENTER_HORIZONTAL);
        builder.externalGravity(Gravity.TOP);
        builder.posX(0).posY(location[1]);
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, android.R.color.transparent));
        return this;
    }

    public RxDialog setViewBottom(View targetView) { //展示在targetView底部
        //测量view起点坐标
        final int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        builder.gravity(Gravity.CENTER_HORIZONTAL);
        builder.externalGravity(Gravity.BOTTOM);
        builder.posX(0).posY(location[1] + targetView.getHeight());
        builder.backgroundDrawable(ContextCompat.getDrawable(activity, android.R.color.transparent));
        return this;
    }


    /**
     * **************************************真实创建，相当于build************************************************
     */
    public void show() {
        isShowing = true;
        if (dialogParams == null) {
            dialogParams = builder.build();
            createPopView();
        }
        if (parent.getVisibility() == View.GONE) {
            if (dialogParams.getAnimation() != null) {
                dialogParams.getAnimation().inAnimation(dialogView, parent);
            }
            parent.setVisibility(View.VISIBLE);
        }
    }

    public void dismiss() {
        int time = 0;
        isShowing = false;
        if (parent.getVisibility() == View.VISIBLE) {
            if (dialogParams.getAnimation() != null) {
                time = dialogParams.getAnimation().outAnimation(dialogView, parent);
            }
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //(等待动画执行完毕)移除Dialog布局
                parent.setVisibility(View.GONE);
                if (rootViewGroup != null) {
                    rootViewGroup.removeViewAt(rootViewGroup.getChildCount() - 1);
                }
                if (parent != null) {
                    parent.removeAllViews();
                }
            }
        }, time);
    }

    public boolean isShowing() {
        return isShowing;
    }
}

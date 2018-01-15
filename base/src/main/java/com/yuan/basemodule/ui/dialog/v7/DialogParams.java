package com.yuan.basemodule.ui.dialog.v7;

import android.support.annotation.ColorRes;
import android.view.Gravity;

/**
 * Created by YuanYe on 2018/1/15.
 */

public class DialogParams {

    private int gravity = Gravity.BOTTOM;//相对位置

    private
    @ColorRes
    int dialogBackground = android.R.color.white;//弹窗白色部分背景

    private float dialogBehindAlpha = 0.5f;//弹窗整个界面黑色背景透明度，范围0.0-1：透明-不透明
    private float dialogFrontAlpha = 1f;//弹窗白色部分背景透明度，范围0.0-1：透明-不透明

    private boolean matchWidth = true; //最大宽度

    private boolean matchHeight = false;//最大高度

    private int posX = 0;//Dialog左上角定点横坐标
    private int posY = 0;//Dialog左上角定点纵坐标

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getDialogFrontAlpha() {
        return dialogFrontAlpha;
    }

    public void setDialogFrontAlpha(float dialogFrontAlpha) {
        this.dialogFrontAlpha = dialogFrontAlpha;
    }

    public float getDialogBehindAlpha() {
        return dialogBehindAlpha;
    }

    public void setDialogBehindAlpha(float dialogBehindAlpha) {
        this.dialogBehindAlpha = dialogBehindAlpha;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public boolean isMatchWidth() {
        return matchWidth;
    }

    public void setMatchWidth(boolean matchWidth) {
        this.matchWidth = matchWidth;
    }

    public int getDialogBackground() {
        return dialogBackground;
    }

    public void setDialogBackground(int dialogBackground) {
        this.dialogBackground = dialogBackground;
    }

    public boolean isMatchHeight() {
        return matchHeight;
    }

    public void setMatchHeight(boolean matchHeight) {
        this.matchHeight = matchHeight;
    }
}

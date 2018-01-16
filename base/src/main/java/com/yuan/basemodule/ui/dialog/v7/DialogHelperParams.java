package com.yuan.basemodule.ui.dialog.v7;

import android.support.annotation.ColorRes;
import android.view.Gravity;

/**
 * Created by YuanYe on 2018/1/15.
 * AlertDialog 简单统一配置文件
 */

public class DialogHelperParams {

    private int gravity;
    private int dialogBackground;
    private float dialogBehindAlpha;
    private float dialogFrontAlpha;
    private boolean matchWidth;
    private boolean matchHeight;
    private int posX;
    private int posY;

    private DialogHelperParams(Builder builder) {
        gravity = builder.gravity;
        dialogBackground = builder.dialogBackground;
        dialogBehindAlpha = builder.dialogBehindAlpha;
        dialogFrontAlpha = builder.dialogFrontAlpha;
        matchWidth = builder.matchWidth;
        matchHeight = builder.matchHeight;
        posX = builder.posX;
        posY = builder.posY;
    }

    public int getGravity() {
        return gravity;
    }

    public int getDialogBackground() {
        return dialogBackground;
    }

    public float getDialogBehindAlpha() {
        return dialogBehindAlpha;
    }

    public float getDialogFrontAlpha() {
        return dialogFrontAlpha;
    }

    public boolean isMatchWidth() {
        return matchWidth;
    }

    public boolean isMatchHeight() {
        return matchHeight;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public static final class Builder {

        private int gravity = Gravity.CENTER;//相对位置

        private
        @ColorRes
        int dialogBackground = android.R.color.white;//弹窗白色部分背景

        private float dialogBehindAlpha = 0.5f;//弹窗整个界面黑色背景透明度，范围0.0-1：透明-不透明
        private float dialogFrontAlpha = 1f;//弹窗白色部分背景透明度，范围0.0-1：透明-不透明

        private boolean matchWidth = false; //最大宽度
        private boolean matchHeight = false;//最大高度

        private int posX = 0;//Dialog左上角定点横坐标
        private int posY = 0;//Dialog左上角定点纵坐标(坐标位置与gravity有关)

        public Builder() {
        }

        public Builder gravity(int val) {
            gravity = val;
            return this;
        }

        public Builder dialogBackground(int val) {
            dialogBackground = val;
            return this;
        }

        public Builder dialogBehindAlpha(float val) {
            dialogBehindAlpha = val;
            return this;
        }

        public Builder dialogFrontAlpha(float val) {
            dialogFrontAlpha = val;
            return this;
        }

        public Builder matchWidth(boolean val) {
            matchWidth = val;
            return this;
        }

        public Builder matchHeight(boolean val) {
            matchHeight = val;
            return this;
        }

        public Builder posX(int val) {
            posX = val;
            return this;
        }

        public Builder posY(int val) {
            posY = val;
            return this;
        }

        public DialogHelperParams build() {
            return new DialogHelperParams(this);
        }
    }
}

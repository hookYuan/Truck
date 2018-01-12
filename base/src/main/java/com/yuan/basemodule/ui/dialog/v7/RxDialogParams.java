package com.yuan.basemodule.ui.dialog.v7;

import android.graphics.drawable.Drawable;
import android.view.Gravity;

import com.yuan.basemodule.ui.dialog.custom.IDialogAnimation;

/**
 * Created by YuanYe on 2017/9/20.
 * 构建RxDialog基本参数设置
 */
public class RxDialogParams {

    private float width = 0;//Dialog宽
    private float height = 0;//Dialog高

    private int posX = 0;//Dialog左上角定点横坐标
    private int posY = 0;//Dialog左上角定点纵坐标

    private int gravity; //Dialog在屏幕的位置快捷设置

    private int externalGravity;//Dialog相对于目标View的方向值

    private int marginTop = 0;//距离上面间隔
    private int marginLeft = 0;//距离左边间隔
    private int marginRight = 0;//距离右边间隔
    private int marginBottom = 0;//距离下面间隔

    private IDialogAnimation animation;//动画

    private Drawable backgroundDrawable;//Dialog背景Drawable

    private boolean outsideCancle;//点击外部消失


    private RxDialogParams(Builder builder) {
        width = builder.width;
        height = builder.height;
        posX = builder.posX;
        posY = builder.posY;
        marginTop = builder.marginTop;
        marginLeft = builder.marginLeft;
        marginRight = builder.marginRight;
        marginBottom = builder.marginBottom;
        animation = builder.animation;
        backgroundDrawable = builder.backgroundDrawable;
        outsideCancle = builder.outsideCancle;
        gravity = builder.gravity;
        externalGravity = builder.externalGravity;
    }

    public boolean isOutsideCancle() {
        return outsideCancle;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }


    public IDialogAnimation getAnimation() {
        return animation;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public int getGravity() {
        return gravity;
    }

    public int getExternalGravity() {
        return externalGravity;
    }

    public static final class Builder {
        private float width;
        private float height;
        private int posX;
        private int posY;
        private int marginTop;
        private int marginLeft;
        private int marginRight;
        private int marginBottom;
        private IDialogAnimation animation;
        private Drawable backgroundDrawable;
        private boolean outsideCancle = true;
        private int gravity = Gravity.CENTER;
        private int externalGravity;

        public Builder() {
        }

        public Builder outsideCancle(boolean val) {
            outsideCancle = val;
            return this;
        }

        public Builder backgroundDrawable(Drawable val) {
            backgroundDrawable = val;
            return this;
        }

        public Builder width(float val) {
            width = val;
            return this;
        }

        public Builder height(float val) {
            height = val;
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

        public Builder marginTop(int val) {
            marginTop = val;
            return this;
        }

        public Builder marginLeft(int val) {
            marginLeft = val;
            return this;
        }

        public Builder marginRight(int val) {
            marginRight = val;
            return this;
        }

        public Builder marginBottom(int val) {
            marginBottom = val;
            return this;
        }

        public Builder animation(IDialogAnimation val) {
            animation = val;
            return this;
        }

        public Builder gravity(int val) {
            gravity = val;
            return this;
        }
        public Builder externalGravity(int val) {
            externalGravity = val;
            return this;
        }

        public RxDialogParams build() {
            return new RxDialogParams(this);
        }
    }
}

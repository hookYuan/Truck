package com.yuan.album.bean;

import java.io.Serializable;

/**
 * 相册选择器配置器
 * Created by Administrator on 2016/8/24.
 */
public class PhotoConfigure implements Serializable {
    private boolean isSingle = true;// 是否单选
    private boolean isCamera = true;// 是否有相机
    private int num = 0;// 最多选择张数

    public PhotoConfigure() {
        super();
    }

    public PhotoConfigure(boolean isCamera, int num) {
        this.isCamera = isCamera;
        this.num = num;
    }

    public boolean isSingle() {
        if(num>1){ //
            return false;
        }
        return true;
    }

    public boolean isCamera() {
        return isCamera;
    }

    public void setCamera(boolean camera) {
        isCamera = camera;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "PhotoConfigure{" +
                "isSingle=" + isSingle +
                ", isCamera=" + isCamera +
                ", num=" + num +
                '}';
    }
}
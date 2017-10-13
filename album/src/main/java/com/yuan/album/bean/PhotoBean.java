package com.yuan.album.bean;

/**
 * Created by YuanYe on 2017/10/13.
 */

public class PhotoBean {

    private String imgPath;  //图片路径
    private String imgParentPath;//照片的目录

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgParentPath() {
        return imgParentPath;
    }

    public void setImgParentPath(String imgParentPath) {
        this.imgParentPath = imgParentPath;
    }
}

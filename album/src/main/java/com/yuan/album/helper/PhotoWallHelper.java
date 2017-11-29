package com.yuan.album.helper;

import com.yuan.album.bean.PhotoBean;

import java.util.List;

/**
 * Created by YuanYe on 2017/11/29.
 * 用于全局存储图片数据集合，在Activity之间传递
 * 注：使用Intent传递数据，当数据量大时，会出现异常
 */

public class PhotoWallHelper {

    private List<PhotoBean> data;

    public List<PhotoBean> getData() {
        return data;
    }

    public void setData(List<PhotoBean> data) {
        this.data = data;
    }

    private static final PhotoWallHelper holder = new PhotoWallHelper();

    private PhotoWallHelper() {
    }

    public static PhotoWallHelper getInstance() {
        return holder;
    }
}

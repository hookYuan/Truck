package com.yuan.album.helper;

import android.text.TextUtils;

import com.yuan.album.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/11/29.
 * 用于全局存储图片数据集合，在Activity之间传递
 * 注：使用Intent传递数据，当数据量大时，会出现异常
 */

public class PhotoPreviewHelper {

    private ArrayList<String> data;  //手机照片完整路径

    private static final PhotoPreviewHelper holder = new PhotoPreviewHelper();

    private PhotoPreviewHelper() {
    }

    public static PhotoPreviewHelper getInstance() {
        return holder;
    }

    public ArrayList<String> getData() {
        if (data == null) throw new NullPointerException("未添加照片数据，请添加后重试");
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    /**
     * photoBean  转化为 String 路径
     *
     * @param photoBeanList
     */
    public ArrayList<String> setPhotoBean(List<PhotoBean> photoBeanList) {
        if (data == null) data = new ArrayList<>();
        else data.clear();
        for (int i = 0; i < photoBeanList.size(); i++) {
            if (!TextUtils.isEmpty(photoBeanList.get(i).getImgPath())) {
                data.add(photoBeanList.get(i).getImgPath());
            }
        }
        return data;
    }

}

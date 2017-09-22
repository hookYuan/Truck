package com.yuan.album.util;

/**
 * 作者：yuanYe创建于2016/12/29
 * QQ：962851730
 */

public class BaseUtil {
    /**判断该文件是否是一个图片。*/
    public static boolean isImage(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }
}

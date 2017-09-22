package com.yuan.basemodule;

/**
 * Created by wanglei on 2016/12/4.
 */

public class XConfig {

    /******************************* 缓存相关设置 (缓存父目录为Android/data)************************************/
    // 偏好设置默认Key
    public static final String CACHE_SP_NAME = "config";
    // #cache  自定义缓存文件名
    public static final String CACHE_DISK_DIR = "cache";
    // #netcache  网络缓存文件名
    public static final String NET_CACHE = "NetCache";
    public static final long MAX_DIR_SIZE = Runtime.getRuntime().maxMemory() / 8;
    // # dev model
    public static final boolean DEV = true;

}

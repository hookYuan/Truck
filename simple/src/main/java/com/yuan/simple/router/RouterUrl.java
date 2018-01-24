package com.yuan.simple.router;

/**
 * Created by YuanYe on 2017/7/29.
 * 跳转管理界面，界面跳转，统一管理
 */

public class RouterUrl {
    /**
     * 启动登陆界面
     */
    public static String loginActivity = "/user/view/login/ui/LoginActivity";
    /**
     * 启动相册界面
     */
    public static String photoWallActivity = "/album/selectImage/PhotoWallActivity";

    /**
     * 启动图片预览界面
     */
    public static String photoPreviewActivity = "/album/selectImage/PhotoPreviewActivity";

    /**
     * 相册选择测试界面
     */
    public static String selectAlbumActivity = "/activity/two/selectalbumactivity";

    /**
     * 启动城市选择界面
     */
    public static String mapSelectCityActivity = "/map/citylist/ui/MapSelectCityActivity";
}

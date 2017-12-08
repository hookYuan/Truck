package com.yuan.album;

/**
 * Created by YuanYe on 2017/7/30.
 * 相册全局配置说明类
 * 一、启动图片选择器
 * 分为三种种启动方式：1.app模块启动，直接调用AlbumWallActivity启动
 * 2.app模块启动，调用AlbumWallActivity的open静态方法启动
 * 3.module模块启动，使用ARouter启动，地址为‘/album/selectImage/AlbumWallAct’
 * <p>
 * 二、启动图片预览器
 * 分为三种种启动方式：1.app模块启动，直接调用PhotoPreviewActivity启动
 * 2.app模块启动，调用PhotoPreviewActivity的open静态方法启动
 * 3.module模块启动，使用ARouter启动，地址为‘/album/selectImage/PhotoPreview’
 */
public class Config {
    /**
     * ******************************通用配置说明******************************************
     */
    public final static int REQUESTCAMERA = 10001;//打开相机请求码
    public final static String APP_DIR_NAME = "album";  //应用根目录名

    public final static String FILE_DIR_NAME = "camera"; //拍照文件目录名
    public final static String CROP_DIR_NAME = "crop";//剪裁图片保存目录名
    /**
     * *******************************图库选择器配置说明***************************************
     */
    /**********请求主题************/
    public final static int ALBUMTHEME_LIGHT = 20100; //相册选择器默认主题一
    public final static int ALBUMTHEME_BLACK = 20101; //相册选择器默认主题二
    public final static int ALBUMTHEME_BLUE = 20102; //相册选择器默认主题三
    public static String KEYTHEME = "ALBUMTHEME"; //启动图库时，主题key值,参数为以上常量
    /**********请求基础配置************/
    public static String NUM = "num";//启动图库时，需要获取图片的数量key值,参数类型为int
    public static String CAMERA = "camera"; //是否启动相机key值,参数类型为boolean
    public static String CROP = "crop"; //是否启动剪裁key值，参数类型为boolean
    public static String CROPW = "cropW"; //剪裁宽度key值，参数类型为int,可以不指定输出大小
    public static String CROPH = "cropH"; //剪裁高度key值，参数类型为int,可以不指定输出大小


    /**********返回结果部分**********/
    public static int PHOTOWALLREQUEST = 20001;   //图库选择请求码，在ActivityForResult中判断
    public static String KEYRESULT = "albumResult";//图库选择key,在ActivityForResult中获取数据

    /**
     * *******************************图片剪裁器配置说明***************************************
     */
    public static int IMAGECROPREQUEST = 30001;   //图片剪裁请求码，在ActivityForResult中判断
    public static int IMAGECROPPREVIEWREQUEST = 30002;   //图片剪裁预览请求码，在ActivityForResult中判断
    public static String CROPSHAP = "CROPSHAP"; //剪裁形状key，参数类型为int  0--正方形，1--圆形，2--长方形
    public static String CROPPATH = "CROPPATH"; //被剪裁的图片地址，参数类型为String
}

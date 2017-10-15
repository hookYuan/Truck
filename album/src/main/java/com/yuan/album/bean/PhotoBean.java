package com.yuan.album.bean;

/**
 * Created by YuanYe on 2017/10/13.
 */

public class PhotoBean {

    private String imgPath;  //图片路径
    private String imgParentPath;//照片所在的目录

    private String aperture;//光圈值
    private String datetime;//拍摄时间
    private String exposureTime;//曝光时间
    private String flash;//闪光灯
    private String focalLength;//焦距
    private String imageLength;//图片高度
    private String imageWidth;//图片宽度
    private String ISO;//iso
    private String make;//设备品牌
    private String model;//设备型号
    private String orientation;//旋转角度
    private String whiteBalance;//白平衡

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

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getExposureTime() {
        return exposureTime;
    }

    public void setExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
    }

    public String getFlash() {
        return flash;
    }

    public void setFlash(String flash) {
        this.flash = flash;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getImageLength() {
        return imageLength;
    }

    public void setImageLength(String imageLength) {
        this.imageLength = imageLength;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getISO() {
        return ISO;
    }

    public void setISO(String ISO) {
        this.ISO = ISO;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getWhiteBalance() {
        return whiteBalance;
    }

    public void setWhiteBalance(String whiteBalance) {
        this.whiteBalance = whiteBalance;
    }
}

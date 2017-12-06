package com.yuan.album.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by YuanYe on 2017/10/13.
 */
public class PhotoBean implements Parcelable {

    private boolean isSelect;//是否选中
    private int resID;//图片资源ID
    private String imgPath; //图片路径
    private String imgThumbnailID;//图片缩略图路径
    private String imgParentName;//照片父目录名
    private String imgParentPath;//照片所在文件夹的路径

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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgThumbnailID() {
        return imgThumbnailID;
    }

    public void setImgThumbnailID(String imgThumbnailID) {
        this.imgThumbnailID = imgThumbnailID;
    }

    public String getImgParentName() {
        return imgParentName;
    }

    public void setImgParentName(String imgParentName) {
        this.imgParentName = imgParentName;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.resID);
        dest.writeString(this.imgPath);
        dest.writeString(this.imgThumbnailID);
        dest.writeString(this.imgParentName);
        dest.writeString(this.imgParentPath);
        dest.writeString(this.aperture);
        dest.writeString(this.datetime);
        dest.writeString(this.exposureTime);
        dest.writeString(this.flash);
        dest.writeString(this.focalLength);
        dest.writeString(this.imageLength);
        dest.writeString(this.imageWidth);
        dest.writeString(this.ISO);
        dest.writeString(this.make);
        dest.writeString(this.model);
        dest.writeString(this.orientation);
        dest.writeString(this.whiteBalance);
    }

    public PhotoBean() {
    }

    protected PhotoBean(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.resID = in.readInt();
        this.imgPath = in.readString();
        this.imgThumbnailID = in.readString();
        this.imgParentName = in.readString();
        this.imgParentPath = in.readString();
        this.aperture = in.readString();
        this.datetime = in.readString();
        this.exposureTime = in.readString();
        this.flash = in.readString();
        this.focalLength = in.readString();
        this.imageLength = in.readString();
        this.imageWidth = in.readString();
        this.ISO = in.readString();
        this.make = in.readString();
        this.model = in.readString();
        this.orientation = in.readString();
        this.whiteBalance = in.readString();
    }

    public static final Creator<PhotoBean> CREATOR = new Creator<PhotoBean>() {
        @Override
        public PhotoBean createFromParcel(Parcel source) {
            return new PhotoBean(source);
        }

        @Override
        public PhotoBean[] newArray(int size) {
            return new PhotoBean[size];
        }
    };
}

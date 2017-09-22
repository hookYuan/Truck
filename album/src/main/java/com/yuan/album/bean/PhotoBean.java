package com.yuan.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：yuanYe创建于2016/12/23
 * QQ：962851730
 */

public class PhotoBean implements Parcelable {
    private boolean isSelect = false;
    private String image_url;  //原图
    private int position; //照片的位置编号

    private String photoAlbumName;//照片的目录

    public String getPhotoAlbumName() {
        return photoAlbumName;
    }

    public void setPhotoAlbumName(String photoAlbumName) {
        this.photoAlbumName = photoAlbumName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PhotoBean(){

    }

    public PhotoBean(String image_url, int position) {
        this.image_url = image_url;
        this.position = position;
    }

    public PhotoBean(String image_url){
        this.image_url = image_url;
    }
    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.image_url);
        dest.writeInt(this.position);
        dest.writeString(this.photoAlbumName);
    }

    protected PhotoBean(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.image_url = in.readString();
        this.position = in.readInt();
        this.photoAlbumName = in.readString();
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

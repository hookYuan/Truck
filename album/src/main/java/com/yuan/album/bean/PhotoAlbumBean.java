package com.yuan.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：yuanYe创建于2016/12/29
 * QQ：962851730
 * 相册的bean,用于展示相册列表
 */

public class PhotoAlbumBean implements Parcelable {
    private boolean isSelect = false; //是否选中
    private String image_url;  //原图
    private String albumName;//相册名
    private String albumPath;//相册的绝对路径
    private int imageNumber;//相册的数量


    public PhotoAlbumBean(String image_url, String albumName, int imageNumber) {
        this.image_url = image_url;
        this.albumName = albumName;
        this.imageNumber = imageNumber;
    }
    public PhotoAlbumBean(String image_url, String albumName, int imageNumber,boolean isSelect) {
        this.image_url = image_url;
        this.albumName = albumName;
        this.imageNumber = imageNumber;
        this.isSelect = isSelect;
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

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.image_url);
        dest.writeString(this.albumName);
        dest.writeInt(this.imageNumber);
    }

    public PhotoAlbumBean() {

    }

    protected PhotoAlbumBean(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.image_url = in.readString();
        this.albumName = in.readString();
        this.imageNumber = in.readInt();
    }

    public static final Creator<PhotoAlbumBean> CREATOR = new Creator<PhotoAlbumBean>() {
        @Override
        public PhotoAlbumBean createFromParcel(Parcel source) {
            return new PhotoAlbumBean(source);
        }

        @Override
        public PhotoAlbumBean[] newArray(int size) {
            return new PhotoAlbumBean[size];
        }
    };
}

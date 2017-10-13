package com.yuan.album.bean;

/**
 * Created by YuanYe on 2017/10/13.
 * 相册实体Bean
 */
public class AlbumBean {

    private String imgPath;  //封面图
    private String albumName;//相册名
    private String albumPath;//相册的绝对路径
    private int number;//相册的数量

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumPath() {
        return albumPath;
    }

    public void setAlbumPath(String albumPath) {
        this.albumPath = albumPath;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

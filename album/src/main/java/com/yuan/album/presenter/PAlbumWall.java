package com.yuan.album.presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.yuan.album.bean.AlbumBean;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.AlbumWallActivity;
import com.yuan.album.util.BaseUtil;
import com.yuan.basemodule.common.other.MediaFile;
import com.yuan.basemodule.common.other.RxUtil;
import com.yuan.basemodule.ui.base.mvp.XPresenter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by YuanYe on 2017/10/13.
 */


public class PAlbumWall extends XPresenter<AlbumWallActivity> {

    private final static String TAG = "PAlbumWall";

    private ArrayList<PhotoBean> allPhotos; //所有照片集合(缓存目录，原始数据)
    private ArrayList<AlbumBean> allAlbums; //所有相册目录集合（缓存目录，原始数据）


    public void addOnePhoto(PhotoBean photoBean) {
        if (allPhotos == null) {
            allPhotos = new ArrayList<>();
        }
        if (getV().isCamera) {
            allPhotos.add(1, photoBean);
        } else {
            allPhotos.add(0, photoBean);
        }
    }

    public List<PhotoBean> getAllPhotos() {
        if (allPhotos == null) {
            try {
                throw new NullPointerException("尚未初始化相册数据");
            } catch (Exception e) {

            }
        }
        return allPhotos;
    }

    /**
     * 使用ContentProvider读取SD卡最近图片。
     */
    public void initDB() {
        if (allAlbums == null) {
            allAlbums = new ArrayList<>();
        }
        if (allPhotos == null) {
            allPhotos = new ArrayList<>();
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> observer) throws Exception {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                //查询目标目录
                String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Files.FileColumns._ID};
                //查询条件
                String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;

                ContentResolver mContentResolver = getV().getContentResolver();
                // 只查询jpg和png的图片,按最新修改排序
                Cursor cursor = mContentResolver.query(mImageUri, projection,
                        key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                        new String[]{"image/jpg", "image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                if (cursor != null) {
                    //从最新的图片开始读取.
                    //当cursor中没有数据时，cursor.moveToLast()将返回false
                    if (cursor.moveToLast()) {
                        //路径缓存，防止多次扫描同一目录
                        HashSet<String> cachePath = new HashSet<String>();
                        while (true) {
                            // 获取图片的路径
                            String path = cursor.getString(0);
                            String thumbnail = cursor.getString(1);
                            //过滤其他格式文件，只保留图片文件
                            if (MediaFile.isImageFileType(path)) {
                                File parentFile = new File(path).getParentFile();
                                String parentName = parentFile.getName();
                                PhotoBean photoBean = new PhotoBean();
                                photoBean.setImgPath(path);
                                photoBean.setImgThumbnailID(thumbnail);
                                photoBean.setImgParentName(parentName);
                                allPhotos.add(photoBean);
                                //获取所有相册目录
                                String parentPath = parentFile.getAbsolutePath();
                                if (!cachePath.contains(parentPath)) {
                                    AlbumBean albumBean = new AlbumBean();
                                    albumBean.setAlbumName(parentName);
                                    albumBean.setImgPath(getFirstImagePath(parentFile));
                                    albumBean.setAlbumPath(parentPath);
                                    albumBean.setNumber(getImageCount(parentFile));
                                    allAlbums.add(albumBean);
                                    cachePath.add(parentPath);
                                }
                            }
                            if (!cursor.moveToPrevious()) {
                                break;
                            }
                        }
                    }
                    cursor.close();
                }
                observer.onNext(1000);
            }
        }).compose(RxUtil.<Integer>io_main())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        if (integer == 1000) { //数据加载完成
                            //添加所有相册选项
                            AlbumBean albumBean = new AlbumBean();
                            albumBean.setAlbumName("所有照片");
                            albumBean.setNumber(allPhotos.size());
                            albumBean.setImgPath(allPhotos.get(0).getImgPath());
                            allAlbums.add(0, albumBean);

                            if (getV().isCamera) {
                                PhotoBean bean = new PhotoBean();
                                bean.setImgParentName("相机");
                                allPhotos.add(0, bean);
                            }
                            getV().initView(allPhotos);
                            getV().initCatalog(allAlbums);
                        }
                    }
                });
    }

    /**
     * 根据相册获取照片集合
     */
    public List<PhotoBean> getPhotosForAlbum(String albumName) {
        List<PhotoBean> photos = new ArrayList<PhotoBean>();
        for (int i = 0; i < getAllPhotos().size(); i++) {
            if (albumName.equals(
                    getAllPhotos().get(i).getImgParentName())) {
                photos.add(getAllPhotos().get(i));
            }
        }
        return photos;
    }

    /**
     * 获取照片详细信息,单张图片查看时，补充填写详细信息
     */
    public void getPhotoInfo(String path, PhotoBean photoBean) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
            String TAG_APERTURE = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
            String TAG_DATETIME = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            String TAG_EXPOSURE_TIME = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME);
            String TAG_FLASH = exifInterface.getAttribute(ExifInterface.TAG_FLASH);
            String TAG_FOCAL_LENGTH = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            String TAG_IMAGE_LENGTH = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String TAG_IMAGE_WIDTH = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            String TAG_ISO = exifInterface.getAttribute(ExifInterface.TAG_ISO);
            String TAG_MAKE = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String TAG_MODEL = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            String TAG_ORIENTATION = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            String TAG_WHITE_BALANCE = exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);

            photoBean.setAperture(TAG_APERTURE);
            photoBean.setDatetime(TAG_DATETIME);
            photoBean.setExposureTime(TAG_EXPOSURE_TIME);
            photoBean.setFlash(TAG_FLASH);
            photoBean.setFocalLength(TAG_FOCAL_LENGTH);
            photoBean.setImageLength(TAG_IMAGE_LENGTH);
            photoBean.setImageWidth(TAG_IMAGE_WIDTH);
            photoBean.setISO(TAG_ISO);
            photoBean.setMake(TAG_MAKE);
            photoBean.setModel(TAG_MODEL);
            photoBean.setOrientation(TAG_ORIENTATION);
            photoBean.setWhiteBalance(TAG_WHITE_BALANCE);
        } catch (IOException e) {
        }
    }

    /**
     * 获取目录中最新的一张图片的绝对路径。
     */
    private String getFirstImagePath(File folder) {
        File[] files = folder.listFiles();
        for (int i = files.length - 1; i >= 0; i--) {
            File file = files[i];
            if (BaseUtil.isImage(file.getName())) {
                Log.i("222222", "getAbsolutePath=" + file.getAbsolutePath());
                Log.i("222222", "getName=" + file.getName());
                Log.i("222222", "getPath=" + file.getPath());
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 获取目录中图片的个数。
     * TODO 待优化（该算法数量不准确）
     */
    private int getImageCount(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            if (BaseUtil.isImage(file.getName())) {
                count++;
            }
        }
        return count;
    }
}

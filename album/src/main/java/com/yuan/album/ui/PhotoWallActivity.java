package com.yuan.album.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.yuan.basemodule.common.adapter.BaseListAdapter;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.album.R;
import com.yuan.album.base.OnClickListener;
import com.yuan.album.bean.PhotoAlbumBean;
import com.yuan.album.bean.PhotoBean2;
import com.yuan.album.bean.PhotoConfigure;
import com.yuan.album.util.BaseUtil;
import com.yuan.album.util.PopupWindowUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 作者：yuanYe创建于2016/12/22
 * 照片墙
 * 1.清单文件注册PhotoWallActivity
 * 2.添加读写权限
 * 3.导入photo_wall.xml和photo_wall_item.xml文件
 * 4.所有的toolbar的文字的图标调用baseActivity的方法修改
 * 5.导入相机图片 album_camera.pngra.png
 */
@Route(path = "/album/selectImage/PhotoWallActivity")
public class PhotoWallActivity extends ExtraActivity {

    private ArrayList<PhotoBean2> allPhotoList;  //本地所有的照片集合
    private ArrayList<PhotoBean2> selectPhotoList;  //选中的照片集合
    private ArrayList<PhotoAlbumBean> albumData; //所有相册基本信息集合
    private GridView photo_wall_grid;
    private int maxCount; //照片墙最多显示的数量
    static OnHandlerResultCallback callback; //回调
    private PhotoConfigure photoConfigure;
    private BaseAdapter adapter;
    private Button btn_look;//预览按钮
    private String cameraUri = "/HHImages"; //拍照 照片的保存文件夹
    private Uri mUri; // 照片保存的uri完整路径
    private Button btn_file; //查看相册
    private ListView listView;
    private BaseAdapter albumAdapter; //相册Adapter
    private ArrayList<PhotoBean2> allPhotoListCopy;
    private boolean isCamera;//保存最开始的是否显示照片状态
    private final int SD_REQUEST_CODE = 1002; //检查SD卡读写权限
    private final int CAMERA_REQUEST_CODE = 1003; //检查相机权限

    // 跳转到该界面
    public static void openImageSelecter(Activity activity, PhotoConfigure photoConfigure, OnHandlerResultCallback callback) {
        PhotoWallActivity.callback = callback;
        Intent intent = new Intent(activity, PhotoWallActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("photoConfigure", photoConfigure);
        intent.putExtras(mBundle);
        activity.startActivity(intent);
    }

    /**
     * 权限SD检查
     */
    public void permissionSDCheck() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ) {
            //进入到这里代表没有权限.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE}, SD_REQUEST_CODE);
        }
    }

    public void permissionCAMERACheck() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {
            //进入到这里代表没有权限.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA
            }, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SD_REQUEST_CODE: //SD卡权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //用户拒绝授权
                    Toast.makeText(PhotoWallActivity.this, "手机读写权限获取失败,请在手机设置中打开", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAMERA_REQUEST_CODE: //相机权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权

                } else {
                    //用户拒绝授权
                    Toast.makeText(PhotoWallActivity.this, "获取相机权限失败，请在手机设置中打开", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /**
     * 初始化数据集合
     */
    private void initData() {
        photoConfigure = (PhotoConfigure) getIntent().getExtras().getSerializable("photoConfigure");
        if (photoConfigure == null) {
            photoConfigure = new PhotoConfigure();
            photoConfigure.setNum(getIntent().getIntExtra("num", 1));
            photoConfigure.setCamera(getIntent().getBooleanExtra("camara", true));
        }

        isCamera = photoConfigure.isCamera();
        selectPhotoList = new ArrayList<>();
        allPhotoList = new ArrayList<>();
        //初始化相册数据
        albumData = new ArrayList<PhotoAlbumBean>();

        if (photoConfigure.isCamera()) { //显示相机
            allPhotoList.add(new PhotoBean2("album_camera"));
        }
        maxCount = 100000;
        getLatestImagePaths(maxCount);
        allPhotoListCopy = new ArrayList<>(allPhotoList);
        //为相册添加所有照片
        albumData.add(0, new PhotoAlbumBean(allPhotoList.get(1).getImage_url(), "所有图片", allPhotoList.size(), true));
        //设置标题
        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "图片")
                .setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setRightAsButton(R.drawable.selector_base_circular)
                .setRightText("完成", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击完成的点击事件
                        if (!photoConfigure.isSingle()) {
                            if (callback != null)
                                callback.onHandlerSuccess(selectPhotoList);
                            Intent intent = getIntent();
                            intent.putParcelableArrayListExtra("select_list", selectPhotoList);
                            setResult(RESULT_OK, intent);
                        }
                        finish();
                    }
                });
        if (!photoConfigure.isSingle()) {
            //多选
            updateRightView();
        }
    }

    //更改多选按钮
    public void updateRightView() {
        if (selectPhotoList.size() == 0) {
            getTitleBar().setRightText("完成")
                    .setRightClickEnable(false);
        } else if (selectPhotoList.size() <= photoConfigure.getNum()) {
            getTitleBar().setRightText("完成(" + selectPhotoList.size() + "/" + photoConfigure.getNum() + ")")
                    .setRightClickEnable(true);
        }
    }


    private void initView() {
        photo_wall_grid = (GridView) findViewById(R.id.photo_wall_grid);
        adapter = new BaseListAdapter<PhotoBean2>(allPhotoList, R.layout.album_photo_wall_item) {
            @Override
            public void bindView(ViewHolder holder, PhotoBean2 obj) {
                final ImageView image = holder.getView(R.id.photo_wall_item_photo);
                final CheckBox checkBox = holder.getView(R.id.photo_wall_item_cb);
                final int index = holder.getItemPosition();
                //相机的时候
                if ("album_camera".equals(allPhotoList.get(holder.getItemPosition()).getImage_url())) {
                    Glide.with(PhotoWallActivity.this).load(R.mipmap.album_bg).crossFade(300).placeholder(R.mipmap.album_bg).into(image);
                    checkBox.setVisibility(View.GONE);
                    holder.getView(R.id.ll_camera).setVisibility(View.VISIBLE);
                    //TODO 需要更改为自己的相机图标
                    ((ImageView) holder.getView(R.id.iv_camera)).setImageResource(R.mipmap.album_camera);
                } else {
                    // placeholder:图片显示前的占位符
                    // crossFade:淡入淡出动画
                    allPhotoList.get(index).setPosition(index);
                    checkBox.setVisibility(View.VISIBLE);
                    Glide.with(PhotoWallActivity.this).load(obj.getImage_url()).crossFade(300).placeholder(R.mipmap.album_bg).into(image);
                    holder.getView(R.id.ll_camera).setVisibility(View.GONE);
                }

                //设置checkbox
                if (photoConfigure.isSingle()) {
                    //如果是单选--更改界面显示
                    checkBox.setVisibility(View.GONE);
                } else {
                    //多选的时候
                    /**
                     * 设置多选框
                     */
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //设置选中的
                            if (!checkBox.isChecked()) {
                                allPhotoList.get(index).setSelect(false);
                                image.setColorFilter(Color.parseColor("#00ffffff"));
                                //移除已有对象
                                for (int i = 0; i < selectPhotoList.size(); i++) {
                                    if (selectPhotoList.get(i).getPosition() == allPhotoList.get(index).getPosition()) {
                                        selectPhotoList.remove(i);
                                    }
                                }
                            } else if (selectPhotoList.size() < photoConfigure.getNum()) {
                                allPhotoList.get(index).setSelect(true);
                                image.setColorFilter(Color.parseColor("#66000000"));
                                selectPhotoList.add(allPhotoList.get(index));
                            } else {
                                checkBox.setChecked(false);
                                Toast.makeText(PhotoWallActivity.this, "你最多只能选择" + photoConfigure.getNum() + "张照片", Toast.LENGTH_SHORT).show();
                            }
                            updateRightView();
                        }
                    });
                    //防止checkbox滑动后混乱
                    checkBox.setChecked(allPhotoList.get(index).isSelect());
                    //防止checkbox选中背景滑动后混乱
                    if (allPhotoList.get(index).isSelect()) {
                        image.setColorFilter(Color.parseColor("#66000000"));
                    } else {
                        image.setColorFilter(Color.parseColor("#00ffffff"));
                    }
                }

                //设置图片点击事件
                image.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (index == 0 && photoConfigure.isCamera()) { //打开相机
                            //启动相机
                            doTakePhoto();
                        } else {
                            //跳转到图片查看界面(这里查看的是所有的照片)
                            PhotoPreviewActivity.openImagePreview(PhotoWallActivity.this, index, photoConfigure, allPhotoList, selectPhotoList, new PhotoPreviewActivity.OnHandlerResultCallback() {

                                @Override
                                public void onHandlerSuccess(List<PhotoBean2> selectList, ArrayList<PhotoBean2> allPhotoList) {
                                    //对返回值的处理
                                    selectPhotoList.clear();
                                    selectPhotoList.addAll(selectList);
                                    PhotoWallActivity.this.allPhotoList.clear();
                                    PhotoWallActivity.this.allPhotoList.addAll(allPhotoList);
                                    //刷新选中显示
                                    adapter.notifyDataSetChanged();
                                    //刷新toolbar
                                    updateRightView();
                                }
                            });
                        }
                    }
                });
            }
        };
        photo_wall_grid.setAdapter(adapter);

        //预览操作
        btn_look = (Button) findViewById(R.id.btn_look);
        btn_look.setOnClickListener(new OnClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //防止没有图片的预览
                if (selectPhotoList.size() == 0)
                    return;
                ArrayList<PhotoBean2> selectList = new ArrayList<PhotoBean2>(selectPhotoList);
                int currentPos = 0;
                if (photoConfigure.isCamera()) {
                    selectPhotoList.add(0, new PhotoBean2("album_camera"));
                    currentPos = 1;
                }
                //跳转到图片查看界面(这里查看的是所有的照片)
                PhotoPreviewActivity.openImagePreview(PhotoWallActivity.this, currentPos, photoConfigure, selectPhotoList, selectList, new PhotoPreviewActivity.OnHandlerResultCallback() {
                    @Override
                    public void onHandlerSuccess(List<PhotoBean2> selectList, ArrayList<PhotoBean2> allPhotoList) {
//                        //对返回值的处理
                        selectPhotoList.clear();
                        selectPhotoList.addAll(selectList);
                        //这里的allphotolist是预览的部分
                        Log.i("yuanye", "-----" + allPhotoList.size());
                        for (int i = 0; i < allPhotoList.size(); i++) {
                            Log.i("yuanye", "--po---" + allPhotoList.get(i).getPosition() + "-----" + allPhotoList.get(i).isSelect());
                            PhotoWallActivity.this.allPhotoList.get(allPhotoList.get(i).getPosition()).setSelect(allPhotoList.get(i).isSelect());
                        }
                        //刷新选中显示
                        adapter.notifyDataSetChanged();
                        //刷新toolbar
                        updateRightView();
                    }
                });
            }
        });

        //查看相册
        btn_file = (Button) findViewById(R.id.btn_file);
        listView = (ListView) findViewById(R.id.listView);
        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击弹出相册选择列表
                PopupWindowUtil.showMyWindow(listView);
            }
        });
        setPhotoAlbum();
    }

    /**
     * 分相册查看
     */
    public void setPhotoAlbum() {
        listView.setTag(0);
        albumAdapter = new BaseListAdapter<PhotoAlbumBean>(albumData, R.layout.album_photo_wall_album_item) {
            @Override
            public void bindView(ViewHolder holder, PhotoAlbumBean obj) {
                Glide.with(PhotoWallActivity.this).load(obj.getImage_url()).crossFade(300).placeholder(R.mipmap.album_bg).into((ImageView) holder.getView(R.id.iv_album));
                holder.setText(R.id.tv_album_name, obj.getAlbumName());
                holder.setText(R.id.tv_image_number, obj.getImageNumber() + "张");
                if ((int) listView.getTag() == holder.getItemPosition()) {
                    holder.getView(R.id.radioButton).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.radioButton).setVisibility(View.GONE);
                }
            }
        };
        listView.setAdapter(albumAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setTag(position);
                //更改相册显示数据
                allPhotoList.clear();

                if (position == 0) { //查看所有相册
                    photoConfigure.setCamera(isCamera);
                    allPhotoList.addAll(allPhotoListCopy);
                } else {
                    photoConfigure.setCamera(false);
//                    Log.i("yuanye12221","")
                    String albumName = albumData.get(position).getAlbumName();
                    Log.i("yuanye12221", "albumName--------" + albumName);
                    Log.i("yuanye12221", "getPhotoAlbumName()--------" + allPhotoListCopy.get(1).getPhotoAlbumName());
                    for (int i = 1; i < allPhotoListCopy.size(); i++) {
                        if (allPhotoListCopy.get(i).getPhotoAlbumName().equals(albumName)) {
                            allPhotoList.add(allPhotoListCopy.get(i));
                        }
                    }
                }
//                Glide.get(PhotoWallActivity.this).clearMemory();
                albumAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                PopupWindowUtil.showMyWindow(listView);

            }
        });
    }

    /**
     * 拍照获取相片
     **/
    private void doTakePhoto() {
        permissionCAMERACheck();
        if (selectPhotoList.size() >= photoConfigure.getNum()) {
            Toast.makeText(PhotoWallActivity.this, "您已选择超过" + photoConfigure.getNum() + "张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + cameraUri);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        mUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + cameraUri, String.valueOf(System.currentTimeMillis()) + ".jpg"));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(cameraIntent, 0);
    }

    //处理拍照返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                // 拍照返回照片
                Log.i("yuanye", mUri.getPath());
                try {
                    // 刷新在系统相册中显示(以下代码会自动保存到系统的相册目录)
//                    MediaStore.Images.Media.insertImage(getContentResolver(),
//                            MediaStore.Images.Media.getBitmap(this.getContentResolver(), mUri),
//                            getResources().getString(R.string.app_name), "");
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(mUri);
                    sendBroadcast(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //跳转到图片预览界面（这里只查看一张照片）
                ArrayList<PhotoBean2> allList = new ArrayList<>();
                allList.add(new PhotoBean2(mUri.getPath(), 1));
                ArrayList<PhotoBean2> selectList = new ArrayList<>(allList);
                if (photoConfigure.isCamera()) {
                    allList.add(0, new PhotoBean2("album_camera"));
                }
                PhotoPreviewActivity.openImagePreview(PhotoWallActivity.this, 1, photoConfigure, allList, selectPhotoList, new PhotoPreviewActivity.OnHandlerResultCallback() {
                    @Override
                    public void onHandlerSuccess(List<PhotoBean2> selectList, ArrayList<PhotoBean2> allPhotoList) {
                        //对返回值的处理
                        int selectSize = selectPhotoList.size();
                        selectPhotoList.clear();
                        selectPhotoList.addAll(selectList);
                        //刷新数据集合
                        PhotoBean2 bean = new PhotoBean2(mUri.getPath(), 1);
                        if (selectSize < selectPhotoList.size()) {
                            bean.setSelect(selectPhotoList.get(selectList.size() - 1).isSelect());
                        }
                        //保证位置的统一
                        for (PhotoBean2 bean_all : allPhotoList) {
                            bean_all.setPosition(bean_all.getPosition() + 1);
                        }
                        for (PhotoBean2 bean_all : selectPhotoList) {
                            bean_all.setPosition(bean_all.getPosition() + 1);
                        }
                        PhotoWallActivity.this.allPhotoList.add(1, bean);
                        //刷新选中显示
                        adapter.notifyDataSetChanged();
                        //刷新toolbar
                        updateRightView();
                    }
                });
            }
        }
    }

    /**
     * 使用ContentProvider读取SD卡最近图片。
     */
    private void getLatestImagePaths(int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;
        ContentResolver mContentResolver = getContentResolver();
        // 只查询jpg和png的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);
//        ArrayList<String> latestImagePaths = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
//                allPhotoList = new ArrayList<String>();
                //路径缓存，防止多次扫描同一目录
                HashSet<String> cachePath = new HashSet<String>();
                while (true) {
                    // 获取图片的路径
                    String path = cursor.getString(0);
                    File parentFile = new File(path).getParentFile();
                    String parentName = parentFile.getName();

                    PhotoBean2 photoBean2 = new PhotoBean2();
                    photoBean2.setImage_url(path);
                    photoBean2.setPhotoAlbumName(parentName);
                    allPhotoList.add(photoBean2);
                    if (allPhotoList.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }

                    //处理相册目录
                    String parentPath = parentFile.getAbsolutePath();

                    //不扫描重复路径
                    if (!cachePath.contains(parentPath)) {
                        albumData.add(new PhotoAlbumBean(getFirstImagePath(parentFile),
                                parentName, getImageCount(parentFile)));
                        cachePath.add(parentPath);
                    }
                }
            }
            cursor.close();
        }
    }

    /**
     * 获取目录中图片的个数。
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

    @Override
    public int getLayoutId() {
        return R.layout.album_photo_wall;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //6.0以上权限检查
        permissionSDCheck();
        initData();
        initView();
    }

    /**
     * 处理结果
     */
    public interface OnHandlerResultCallback {
        /**
         * 处理成功
         *
         * @param resultList
         */
        void onHandlerSuccess(List<PhotoBean2> resultList);
    }

}

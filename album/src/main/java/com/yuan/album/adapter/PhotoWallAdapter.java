package com.yuan.album.adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.AlbumWallAct;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.common.other.GoToSystemSetting;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;

import java.io.File;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by YuanYe on 2017/10/13.
 * 照片墙Adapter
 */
public class PhotoWallAdapter extends BaseAdapter implements View.OnClickListener {

    private AlbumWallAct mContext;
    private boolean isCamera;
    private int num;
    private List<PhotoBean> mData;


    public PhotoWallAdapter(Context mContext, List<PhotoBean> mData,
                            boolean isCamera, int num) {
        this.mContext = (AlbumWallAct) mContext;
        this.isCamera = isCamera;
        this.num = num;
        if (isCamera) {
            PhotoBean bean = new PhotoBean();
            mData.add(0, bean);
        }
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.album_photo_wall_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //初始化布局
        if (isCamera && i == 0) { //显示相机按钮
            holder.camera.setVisibility(View.VISIBLE);
            holder.select.setVisibility(View.GONE);
            holder.camera.setOnClickListener(this);
            GlideHelper.with(mContext).load(R.mipmap.album_bg)
                    .loading(R.mipmap.album_bg).into(holder.photo);
        } else {
            holder.camera.setVisibility(View.GONE);
            holder.select.setTag(R.id.album_wall_select_pos, i);
            holder.select.setTag(R.id.photo_wall_item_photo, holder.photo);
            holder.select.setOnClickListener(this);
            if (num > 1) { //多选
                holder.select.setVisibility(View.VISIBLE);
            } else {
                holder.select.setVisibility(View.GONE);
            }
            GlideHelper.with(mContext).load(mData.get(i).getImgPath())
                    .loading(R.mipmap.album_bg).into(holder.photo);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_camera) {//点击相机
            permissionCheck();
        }
        if (view.getId() == R.id.photo_wall_item_cb) {
            CheckBox checkBox = (CheckBox) view;
            int position = (int) view.getTag(R.id.album_wall_select_pos);
            ImageView photo = (ImageView) view.getTag(R.id.photo_wall_item_photo);
            if (!checkBox.isChecked()) { //未选中照片的时候
                photo.setColorFilter(Color.parseColor("#00ffffff"));
                mContext.getSelectPhotos().remove(mData.get(position));
            } else if (mContext.getSelectPhotos().size() <= num) { //选中的时候
                photo.setColorFilter(Color.parseColor("#66000000"));
                mContext.getSelectPhotos().add(mData.get(position));
            } else {
                ToastUtil.showShort(mContext, "你最多只能选择" + num + "张照片");
            }
        }
    }

    /**
     * 申请拍照权限（适配6.0以上系统）
     */
    private void permissionCheck() {
        new RxPermissions(mContext)
                .request(Manifest.permission.CAMERA
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            doTakePhoto();
                        } else {
                            //没有读写权限,跳转设置
                            new MaterialDialog().alertText(mContext, "提示", "使用相机功能，请先开启应用拍照权限", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    GoToSystemSetting.open(mContext);
                                }
                            });
                        }
                    }
                });
    }

    /**
     * 拍照获取相片
     **/
    private void doTakePhoto() {
        if (mContext.getSelectPhotos().size() >= num) {
            Toast.makeText(mContext, "您已选择" + num + "张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File appDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + AlbumWallAct.CAMERAURI);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        Uri mUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + AlbumWallAct.CAMERAURI, String.valueOf(System.currentTimeMillis()) + ".jpg"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //6.0以上
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.setDataAndType(mUri, "application/vnd.android.package-archive");
        } else {
            cameraIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        }
        mContext.startActivityForResult(cameraIntent, 0);
    }

    public class ViewHolder {
        ImageView photo;
        CheckBox select;
        LinearLayout camera;

        public ViewHolder(View itemView) {
            photo = (ImageView) itemView.findViewById(R.id.photo_wall_item_photo);
            select = (CheckBox) itemView.findViewById(R.id.photo_wall_item_cb);
            camera = (LinearLayout) itemView.findViewById(R.id.ll_camera);
        }
    }
}

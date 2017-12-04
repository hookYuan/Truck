package com.yuan.album.adapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alexvasilkov.events.Events;
import com.alexvasilkov.gestures.animation.ViewPosition;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.helper.PhotoWallHelper;
import com.yuan.album.ui.AlbumWallAct;
import com.yuan.album.ui.PhotoViewPageActivity;
import com.yuan.album.util.FileUtils;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.common.other.GoToSystemSetting;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by YuanYe on 2017/10/13.
 * 照片墙Adapter
 */
public class PhotoWallAdapter extends RecyclerView.Adapter<PhotoWallAdapter.ViewHolder> implements View.OnClickListener {

    private AlbumWallAct mContext;
    private boolean isCamera;
    private int num;

    private ArrayList<PhotoBean> mData;         //rlv数据集合
    private String mFilePath;                   //相机拍照保存路径
    private String mFileName;                   //相机拍照保存文件名

    private OnPaintingListener listener;

    public PhotoWallAdapter(Context context, ArrayList<PhotoBean> mData,
                            boolean isCamera, int num, OnPaintingListener listener) {
        this.mContext = (AlbumWallAct) context;
        this.isCamera = isCamera;
        this.num = num;
        this.mData = mData;
        this.listener = listener;
        FileUtils.init();
        mFilePath = FileUtils.getFileDir() + File.separator;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = Views.inflate(parent, R.layout.album_photo_wall_item);
        return onCreateHolder(view);
    }

    private ViewHolder onCreateHolder(View view) {
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setTag(R.id.album_wall_holder, holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //初始化布局
        if (isCamera && "所有照片".equals(mContext.getSelectAlbum()) && position == 0
                && "相机".equals(mData.get(0).getImgParentName())) { //显示相机按钮
            holder.camera.setVisibility(View.VISIBLE);
            holder.select.setVisibility(View.GONE);
            holder.camera.setOnClickListener(this);
            GlideHelper.with(mContext).load(R.mipmap.album_bg)
                    .loading(R.mipmap.album_bg).into(holder.photo);
        } else {
            holder.camera.setVisibility(View.GONE);
            holder.select.setTag(R.id.album_wall_select_pos, position);
            holder.select.setTag(R.id.photo_wall_item_photo, holder.photo);
            holder.select.setOnClickListener(this);
            holder.photo.setTag(R.id.album_wall_select_pos, position);
            holder.photo.setOnClickListener(this);
            GlideHelper.with(mContext).load(mData.get(position).getImgPath())
                    .loading(R.mipmap.album_bg).into(holder.photo);
            //多选
            if (num <= 1) {
                holder.select.setVisibility(View.GONE);
            } else {
                holder.select.setVisibility(View.VISIBLE);
                //设置照片是否选中
                if (!mData.get(position).getIsSelect()) { //未选中照片的时候
                    holder.photo.setColorFilter(Color.parseColor("#00ffffff"));
                    holder.select.setChecked(false);
                } else if (mContext.getSelectPhotos().size() < num) { //选中的时候
                    holder.photo.setColorFilter(Color.parseColor("#66000000"));
                    holder.select.setChecked(true);
                }
            }
        }
    }

    public static ImageView getImage(View itemView) {
        ViewHolder holder = (ViewHolder) itemView.getTag(R.id.album_wall_holder);
        return holder == null ? null : holder.photo;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_camera) {//点击相机
            permissionCheck();
        } else if (view.getId() == R.id.photo_wall_item_cb) { //checkbox
            CheckBox checkBox = (CheckBox) view;
            int position = (int) view.getTag(R.id.album_wall_select_pos);
            ImageView photo = (ImageView) view.getTag(R.id.photo_wall_item_photo);

            if (!checkBox.isChecked()) { //未选中照片的时候
                photo.setColorFilter(Color.parseColor("#00ffffff"));
                mData.get(position).setIsSelect(false);
                mContext.updateWall4One(mData.get(position));
            } else { //选中的时候
                if (mContext.getSelectPhotos().size() >= num) {
                    ToastUtil.showShort(mContext, "你最多只能选择" + num + "张照片");
                    checkBox.setChecked(false);
                    return;
                }
                photo.setColorFilter(Color.parseColor("#66000000"));
                //同步选中状态
                mData.get(position).setIsSelect(true);
                mContext.updateWall4One(mData.get(position));
            }
        } else if (view.getId() == R.id.photo_wall_item_photo) {
            //TODO 添加图片跳转炫酷动画
            int position = (int) view.getTag(R.id.album_wall_select_pos);
            listener.onPaintingClick(position);
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
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = new File(mFilePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            mFileName = System.currentTimeMillis() + ".jpg";
            File file = new File(path, mFileName);
            if (file.exists()) {
                file.delete();
            }
            mContext.mUriTakPhoto = Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0
                FileUtils.startActionCapture(mContext, file, Config.REQUESTCAMERA);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //6.0以上
                    cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    cameraIntent.setDataAndType(mContext.mUriTakPhoto, "application/vnd.android.package-archive");
                } else {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mContext.mUriTakPhoto);
                }
                mContext.startActivityForResult(cameraIntent, Config.REQUESTCAMERA);
            }
        } else {
            ToastUtil.showShort(mContext, "没有挂载存储空间");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        CheckBox select;
        LinearLayout camera;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo_wall_item_photo);
            select = (CheckBox) itemView.findViewById(R.id.photo_wall_item_cb);
            camera = (LinearLayout) itemView.findViewById(R.id.ll_camera);
        }
    }

    public interface OnPaintingListener {
        void onPaintingClick(int position);
    }

}

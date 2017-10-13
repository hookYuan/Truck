package com.yuan.album.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.BaseListAdapter;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.album.bean.PhotoBean2;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/activity/two/selectalbumactivity")
public class SelectAlbumActivity extends ExtraActivity implements ISwipeBack, AdapterView.OnItemClickListener {

    private int maxNum = 8;//最多选择图片的个数
    private ArrayList<String> imageSelect_01;
    private ArrayList<PhotoBean2> resultList = new ArrayList<>();
    private GridView grapeGridView;
    private BaseAdapter adapter;
    private final int REQUEST_CODE = 1002;

    @Override
    public int getLayoutId() {
        return R.layout.act_album_select;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar().setToolbar("图库选择")
                .setLeftIcon(R.drawable.ic_base_back_white)
                .setRightText("图库", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startSelect();
                    }
                });
        permissionCheck();
        imageSelect_01 = new ArrayList<>();
        imageSelect_01.add("add");
        grapeGridView = (GridView) findViewById(R.id.grapeGridView);
        grapeGridView.setAdapter(getAdapter());
        grapeGridView.setOnItemClickListener(this);
    }

    //图片选择回调处理
    private void startSelect() {
        //跳转到自定义图库选择
        if (imageSelect_01.size() >= maxNum + 1) return;
        RouterHelper.from(this)
                .put("num", maxNum - imageSelect_01.size())
                .put("camara", true)
                .to("/album/selectImage/PhotoWallActivity", Config.PHOTOWALLREQUEST);
    }

    public BaseAdapter getAdapter() {
        adapter = new BaseListAdapter<String>(imageSelect_01, com.yuan.album.R.layout.album_photo_wall_item) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                if (holder.getItemPosition() == imageSelect_01.size() - 1) {
                    holder.setVisibility(R.id.photo_wall_item_cb, View.GONE);
                    Glide.with(mContext).load(R.mipmap.album_ic_add).into((ImageView) holder.getView(R.id.photo_wall_item_photo));
                } else {
                    holder.setVisibility(R.id.photo_wall_item_cb, View.GONE);
                    Glide.with(mContext).load(obj).into((ImageView) holder.getView(R.id.photo_wall_item_photo));
                }
            }
        };
        return adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<PhotoBean2> resultList = data.getParcelableArrayListExtra("select_list");
            if (requestCode == Config.PHOTOWALLREQUEST) {
                SelectAlbumActivity.this.resultList.addAll(resultList);
                //返回的选中照片数据集合
                for (PhotoBean2 url : resultList) {
                    imageSelect_01.add(imageSelect_01.size() - 1, url.getImage_url());
                }
                adapter.notifyDataSetChanged();
            }
            if (requestCode == Config.PHOTOPREVIEWREQUEST) {
                SelectAlbumActivity.this.resultList.clear();
                SelectAlbumActivity.this.resultList.addAll(resultList);
                imageSelect_01.clear();
                imageSelect_01.add("add");
                //返回的选中照片数据集合
                for (PhotoBean2 url : resultList) {
                    imageSelect_01.add(imageSelect_01.size() - 1, url.getImage_url());
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 权限检查
     */
    public void permissionCheck() {
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {
            //进入到这里代表没有权限.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权

                } else {
                    //用户拒绝授权
                    Toast.makeText(mContext, "手机读写权限获取失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (imageSelect_01.size() - 1 == i) {
            //添加
            startSelect();
        } else {
            if (resultList.size() == 0)
                return;
            //防止没有图片的预览
            int currentPos = i;
            //调用图片预览界面
            RouterHelper.from(mContext)
                    .put("allPhotoList", resultList)
                    .put("selectPhotoList", resultList)
                    .put("position", currentPos)
                    .put("camara", false)
                    .put("num", maxNum - imageSelect_01.size())
                    .to("/album/selectImage/PhotoPreviewActivity", Config.PHOTOPREVIEWREQUEST);
        }
    }
}

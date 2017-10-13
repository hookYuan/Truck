package com.yuan.album.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.album.R;
import com.yuan.album.adapter.PhotoWallAdapter;
import com.yuan.album.presenter.PAlbumWall;
import com.yuan.basemodule.common.other.GoToSystemSetting;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 作者：yuanYe重构于2017/10/13
 * 照片墙
 * 为适应模块化开发，重构代码
 * 基于base模块开发，简化使用逻辑
 * 如果使用ARouter方式使用本类，需要传递以下参数:
 * Camera
 * num
 */
public class AlbumWallAct extends MVPActivity<PAlbumWall> {

    GridView wallGrid;      //内容GridView
    Button btnAllClassify   //相册分类按钮
            , btnPreview;   //预览按钮

    @Override
    protected void initData(Bundle savedInstanceState) {
        permissionCheck();
    }

    /**
     * 申请读写权限（适配6.0以上系统）
     */
    private void permissionCheck() {
        new RxPermissions(mContext)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //获取读写权限
                            initView();
                        } else {
                            //没有读写权限,跳转设置
                            new MaterialDialog().alertText(mContext, "提示", "使用相册功能，请先开启应用读写权限", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    GoToSystemSetting.open(mContext);
                                }
                            });
                        }
                    }
                });
    }

    Boolean isCamera;//是否显示相机
    public final static String ISCAMERA = "camera";
    int num; //需要选择照片的数量
    public final static String SELECTNUM = "num";

    private void initView() {
        wallGrid = (GridView) findViewById(R.id.photo_wall_grid);
        btnAllClassify = (Button) findViewById(R.id.btn_album_file);
        btnPreview = (Button) findViewById(R.id.btn_preview);

        isCamera = getIntent().getBooleanExtra(ISCAMERA, true);
        num = getIntent().getIntExtra(SELECTNUM, 0);

        wallGrid.setAdapter(new PhotoWallAdapter(mContext,getP().getAllPhotos()));

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_wall;
    }

}

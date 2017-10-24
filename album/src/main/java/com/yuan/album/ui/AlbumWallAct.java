package com.yuan.album.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.album.R;
import com.yuan.album.adapter.PhotoWallAdapter;
import com.yuan.album.adapter.PhotoWallAlbumAdapter;
import com.yuan.album.bean.AlbumBean;
import com.yuan.album.bean.PhotoAlbumBean;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.presenter.PAlbumWall;
import com.yuan.album.util.PopupWindowUtil;
import com.yuan.basemodule.common.other.GoToSystemSetting;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.dialog.custom.RxDialog;
import com.yuan.basemodule.ui.dialog.custom.RxTranslateAnimation;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

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
@Route(path = "/album/selectImage/AlbumWallAct")
public class AlbumWallAct extends MVPActivity<PAlbumWall> implements ISwipeBack, View.OnClickListener {

    private GridView wallGrid;      //内容GridView
    private Button btnAllClassify   //相册分类按钮
            , btnPreview;   //预览按钮
    private ListView catalog; //目录

    public final static String ISCAMERA = "camera";
    public final static String SELECTNUM = "num";
    public final static String CAMERAURI = "/HHImages"; //拍照 照片的保存文件夹
    private Boolean isCamera;//是否显示相机
    private int num; //需要选择照片的数量
    private List<PhotoBean> selectPhotos; //选中照片集合

    @Override
    protected void initData(Bundle savedInstanceState) {
        //设置statusbar的图标颜色高亮反转
        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "图片")
                .setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setRightAsButton(R.drawable.selector_base_circular)
                .setRightText("完成", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击完成的点击事件
                        finish();
                    }
                });
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


    private void initView() {
        getP().initDB(); //查询数据库，初始化照片数据
        catalog = (ListView) findViewById(R.id.lv_album_catalog);
        wallGrid = (GridView) findViewById(R.id.photo_wall_grid);
        btnAllClassify = (Button) findViewById(R.id.btn_album_file);
        btnPreview = (Button) findViewById(R.id.btn_preview);
        isCamera = getIntent().getBooleanExtra(ISCAMERA, true);
        num = getIntent().getIntExtra(SELECTNUM, 1);
    }

    /**
     * 显示数据到界面上
     */
    public void showOnAct(List<PhotoBean> allPhotos, List<AlbumBean> albums) {
        wallGrid.setAdapter(new PhotoWallAdapter(mContext, allPhotos, isCamera, num));
        btnAllClassify.setOnClickListener(AlbumWallAct.this);
        //设置相册目录数据
        catalog.setTag(0); //标记默认选中的数据
        catalog.setAdapter(new PhotoWallAlbumAdapter(mContext, albums, R.layout.album_photo_wall_album_item));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_album_file) {
            //点击弹出相册选择列表
            PopupWindowUtil.showMyWindow(catalog);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_wall;
    }

    public List<PhotoBean> getSelectPhotos() {
        if (selectPhotos == null) {
            selectPhotos = new ArrayList<>();
        }
        return selectPhotos;
    }
}

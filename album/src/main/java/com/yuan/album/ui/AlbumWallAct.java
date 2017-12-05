package com.yuan.album.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.SimpleTracker;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.album.adapter.PaintingsPagerAdapter;
import com.yuan.album.adapter.PhotoPagerAdapter;
import com.yuan.album.adapter.PhotoWallAdapter;
import com.yuan.album.adapter.PhotoWallAlbumAdapter;
import com.yuan.album.bean.AlbumBean;
import com.yuan.album.bean.PhotoAlbumBean;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.presenter.PAlbumWall;
import com.yuan.album.util.GridDivider;
import com.yuan.album.util.PopupWindowUtil;
import com.yuan.album.util.RLVDivider;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.common.other.GoToSystemSetting;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.dialog.custom.RxDialog;
import com.yuan.basemodule.ui.dialog.custom.RxTranslateAnimation;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;

import java.io.File;
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
public class AlbumWallAct extends MVPActivity<PAlbumWall> implements ISwipeBack,
        View.OnClickListener, PhotoWallAdapter.OnPaintingListener {

    private RecyclerView rlvWall;               //recyclerView
    private Button btnAllClassify               //相册分类按钮
            , btnPreview;                       //预览按钮
    private ListView catalog;                   //目录列表
    private ViewPager viewPager;                //相册预览viewPage
    private View background;                    //动画背景view


    public final static String ISCAMERA = "camera";
    public final static String SELECTNUM = "num";

    public Boolean isCamera;               //是否显示相机
    private int num;                        //需要选择照片的数量

    private ArrayList<PhotoBean> selectPhotos;      //选中照片集
    private ArrayList<PhotoBean> allPhotos;         //照片墙数据集
    private ArrayList<PhotoBean> pagerPhotos;        //viewPager数据集

    private PhotoWallAdapter wallAdapter;           //Album adapter
    private PaintingsPagerAdapter pagerAdapter;          //ViewPager adapter
    private ViewsTransitionAnimator<Integer> animator;   //GestureImageView  动画效果

    public Uri mUriTakPhoto = null;                 //拍摄照片的uri
    private String selectAlbumName = "所有照片";     //已选相册的相册名

    public String getSelectAlbum() {
        return selectAlbumName;
    }

    public void setSelectAlbum(String selectAlbum) {
        this.selectAlbumName = selectAlbum;
    }

    public RecyclerView getWallGrid() {
        return rlvWall;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //Initializing title bar and statue bar
        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "图片")
                .setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setRightAsButton(R.drawable.selector_base_circular)
                .setRightText("完成", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
        //Initializing condition(传递进入的参数)
        isCamera = getIntent().getBooleanExtra(ISCAMERA, true);
        num = getIntent().getIntExtra(SELECTNUM, 1);

        permissionCheck();
    }

    /**
     * Application write and read permission (读写权限,适配6.0以上系统）
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
                            //TODO 设置按钮不可以点击
                            getTitleBar().setRightClickEnable(false);
                            //Initializing db(查询数据库，初始化照片数据)
                            getP().initDB();
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

    public void initView(ArrayList<PhotoBean> allPhotos) {
        //Initializing view
        catalog = (ListView) findViewById(R.id.lv_album_catalog);
        rlvWall = (RecyclerView) findViewById(R.id.rlv_wall);
        viewPager = (ViewPager) findViewById(R.id.transition_pager);
        btnAllClassify = (Button) findViewById(R.id.btn_album_file);
        btnPreview = (Button) findViewById(R.id.btn_preview);
        background = findViewById(R.id.transition_full_background);

        //Initializing click
        btnAllClassify.setOnClickListener(AlbumWallAct.this);

        //Initializing album wall
        wallAdapter = new PhotoWallAdapter(mContext, allPhotos, isCamera, num, this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rlvWall.setLayoutManager(manager);
        rlvWall.addItemDecoration(new GridDivider((int) Kits.Dimens.dpToPx(mContext, 3)
                , ContextCompat.getColor(mContext, R.color.black)));
        rlvWall.setAdapter(wallAdapter);

        //Initializing viewPager
        pagerAdapter = new PaintingsPagerAdapter(viewPager, mContext, allPhotos);
        viewPager.setAdapter(pagerAdapter);
//        viewPager.addOnPageChangeListener(pagerAdapter);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.size_12));

        // Initializing images recyclerView animator
        final SimpleTracker recyclerTracker = new SimpleTracker() {

            int diffValue = 0; //第一条可见数据和最后一条可见数据差值

            @Override
            public View getViewAt(int position) {
                int first = ((GridLayoutManager) rlvWall.getLayoutManager()).findFirstVisibleItemPosition();
                int last = ((GridLayoutManager) rlvWall.getLayoutManager()).findLastVisibleItemPosition();

                if (first == last) {
                    //翻页时，手动更新页码差值. 因为此时first为-1
                    first = position;
                    last = position + diffValue;
                    //移动recyclerView到当前位置，然后取点
                } else {
                    diffValue = (last - first);
                }
                if (position < first || position > last) {
                    ((GridLayoutManager) rlvWall.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                    return null;
                } else {
                    //返回RecyclerView中选中的Item
                    View itemView = rlvWall.getChildAt(position - first);
                    return PhotoWallAdapter.getImage(itemView);
                }
            }
        };
        // Initializing images viewPager animator
        final SimpleTracker pagerTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                RecyclePagerAdapter.ViewHolder holder = pagerAdapter.getViewHolder(position);
                View view = holder == null ? null : PaintingsPagerAdapter.getImage(holder);
                if (view == null) {
                    Log.i("111111viewPager", "没有定位到动画view的位置");
                }
                return view;
            }
        };

        animator = GestureTransitions.from(rlvWall, recyclerTracker).into(viewPager, pagerTracker);

        // Setting up background animation during image animation
        background = Views.find(this, R.id.transition_full_background);
        animator.addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            @Override
            public void onPositionUpdate(float position, boolean isLeaving) {
                Log.i("111111animator", "动画执行，隐藏背景中--------"+position);
                background.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
                background.getBackground().setAlpha((int) (255 * position));
            }
        });
    }

    /**
     * 更新照片墙所有数据
     *
     * @param datas
     */
    public void upDateWall(List<PhotoBean> datas) {
        if (allPhotos == null) {
            allPhotos = new ArrayList<>();
        }
        allPhotos.clear();
        allPhotos.addAll(datas);
//        wallAdapter.notifyDataSetChanged();
    }

    /**
     * 更新一条数据
     */
    public void updateWall4One(PhotoBean photoBean) {
        if (getP().getAllPhotos().contains(photoBean)) {
            int index = getP().getAllPhotos().indexOf(photoBean);
            getP().getAllPhotos().get(index).setIsSelect(photoBean.getIsSelect());
            //更新选择列表
            if (photoBean.getIsSelect()) {
                selectPhotos.add(photoBean);
            } else {
                selectPhotos.remove(photoBean);
            }
            if (selectPhotos.size() <= 0) {
                getTitleBar().setRightClickEnable(false);
                getTitleBar().setRightText("完成");
            } else {
                getTitleBar().setRightClickEnable(true);
                //更新title显示
                getTitleBar().setRightText("完成(" + selectPhotos.size() + "/" + num + ")");
            }
        }
    }

    /**
     * 插入一条数据
     */
    public void addWallOne(PhotoBean photoBean) {
        if (allPhotos == null) {
            allPhotos = new ArrayList<>();
        }
        if (isCamera) {
            allPhotos.add(1, photoBean);
        } else {
            allPhotos.add(0, photoBean);
        }
        wallAdapter.notifyDataSetChanged();
    }


    public void initCatalog(List<AlbumBean> albums) {
        //设置相册目录数据
        catalog.setTag(0); //标记默认选中的数据
        catalog.setAdapter(new PhotoWallAlbumAdapter(AlbumWallAct.this, albums, R.layout.album_photo_wall_album_item));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_album_file) {
            //点击弹出相册选择列表
            PopupWindowUtil.showMyWindow(catalog);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Config.REQUESTCAMERA) {
                //处理拍照请求
                try {
                    // 刷新在系统相册中显示(以下代码会自动保存到系统的相册目录)
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(mUriTakPhoto);
                    sendBroadcast(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //刷新界面显示
                PhotoBean photoBean = new PhotoBean();
                String path = mUriTakPhoto.getPath();
                String parentName = new File(path).getParentFile().getName();
                photoBean.setImgPath(path);
                photoBean.setImgParentName(parentName);
                getP().getPhotoInfo(path, photoBean);
                addWallOne(photoBean);                  //更新显示数据
                getP().addOnePhoto(photoBean);          //更新原始数据
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_wall;
    }

    public ArrayList<PhotoBean> getSelectPhotos() {
        if (selectPhotos == null) {
            selectPhotos = new ArrayList<>();
        }
        return selectPhotos;
    }

    /**
     * @param position The position of click imageView
     */
    @Override
    public void onPaintingClick(int position) {
        animator.enter(position, false);
    }


    @Override
    public void onBackPressed() {
        Log.i("animator", "is------" + animator.isLeaving());
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.onBackPressed();
        }
    }
}

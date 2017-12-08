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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.FromTracker;
import com.alexvasilkov.gestures.transition.tracker.SimpleTracker;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.album.adapter.PaintingsPagerAdapter;
import com.yuan.album.adapter.PhotoWallAdapter;
import com.yuan.album.adapter.PhotoWallAlbumAdapter;
import com.yuan.album.bean.AlbumBean;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.helper.ActivityManagerHelpder;
import com.yuan.album.helper.PhotoPreviewHelper;
import com.yuan.album.presenter.PAlbumWall;
import com.yuan.album.util.GridDivider;
import com.yuan.album.util.PopupWindowUtil;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.common.other.GoToSystemSetting;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
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
 * Camera  是否使用相机
 * num  需要选择的图片数量
 * crop 是否使用剪裁，如果使用剪裁，num自动更改为1
 * CROPW  剪裁宽度，默认200
 * CROPH  剪裁高度，默认200
 */
@Route(path = "/album/ui/AlbumWallActivity")
public class AlbumWallActivity extends MVPActivity<PAlbumWall> implements ISwipeBack,
        View.OnClickListener, PhotoWallAdapter.OnPaintingListener, PhotoWallAlbumAdapter.OnCatalogListener {
    private RecyclerView rlvWall;               //recyclerView
    private Button btnAllClassify               //相册分类按钮
            , btnPreview;                       //预览按钮
    public LinearLayout llAction, llCatalog;
    public CheckBox checkBox;
    private ListView catalog;                   //目录列表
    private ViewPager viewPager;                //相册预览viewPage
    private View background;                    //动画背景view

    private ArrayList<PhotoBean> selectPhotos;           //选中照片集
    private ArrayList<PhotoBean> mWallData;              //照片墙数据集
    public PhotoWallAdapter wallAdapter;                 //Album adapter
    private PaintingsPagerAdapter pagerAdapter;          //ViewPager adapter
    private ViewsTransitionAnimator<Integer> animator;   //GestureImageView  动画效果
    public Uri mUriTakPhoto = null;                      //拍摄照片的uri

    public boolean isCamera;                //是否显示相机
    private boolean isCrop;                 //是否使用剪裁
    private int num;                        //需要选择照片的数量
    private int cropW;                      //剪裁宽度
    private int cropH;                      //剪裁宽度

    public static void open(Activity from) {
        Intent intent = new Intent(from, AlbumWallActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //Initializing condition(传递进入的参数)
        isCamera = getIntent().getBooleanExtra(Config.CAMERA, true);
        num = getIntent().getIntExtra(Config.NUM, 1);
        isCrop = getIntent().getBooleanExtra(Config.CROP, false);
        if (isCrop) num = 1;
        cropW = getIntent().getIntExtra(Config.CROPW, 200);
        cropH = getIntent().getIntExtra(Config.CROPH, 200);

        //Initializing title bar and statue bar
        getTitleBar().setToolbar("图片")
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setLeftIcon(R.drawable.ic_base_back_white, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlbumWallActivity.super.onBackPressed();
                    }
                });
        if (!isCrop) {
            getTitleBar()
                    .setRightAsButton(R.drawable.selector_base_circular)
                    .setRightText("完成", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = getIntent();
                            intent.putStringArrayListExtra(Config.KEYRESULT,
                                    PhotoPreviewHelper.getInstance().setPhotoBean(selectPhotos));
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
        }
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
        selectPhotos = new ArrayList<>();
        mWallData = new ArrayList<>();
        mWallData.addAll(allPhotos);

        //Initializing view
        catalog = (ListView) findViewById(R.id.lv_album_catalog);
        rlvWall = (RecyclerView) findViewById(R.id.rlv_wall);
        viewPager = (ViewPager) findViewById(R.id.transition_pager);
        btnAllClassify = (Button) findViewById(R.id.btn_album_file);
        btnPreview = (Button) findViewById(R.id.btn_album_preview);
        background = findViewById(R.id.transition_full_background);
        llAction = (LinearLayout) findViewById(R.id.ll_action);
        llCatalog = (LinearLayout) findViewById(R.id.ll_album_catalog);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        if (isCrop) btnPreview.setVisibility(View.INVISIBLE);

        //Initializing click
        btnAllClassify.setOnClickListener(AlbumWallActivity.this);
        btnPreview.setOnClickListener(AlbumWallActivity.this);

        //Initializing album wall
        rlvWall.setPadding(rlvWall.getPaddingLeft(), rlvWall.getPaddingTop() + getTitleBar().getTitleBarHeight() +
                        getTitleBar().getStatusBarHeight(),
                rlvWall.getPaddingRight(), rlvWall.getPaddingBottom());
        wallAdapter = new PhotoWallAdapter(mContext, mWallData, isCamera, isCrop, num, this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rlvWall.setLayoutManager(manager);
        rlvWall.addItemDecoration(new GridDivider((int) Kits.Dimens.dpToPx(mContext, 3)
                , ContextCompat.getColor(mContext, R.color.black)));
        rlvWall.setAdapter(wallAdapter);

        //Initializing viewPager
        pagerAdapter = new PaintingsPagerAdapter(viewPager, mContext, isCamera, mWallData);
        viewPager.setAdapter(pagerAdapter);
        //Update viewPager in animation
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.size_12));

        // Initializing images recyclerView animator
        final FromTracker<Integer> recyclerTracker = new FromTracker<Integer>() {
            @Override
            public View getViewById(@android.support.annotation.NonNull Integer imagePos) {
                final RecyclerView.ViewHolder holder =
                        rlvWall.findViewHolderForLayoutPosition(imagePos);
                return holder == null ? null : wallAdapter.getImage(holder);
            }

            @Override
            public int getPositionById(@android.support.annotation.NonNull Integer imagePos) {
                final boolean hasHolder =
                        rlvWall.findViewHolderForLayoutPosition(imagePos) != null;
                return !hasHolder || getViewById(imagePos) != null
                        ? imagePos : FromTracker.NO_POSITION;
            }
        };

        // Initializing images viewPager animator
        final SimpleTracker pagerTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                RecyclePagerAdapter.ViewHolder holder = pagerAdapter.getViewHolder(position);
                View view = holder == null ? null : PaintingsPagerAdapter.getImage(holder);
                if (view == null) {
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
                background.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
                background.getBackground().setAlpha((int) (255 * position));

                if (isLeaving && position == 0f) {
                    pagerAdapter.setActivated(false);
                }

                if (background.getVisibility() == View.INVISIBLE) {
                    //切换底部显示
                    if (getTitleBar().getVisibility() == View.GONE) {
                        //相当于手势返回按钮
                        llCatalog.setVisibility(View.VISIBLE);
                        llAction.setVisibility(View.INVISIBLE);
                        getTitleBar().restoreAnimationTitle();
                    } else if (llAction.getVisibility() == View.VISIBLE) {
                        llCatalog.setVisibility(View.VISIBLE);
                        llAction.setVisibility(View.INVISIBLE);
                    }
                } else {
                    llCatalog.setVisibility(View.INVISIBLE);
                    llAction.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initCatalog(List<AlbumBean> albums) {
        //设置相册目录数据
        catalog.setTag(0); //标记默认选中的数据
        catalog.setAdapter(new PhotoWallAlbumAdapter(AlbumWallActivity.this, albums, R.layout.album_photo_wall_album_item, this));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) catalog.getLayoutParams();
        params.topMargin = params.topMargin + getTitleBar().getTitleBarHeight() +
                getTitleBar().getStatusBarHeight();
        catalog.setLayoutParams(params);
    }

    /**
     * 更新一条数据
     */
    public void updateWall4One(PhotoBean photoBean) {
        if (getP().getAllPhotos().contains(photoBean)) {
            int index = getP().getAllPhotos().indexOf(photoBean);
            getP().getAllPhotos().get(index).setSelect(photoBean.isSelect());
            //更新选择列表
            if (photoBean.isSelect()) {
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
        if (mWallData == null) {
            mWallData = new ArrayList<>();
        }
        if (isCamera) {
            mWallData.add(1, photoBean);
        } else {
            mWallData.add(0, photoBean);
        }
        wallAdapter.notifyDataSetChanged();
        pagerAdapter.notifyDataSetChanged();
    }

    /**
     * 获取当前已选数据集合
     *
     * @return
     */
    public ArrayList<PhotoBean> getSelectPhotos() {
        if (selectPhotos == null) {
            selectPhotos = new ArrayList<>();
        }
        return selectPhotos;
    }

    /**
     * 更新照片墙所有数据
     *
     * @param datas
     */
    public void updateWall(List<PhotoBean> datas) {
        if (mWallData == null) {
            mWallData = new ArrayList<>();
        }
        mWallData.clear();
        mWallData.addAll(datas);
        //Refresh recyclerView and viewPager
        wallAdapter.notifyDataSetChanged();
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_album_file) {
            //点击弹出相册选择列表
            PopupWindowUtil.showMyWindow(catalog);
        } else if (view.getId() == R.id.btn_album_preview) {
            //预览相册(防止Intent大小限制)
            PhotoPreviewHelper.getInstance().setPhotoBean(selectPhotos);
            PhotoPreviewActivity.open(this, 0);
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
                addWallOne(photoBean);                  //更新显示数据
                getP().addOnePhoto(photoBean);          //更新原始数据
            } else if (Config.IMAGECROPREQUEST == requestCode) {
                //返回剪裁数据集合
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_wall;
    }

    @Override
    protected ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }

    /**
     * @param position The position of click imageView
     */
    @Override
    public void onPaintingClick(int position) {
        if (isCrop) { //跳转图片剪裁界面
            Intent intent = new Intent(mContext, ImageCropActivity.class);
            intent.putExtra(Config.CROPPATH, mWallData.get(position).getImgPath());
            intent.putExtra(Config.CROPW, cropW);
            intent.putExtra(Config.CROPH, cropH);
            startActivityForResult(intent, Config.IMAGECROPREQUEST);
        } else {
            pagerAdapter.setActivated(true);
            animator.enter(position, true);
            llAction.setVisibility(View.VISIBLE);
            llCatalog.setVisibility(View.INVISIBLE);
            //设置按钮是否选中
            checkBox.setChecked(mWallData.get(position).isSelect());
            if (pagerAdapter != null) {
                pagerAdapter.setCurrentPosition(position);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!animator.isLeaving()) {
            animator.exit(true);
            //还原titleBar
            getTitleBar().restoreAnimationTitle();
        } else if (catalog.getVisibility() == View.VISIBLE) {
            //隐藏目录列表
            PopupWindowUtil.showMyWindow(catalog);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCatalogItemClick(String title) {
        btnAllClassify.setText(title);
    }
}

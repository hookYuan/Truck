package com.yuan.album.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsCoordinator;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
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
 * Camera
 * num
 */
@Route(path = "/album/selectImage/AlbumWallAct")
public class AlbumWallAct extends MVPActivity<PAlbumWall> implements ISwipeBack,
        View.OnClickListener, PhotoWallAdapter.OnPaintingListener {

    public final static String ISCAMERA = "camera";
    public final static String SELECTNUM = "num";

    private RecyclerView rlvWall;               //recyclerView
    private Button btnAllClassify               //相册分类按钮
            , btnPreview;                       //预览按钮
    public LinearLayout llAction, llCatalog;
    public CheckBox checkBox;
    private ListView catalog;                   //目录列表
    private ViewPager viewPager;                //相册预览viewPage

    private View background;                    //动画背景view

    public Boolean isCamera;               //是否显示相机
    private int num;                        //需要选择照片的数量

    private ArrayList<PhotoBean> selectPhotos;      //选中照片集
    private ArrayList<PhotoBean> mWallData;         //照片墙数据集

    public PhotoWallAdapter wallAdapter;           //Album adapter
    private PaintingsPagerAdapter pagerAdapter;          //ViewPager adapter
    private ViewsTransitionAnimator<Integer> animator;   //GestureImageView  动画效果

    public Uri mUriTakPhoto = null;                 //拍摄照片的uri

    @Override
    protected void initData(Bundle savedInstanceState) {
        //Initializing title bar and statue bar
        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "图片")
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
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
        isCamera = true;
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
        selectPhotos = new ArrayList<>();
        mWallData = new ArrayList<>();
        mWallData.addAll(allPhotos);

        //Initializing view
        catalog = (ListView) findViewById(R.id.lv_album_catalog);
        rlvWall = (RecyclerView) findViewById(R.id.rlv_wall);
        viewPager = (ViewPager) findViewById(R.id.transition_pager);
        btnAllClassify = (Button) findViewById(R.id.btn_album_file);
        btnPreview = (Button) findViewById(R.id.btn_preview);
        background = findViewById(R.id.transition_full_background);
        llAction = (LinearLayout) findViewById(R.id.ll_action);
        llCatalog = (LinearLayout) findViewById(R.id.ll_album_catalog);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        //Initializing click
        btnAllClassify.setOnClickListener(AlbumWallAct.this);

        //Initializing album wall
        rlvWall.setPadding(rlvWall.getPaddingLeft(), rlvWall.getPaddingTop() + getTitleBar().getTitleBarHeight() +
                        getTitleBar().getStatusBarHeight(),
                rlvWall.getPaddingRight(), rlvWall.getPaddingBottom());
        wallAdapter = new PhotoWallAdapter(mContext, mWallData, isCamera, num, this);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rlvWall.setLayoutManager(manager);
        rlvWall.addItemDecoration(new GridDivider((int) Kits.Dimens.dpToPx(mContext, 3)
                , ContextCompat.getColor(mContext, R.color.black)));
        rlvWall.setAdapter(wallAdapter);

        //Initializing viewPager
        pagerAdapter = new PaintingsPagerAdapter(viewPager, mContext, isCamera, mWallData);
        viewPager.setAdapter(pagerAdapter);
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
                background.setVisibility(position == 0f ? View.GONE : View.VISIBLE);
                background.getBackground().setAlpha((int) (255 * position));
            }
        });
    }

    public void initCatalog(List<AlbumBean> albums) {
        //设置相册目录数据
        catalog.setTag(0); //标记默认选中的数据
        catalog.setAdapter(new PhotoWallAlbumAdapter(AlbumWallAct.this, albums, R.layout.album_photo_wall_album_item));
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

    @Override
    protected ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }

    /**
     * @param position The position of click imageView
     */
    @Override
    public void onPaintingClick(int position) {
        animator.enter(position, true);
        //设置按钮是否选中
        checkBox.setChecked(mWallData.get(position).isSelect());
        //切换底部显示
        llCatalog.setVisibility(View.GONE);
        llAction.setVisibility(View.VISIBLE);
        if (pagerAdapter != null) {
            pagerAdapter.setCurrentPosition(position);
        }
    }


    @Override
    public void onBackPressed() {
        if (!animator.isLeaving()) {
            animator.exit(true);
            llCatalog.setVisibility(View.VISIBLE);
            llAction.setVisibility(View.GONE);
            //还原titleBar
            getTitleBar().restoreAnimationTitle();
        } else if (catalog.getVisibility() == View.VISIBLE) {
            //隐藏目录列表
            PopupWindowUtil.showMyWindow(catalog);
        } else {
            super.onBackPressed();
        }
    }

    public void cancelAnimation() {
        if (!animator.isLeaving()) {
            animator.exit(true);
            llCatalog.setVisibility(View.VISIBLE);
            llAction.setVisibility(View.GONE);
            //还原titleBar
            getTitleBar().restoreAnimationTitle();
        }
    }
}

package com.yuan.album.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import com.alexvasilkov.events.Events;
import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.animation.ViewPosition;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.AlbumWallAct;
import com.yuan.album.ui.PhotoViewPageActivity;
import com.yuan.basemodule.net.Glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/11/15.
 */

public class PhotoPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<PhotoBean> mAllPhotos;

    private PhotoViewPageActivity mContext;

    private ArrayList<GestureImageView> pageviews;

    private boolean isCamera = false; //是否有相机

    private boolean isClick = false; //是否点击图片

    private int position = 0; //当前图片位置小标

    private int clickPosition = 0;  //最初点击的图片下标

    public PhotoPagerAdapter(Activity activity, List<PhotoBean> allPhotos) {
        this.mAllPhotos = allPhotos;
        if (TextUtils.isEmpty(mAllPhotos.get(0).getImgPath())) {
            isCamera = true;
        }
        this.mContext = (PhotoViewPageActivity) activity;
        pageviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            //动态生成PageViews
            final GestureImageView page00 = (GestureImageView) LayoutInflater.from(mContext).inflate(R.layout.act_album_photo_view_page_item, null);
            //设置动画
            page00.getPositionAnimator().addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
                @Override
                public void onPositionUpdate(float mPosition, boolean isLeaving) {
                    if (mPosition == 0f && isLeaving) { // Exit finished
                        // Asking previous activity to show back original image
                        Events.create("show_image").param(true).post();
                        // By default end of exit animation will return GestureImageView into
                        // fullscreen state, this will make the image blink. So we need to hack this
                        // behaviour and keep image in exit state until activity is finished.
                        page00.getController().getSettings().disableBounds();
                        page00.getPositionAnimator().setState(0f, false, false);
                        // Finishing activity
                        mContext.finish();
                        mContext.overridePendingTransition(0, 0); // No activity animation
                    }
                }
            });
            page00.getController().getSettings()
                    .setMaxZoom(3f)
                    .setDoubleTapZoom(-1f) // Falls back to max zoom level
                    .setPanEnabled(true)
                    .setZoomEnabled(true)
                    .setDoubleTapEnabled(true)
                    .setRotationEnabled(true)
                    .setRestrictRotation(true)
                    .setOverscrollDistance(0f, 0f)
                    .setOverzoomFactor(2f)
                    .setFillViewport(false)
                    .setFitMethod(Settings.Fit.INSIDE)
                    .setGravity(Gravity.CENTER);

            page00.getController().setOnGesturesListener(new GestureController.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapConfirmed(@NonNull MotionEvent event) {
                    //图片的点击事件
                    if (isClick) { //显示
                        mContext.getTitleBar().setAnimationTitleBarIn();
                        TranslateAnimation animation = new TranslateAnimation(0, 0, mContext.getLlAction().getHeight(), 0);
                        animation.setDuration(300);//设置动画持续时间
                        mContext.getLlAction().setAnimation(animation);
                        animation.startNow();
                        mContext.getLlAction().setVisibility(View.VISIBLE);
                        isClick = false;
                    } else { //隐藏
                        mContext.getTitleBar().setAnimationTitleBarOut();
                        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, mContext.getLlAction().getHeight());
                        animation.setDuration(300);//设置动画持续时间
                        mContext.getLlAction().setAnimation(animation);
                        animation.startNow();
                        mContext.getLlAction().setVisibility(View.GONE);
                        isClick = true;
                    }
                    return false;
                }
            });
            mContext.inAnimation(page00);
            pageviews.add(page00);
        }
        //选中按钮点击事件监听
        mContext.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isSelect;  //获取选中状态
                if (isCamera) {
                    isSelect = mAllPhotos.get(position + 1).getIsSelect();
                } else {
                    isSelect = mAllPhotos.get(position).getIsSelect();
                }
                if (isSelect) { //已选中
                    mContext.getCheckBox().setChecked(false);
//                    mAllPhotos.get

                } else {
                    mContext.getCheckBox().setChecked(true);
                }


            }
        });

        Events.register(this);
    }

    public void destroy() {
        Events.unregister(this);
    }

    @Override
    public int getCount() {
        if (isCamera) {
            return mAllPhotos.size() - 1;
        }
        return mAllPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int realPos = position;
        if (isCamera) {
            realPos = position + 1;
        }
        int i = position % 4;
        GlideHelper.with(mContext).load(mAllPhotos.get(realPos).getImgPath())
                .loadding(false)
                .crossFade(0).into(pageviews.get(i));
        ((ViewPager) container).addView(pageviews.get(i));
        Log.i("yuanye", "--------pos--" + position);
        return pageviews.get(i);
    }

    public void setPosition(int position) {
        this.position = position;
        this.clickPosition = position;
    }

    @Events.Subscribe(PhotoViewPageActivity.VIEWPAGE_END)
    private void showImage() {
        int i = (position) % 4;
        i = i <= 0 ? 0 : i;
        //TODO 通知照片墙滑动到最新位置(不是实时更新，只更新一次)
        AlbumWallAct albumWallAct = (AlbumWallAct) mContext.getmFrom();
        ViewPosition viewPosition = albumWallAct.getWallAdapter().updateAnimation(position);
        //计算需要退出的View
        if (viewPosition != null) pageviews.get(i).getPositionAnimator().update(viewPosition);
        if (!pageviews.get(i).getPositionAnimator().isLeaving()) {
            //TODO 未解决：当结束图片为点击图片3的倍数时，出现结束动画不执行
            if (Math.abs(clickPosition - position) % 3 == 0
                    && clickPosition != position && Math.abs(clickPosition - position) / 3 == 1) {
                // Finishing activity
                mContext.finish();
                mContext.overridePendingTransition(0, 0); // No activity animation
            } else {
                pageviews.get(i).getPositionAnimator().exit(true);
            }
        } else {
            mContext.finish();
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        GestureImageView imageView = (GestureImageView) object;
        releaseImageViewResouce(imageView);
        int i = position % 4;
        container.removeView(pageviews.get(i));
        System.gc();
    }


    public void releaseImageViewResouce(GestureImageView imageView) {
        if (imageView == null) {
            return;
        }
        Matrix max = new Matrix();
        imageView.setDrawingCacheEnabled(true);
        Bitmap bm = imageView.getDrawingCache();
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        Glide.clear(imageView);
        imageView.setDrawingCacheEnabled(false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        //判断当前是否选中
        boolean isSelect;
        if (isCamera) {
            isSelect = mAllPhotos.get(position + 1).getIsSelect();
        } else {
            isSelect = mAllPhotos.get(position).getIsSelect();
        }
        mContext.getCheckBox().setChecked(isSelect);
        //更新标题
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

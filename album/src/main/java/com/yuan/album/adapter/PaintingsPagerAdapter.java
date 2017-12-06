package com.yuan.album.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.AlbumWallAct;
import com.yuan.album.util.glide.GlideHelper;

import java.util.List;

public class PaintingsPagerAdapter extends RecyclePagerAdapter<PaintingsPagerAdapter.ViewHolder> implements ViewPager.OnPageChangeListener {

    private final ViewPager viewPager;
    private final List<PhotoBean> mAllPhotos;
    private AlbumWallAct mContext;
    private boolean isCamera;

    private boolean isClick = false; //是否点击图片
    private boolean isSelect = false;//是否选中图片

    private int currentPosition = 0; //当前的位置

    public PaintingsPagerAdapter(ViewPager pager, Context context, boolean isCamera, List<PhotoBean> allPhotos) {
        this.viewPager = pager;
        this.mAllPhotos = allPhotos;
        this.mContext = (AlbumWallAct) context;
        this.isCamera = isCamera;
        viewPager.addOnPageChangeListener(this);
        //选中按钮点击事件监听
        this.mContext.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAllPhotos.get(currentPosition).isSelect()) {
                    mContext.checkBox.setChecked(false);
                    mAllPhotos.get(currentPosition).setSelect(false);
                } else {
                    mContext.checkBox.setChecked(true);
                    mAllPhotos.get(currentPosition).setSelect(true);
                }
                //TODO 更新显示数据
                mContext.updateWall4One(mAllPhotos.get(currentPosition));
                //TODO 更新显示
            }
        });
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public int getCount() {
        return mAllPhotos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        ViewHolder holder = new ViewHolder(container);
        holder.image.getController().enableScrollInViewPager(viewPager);
        //Initializing GestureView
        holder.image.getController().getSettings()
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
        //Initializing GestureView Click
        holder.image.getController().setOnGesturesListener(new GestureController.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent event) {
                //图片的点击事件
                if (isClick) { //显示
                    mContext.getTitleBar().setAnimationTitleBarIn();
                    TranslateAnimation animation = new TranslateAnimation(0, 0, mContext.llAction.getHeight(), 0);
                    animation.setDuration(300);//设置动画持续时间
                    mContext.llAction.setAnimation(animation);
                    animation.startNow();
                    mContext.llAction.setVisibility(View.VISIBLE);
                    isClick = false;
                } else { //隐藏
                    mContext.getTitleBar().setAnimationTitleBarOut();
                    TranslateAnimation animation = new TranslateAnimation(0, 0, 0, mContext.llAction.getHeight());
                    animation.setDuration(300);//设置动画持续时间
                    mContext.llAction.setAnimation(animation);
                    animation.startNow();
                    mContext.llAction.setVisibility(View.GONE);
                    isClick = true;
                }
                return false;
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideHelper.loadFlickrFull(mAllPhotos.get(position), holder.image);
    }

    public static GestureImageView getImage(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 1 && position == 0 && isCamera) {
            //TODO 隐藏相机页
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        mContext.checkBox.setChecked(mAllPhotos.get(position).isSelect());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final GestureImageView image;

        ViewHolder(ViewGroup container) {
            super(new GestureImageView(container.getContext()));
            image = (GestureImageView) itemView;
        }
    }

}

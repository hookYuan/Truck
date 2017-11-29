package com.yuan.album.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvasilkov.events.Event;
import com.alexvasilkov.events.Events;
import com.alexvasilkov.gestures.animation.ViewPosition;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
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

    public PhotoPagerAdapter(Activity activity, List<PhotoBean> allPhotos) {
        this.mAllPhotos = allPhotos;
        if (TextUtils.isEmpty(allPhotos.get(0).getImgPath())) {
            mAllPhotos.remove(0);
        }
        this.mContext = (PhotoViewPageActivity) activity;
        pageviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            //动态生成PageViews
            GestureImageView page00 = (GestureImageView) LayoutInflater.from(mContext).inflate(R.layout.act_album_photo_view_page_item, null);
            pageviews.add(page00);
            //设置动画
            final int k = i;
            pageviews.get(i).getPositionAnimator().addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
                @Override
                public void onPositionUpdate(float mPosition, boolean isLeaving) {
                    if (mPosition == 0f && isLeaving) { // Exit finished
                        // Asking previous activity to show back original image
                        Events.create("show_image").param(true).post();
                        // By default end of exit animation will return GestureImageView into
                        // fullscreen state, this will make the image blink. So we need to hack this
                        // behaviour and keep image in exit state until activity is finished.
                        pageviews.get(k).getController().getSettings().disableBounds();
                        pageviews.get(k).getPositionAnimator().setState(0f, false, false);
                        // Finishing activity
                        mContext.finish();
                        mContext.overridePendingTransition(0, 0); // No activity animation
                    }
                }
            });
            mContext.inAnimation(pageviews.get(i));
        }
        Events.register(this);
    }

    public void destroy() {
        Events.unregister(this);
    }

    @Override
    public int getCount() {
        return mAllPhotos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int i = position % 4;
        GlideHelper.with(mContext).load(mAllPhotos.get(position).getImgPath())
                .loadding(false)
                .crossFade(0).into(pageviews.get(i));
        ((ViewPager) container).addView(pageviews.get(i));
        Log.i("yuanye", "--------pos--" + position);
        return pageviews.get(i);
    }

    private int position = 0;

    public void setPosition(int position) {
        this.position = position;
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
            pageviews.get(i).getPositionAnimator().exit(true);
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
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

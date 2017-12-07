package com.yuan.album.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.yuan.album.R;
import com.yuan.album.ui.PhotoPreviewActivity;
import com.yuan.basemodule.net.Glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/11/15.
 */
public class PhotoPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private List<String> mAllPhotos;

    private PhotoPreviewActivity mContext;

    private ArrayList<GestureImageView> pageviews;


    public PhotoPagerAdapter(Activity activity, List<String> allPhotos) {
        this.mAllPhotos = allPhotos;
        this.mContext = (PhotoPreviewActivity) activity;

        pageviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            //动态生成PageViews
            final GestureImageView page00 = (GestureImageView) LayoutInflater.from(mContext).inflate(R.layout.act_album_preview_item, null);
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
                    .setFillViewport(true)
                    .setFitMethod(Settings.Fit.INSIDE)
                    .setGravity(Gravity.CENTER);
            pageviews.add(page00);
        }
    }

    @Override
    public int getCount() {
        return mAllPhotos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int i = position % 4;
        GlideHelper.with(mContext).load(mAllPhotos.get(position))
                .loadding(false)
                .crossFade(0).into(pageviews.get(i));
        ((ViewPager) container).addView(pageviews.get(i));
        return pageviews.get(i);
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

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //释放资源
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
        mContext.getTitleBar().setToolbar((position + 1) + "/" + (mAllPhotos.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

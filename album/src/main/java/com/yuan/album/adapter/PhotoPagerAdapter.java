package com.yuan.album.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvasilkov.events.Events;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.PhotoViewPageActivity;
import com.yuan.basemodule.net.Glide.GlideHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/11/15.
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private List<PhotoBean> mAllPhotos;

    private PhotoViewPageActivity mContext;

    private ArrayList<GestureImageView> pageviews;

    private boolean isFirst = true;

//    GestureImageView gestureImageView;

    public PhotoPagerAdapter(Activity activity, List<PhotoBean> allPhotos) {
        this.mAllPhotos = allPhotos;
        if (TextUtils.isEmpty(allPhotos.get(0).getImgPath())) {
            mAllPhotos.remove(0);
        }
        this.mContext = (PhotoViewPageActivity) activity;
        pageviews = new ArrayList<>();
        for (int i = 0; i < mAllPhotos.size(); i++) {
            //动态生成PageViews
            GestureImageView page00 = (GestureImageView) LayoutInflater.from(mContext).inflate(R.layout.act_album_photo_view_page_item, null);
            pageviews.add(page00);
        }


        Events.register(this);
    }

    public void destroy() {
        Events.unregister(this);
    }

    @Override
    public int getCount() {
        return pageviews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.i("boolean", "-------------" + (view == object));
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
//        Log.i("yuanye", "container_size===============" + container.getChildCount());
//        final GestureImageView gestureImageView = (GestureImageView) LayoutInflater.from(container.getContext())
//                .inflate(R.layout.act_album_photo_view_page_item, null);
        //设置动画
        GlideHelper.with(mContext).load(mAllPhotos.get(position).getImgPath())
                .loadding(false)
                .crossFade(0).into(pageviews.get(position));

        pageviews.get(position).getPositionAnimator().addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            @Override
            public void onPositionUpdate(float mPosition, boolean isLeaving) {
                if (mPosition == 0f && isLeaving) { // Exit finished
                    // Asking previous activity to show back original image
                    Events.create("show_image").param(true).post();
                    // By default end of exit animation will return GestureImageView into
                    // fullscreen state, this will make the image blink. So we need to hack this
                    // behaviour and keep image in exit state until activity is finished.
                    pageviews.get(position).getController().getSettings().disableBounds();
                    pageviews.get(position).getPositionAnimator().setState(0f, false, false);
                    // Finishing activity
                    mContext.finish();
                    mContext.overridePendingTransition(0, 0); // No activity animation
                }
            }
        });
        if (isFirst) {
            mContext.inAnimation(pageviews.get(position));
            isFirst = false;
        }
        container.addView(pageviews.get(position), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        return pageviews.get(position);
    }


    @Events.Subscribe(PhotoViewPageActivity.VIEWPAGE_END)
    private void showImage() {
//        if (!gestureImageView.getPositionAnimator().isLeaving()) {
//            gestureImageView.getPositionAnimator().exit(true);
//        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        GlideBitmapDrawable drawable = (GlideBitmapDrawable) (((ImageView) object).getDrawable());
//        Bitmap bmp = drawable.getBitmap();
//        if (null != bmp && !bmp.isRecycled()) {
//            bmp.recycle();
//            bmp = null;
//        }
//        if (bmp != null)
//            Log.i("yuanye", "销毁bmp---" + bmp.getByteCount());
//        ((ImageView) object).setImageBitmap(null);
        container.removeView((GestureImageView) object);
//        ((ImageView) object).destroyDrawingCache();
//        System.gc();
    }

}

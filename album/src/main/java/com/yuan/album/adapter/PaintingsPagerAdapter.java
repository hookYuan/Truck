package com.yuan.album.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.yuan.album.bean.PhotoBean;
import com.yuan.basemodule.net.Glide.GlideHelper;

import java.util.List;

public class PaintingsPagerAdapter extends RecyclePagerAdapter<PaintingsPagerAdapter.ViewHolder> {

    private final ViewPager viewPager;
    private final List<PhotoBean> mAllPhotos;
    private Context mContext;

    public PaintingsPagerAdapter(ViewPager pager, Context mContext, List<PhotoBean> mAllPhotos) {
        this.viewPager = pager;
        this.mAllPhotos = mAllPhotos;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mAllPhotos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup container) {
        ViewHolder holder = new ViewHolder(container);
        holder.image.getController().enableScrollInViewPager(viewPager);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideHelper.with((Activity) mContext).load(mAllPhotos.get(position).getImgPath())
                .loadding(false)
                .crossFade(0).into(holder.image);
    }

    public static GestureImageView getImage(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }


    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final GestureImageView image;

        ViewHolder(ViewGroup container) {
            super(new GestureImageView(container.getContext()));
            image = (GestureImageView) itemView;
        }
    }

}

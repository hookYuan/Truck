package com.yuanye.user.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuanye.user.R;

import java.util.List;

/**
 * Created by YuanYe on 2017/9/26.
 */

public class GuideAdapter extends PagerAdapter {
    private List<String> mData;

    public GuideAdapter(List<String> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View image = LayoutInflater.from(container.getContext()).inflate(R.layout.u_page_img_item, null);

        GlideHelper.with((Activity) container.getContext()).load(mData.get(position)).into((ImageView) image);

        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
    }
}

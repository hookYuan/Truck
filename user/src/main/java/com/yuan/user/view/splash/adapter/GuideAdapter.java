package com.yuan.user.view.splash.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.user.R;
import com.yuan.user.view.login.ui.LoginActivity;

import java.util.List;

/**
 * Created by YuanYe on 2017/9/26.
 */
public class GuideAdapter extends PagerAdapter {
    private List<Integer> mData;

    public GuideAdapter(List<Integer> mData) {
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
    public Object instantiateItem(final ViewGroup container, int position) {
        View image = LayoutInflater.from(container.getContext()).inflate(R.layout.u_page_img_item, null);
        GlideHelper.load(mData.get(position), (ImageView) image);

        if (position == mData.size() - 1) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    container.getContext().startActivity(new Intent(container.getContext(), LoginActivity.class));
                }
            });
        }
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
    }
}

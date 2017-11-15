package com.yuan.album.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;

import com.tmall.ultraviewpager.UltraViewPager;
import com.yuan.album.R;
import com.yuan.album.adapter.PhotoPagerAdapter;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

/**
 * photoViewPage,照片展示界面
 */
public class PhotoViewPageActivity extends MVPActivity {

    @Override
    protected void initData(Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        UltraViewPager ultraViewPager = (UltraViewPager) findViewById(R.id.ultra_viewpager);
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager
        PagerAdapter adapter = new PhotoPagerAdapter();
        ultraViewPager.setAdapter(adapter);
        //设定页面循环播放
        ultraViewPager.setInfiniteLoop(false);

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_photo_view_page;
    }
}

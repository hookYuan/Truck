package com.yuan.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alexvasilkov.events.Events;
import com.alexvasilkov.gestures.animation.ViewPosition;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.tmall.ultraviewpager.UltraViewPager;
import com.yuan.album.R;
import com.yuan.album.adapter.PhotoPagerAdapter;
import com.yuan.album.bean.PhotoBean;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * photoViewPage,照片展示界面
 */
public class PhotoViewPageActivity extends MVPActivity {

    private static final String EXTRA_POSITION = "position";
    private static final String EXTRA_IMAGE_ALL = "image_all";
    private static final String EXTRA_IMAGE_SELECT = "image_select";
    public static final String VIEWPAGE_END = "view_end";

    private Bundle savedInstanceState;

    private PhotoPagerAdapter adapter;

    public static void open(Activity from, ViewPosition position, ArrayList<PhotoBean> allData, ArrayList<PhotoBean> selectData) {
        Intent intent = new Intent(from, PhotoViewPageActivity.class);
        intent.putExtra(EXTRA_POSITION, position.pack());
        intent.putParcelableArrayListExtra(EXTRA_IMAGE_ALL, allData);
        intent.putParcelableArrayListExtra(EXTRA_IMAGE_SELECT, selectData);
        from.startActivity(intent);
        from.overridePendingTransition(0, 0); // No activity animation
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "图片")
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setRightAsButton(R.drawable.selector_base_circular)
                .setRightText("完成", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击完成的点击事件
                        finish();
                    }
                })
                .setLeftIcon(R.drawable.ic_base_back_white, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Events.create(VIEWPAGE_END).post();
                    }
                });
        initViewPager();
    }

    public void inAnimation(GestureImageView gestureImageView) {
        ViewPosition position = ViewPosition.unpack(getIntent().getStringExtra(EXTRA_POSITION));
        boolean animate = savedInstanceState == null; // No animation when restoring activity
        gestureImageView.getPositionAnimator().enter(position, animate);
    }

    private void initViewPager() {
        ViewPager ultraViewPager = (ViewPager) findViewById(R.id.ultra_viewpager);
//        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        ultraViewPager.setOffscreenPageLimit(2);
        List<PhotoBean> data = getIntent().getParcelableArrayListExtra(EXTRA_IMAGE_ALL);
        adapter = new PhotoPagerAdapter(mContext, data);
        ultraViewPager.setAdapter(adapter);
        //设定页面循环播放
//        ultraViewPager.setInfiniteLoop(false);
    }

    @Override
    public void onBackPressed() {
        Events.create(VIEWPAGE_END).post();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁Event
        if (adapter != null) adapter.destroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_photo_view_page;
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }
}

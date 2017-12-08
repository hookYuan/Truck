package com.yuan.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.album.R;
import com.yuan.album.adapter.PhotoPagerAdapter;
import com.yuan.album.helper.ActivityManagerHelpder;
import com.yuan.album.helper.PhotoPreviewHelper;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

import java.util.List;

/**
 * photoViewPage,照片展示界面
 * 图片预览（不包含动画效果）
 */
@Route(path = "/album/ui/PhotoPreviewActivity")
public class PhotoPreviewActivity extends MVPActivity implements ISwipeBack {

    private static final String EXTRA_SELECT_POS = "select_pos";

    private PhotoPagerAdapter adapter;

    public static void open(Activity from, int pos) {
        Intent intent = new Intent(from, PhotoPreviewActivity.class);
        intent.putExtra(EXTRA_SELECT_POS, pos);
        from.startActivity(intent);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //Initializing Album Data
        List<String> data = PhotoPreviewHelper.getInstance().getData();
        int position = getIntent().getIntExtra(EXTRA_SELECT_POS, 0);

        //Initializing toolBar
        getTitleBar().setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar((position + 1) + "/" + (data.size()));

        //Initializing ViewPager
        ViewPager ultraViewPager = (ViewPager) findViewById(R.id.ultra_viewpager);
        adapter = new PhotoPagerAdapter(mContext, data);
        ultraViewPager.setAdapter(adapter);
        ultraViewPager.addOnPageChangeListener(adapter);
        ultraViewPager.setCurrentItem(position <= 0 ? 0 : position);
        ultraViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.size_12));
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_preview;
    }


    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

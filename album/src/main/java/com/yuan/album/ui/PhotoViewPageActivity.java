package com.yuan.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.alexvasilkov.gestures.animation.ViewPosition;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.SimpleTracker;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.yuan.album.R;
import com.yuan.album.adapter.PhotoPagerAdapter;
import com.yuan.album.adapter.PhotoWallAdapter;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.helper.PhotoWallHelper;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

import java.util.List;

/**
 * photoViewPage,照片展示界面
 */
public class PhotoViewPageActivity extends MVPActivity implements PhotoWallAdapter.OnPaintingListener {

    private static final String EXTRA_POSITION = "position";
    private static final String EXTRA_SELECT_POS = "select_pos";
    public static final String VIEWPAGE_END = "view_end";

    private Bundle savedInstanceState;

    private PhotoPagerAdapter adapter;

    private static Activity mFrom;

    private LinearLayout llAction;
    private static RecyclerView recycler;

    private ViewsTransitionAnimator<Integer> animator;
    private CheckBox checkBox;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public LinearLayout getLlAction() {
        return llAction;
    }

    public static Activity getmFrom() {
        return mFrom;
    }

    public static void open(Activity from, RecyclerView recyclerView, ViewPosition position, int pos) {
        recycler = recyclerView;
        Intent intent = new Intent(from, PhotoViewPageActivity.class);
        intent.putExtra(EXTRA_POSITION, position.pack());
        intent.putExtra(EXTRA_SELECT_POS, pos);
        from.startActivity(intent);
        from.overridePendingTransition(0, 0); // No activity animation
        mFrom = from;
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
                        finish();
                    }
                })
                .setLeftIcon(R.drawable.ic_base_back_white, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Events.create(VIEWPAGE_END).post();
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
        //Initializing Album Data
        List<PhotoBean> data = PhotoWallHelper.getInstance().getData();
        int position = getIntent().getIntExtra(EXTRA_SELECT_POS, 0);
        boolean isSelect = data.get(position).getIsSelect();
        if (TextUtils.isEmpty(data.get(0).getImgPath())) { //是否有相机
            position = position - 1;
        }

        //Initializing CheckBox
        llAction = (LinearLayout) findViewById(R.id.ll_action);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setChecked(isSelect);

        //Initializing ViewPager
        ViewPager ultraViewPager = (ViewPager) findViewById(R.id.ultra_viewpager);
        adapter = new PhotoPagerAdapter(ultraViewPager, mContext, data);
        ultraViewPager.setAdapter(adapter);
        adapter.setPosition(position <= 0 ? 0 : position);
        ultraViewPager.setCurrentItem(position <= 0 ? 0 : position);
        ultraViewPager.addOnPageChangeListener(adapter);
        ultraViewPager.setOffscreenPageLimit(1);
        ultraViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.size_12));

        // Initializing images animator
        final SimpleTracker recyclerTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                int first = ((GridLayoutManager) recycler.getLayoutManager()).findFirstVisibleItemPosition();
                int last = ((GridLayoutManager) recycler.getLayoutManager()).findLastVisibleItemPosition();
                if (position < first || position > last) {
                    return null;
                } else {
                    View itemView = recycler.getChildAt(position - first);
                    if (recycler.getAdapter() instanceof PhotoWallAdapter) {
                        //Where from Activity is AlbumWallAct to get item view
                        return PhotoWallAdapter.getImage(itemView);
                    } else {
                        return null;
                    }
                }
            }
        };

        final SimpleTracker pagerTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                RecyclePagerAdapter.ViewHolder holder = adapter.getViewHolder(position);
                return holder == null ? null : PhotoPagerAdapter.getImage(holder);
            }
        };
        animator = GestureTransitions.from(recycler, recyclerTracker).into(ultraViewPager, pagerTracker);
        animator.enter(position, true);
    }

    @Override
    public void finish() {
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.finish();
        }
    }


    @Override
    public void onPaintingClick(int position) {
        animator.enter(position, true);
    }


    @Override
    protected void onDestroy() {
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.onDestroy();
        }
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

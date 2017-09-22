package com.yuan.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.bean.PhotoConfigure;
import com.yuan.album.custom_view.PinchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yuanYe创建于2016/12/23
 * QQ：962851730
 * 照片预览的 Activity
 */

@Route(path = "/album/selectImage/PhotoPreviewActivity")
public class PhotoPreviewActivity extends ExtraActivity {

    private static PhotoPreviewActivity.OnHandlerResultCallback callback; //回调
    private ArrayList<PhotoBean> allPhotoList;  //本地所有的照片集合
    private ArrayList<PhotoBean> selectPhotoList;  //选中的照片集合
    private PhotoConfigure photoConfigure;  //开始设置的图片选择参数
    private int currentPosition; // 当前页的图片(从1开始，最大为allPhotoList.size)
    private ViewPager viewPager;
    private ArrayList<View> pageviews;
    private View page00, page01, page02;
    private PinchImageView image_00, image_01, image_02;
    private CheckBox checkbox;
    private static Activity fromActivity;
    private int firstNumber = 0;
    private boolean isClick = false;//判断是否点击图片（点击隐藏toolbar）
    private LinearLayout ll_action;

    // 跳转到该界面(打开相册预览)
    public static void openImagePreview(Activity activity, int position, PhotoConfigure photoConfigure,
                                        ArrayList<PhotoBean> allPhotoList,
                                        ArrayList<PhotoBean> selectPhotoList,
                                        PhotoPreviewActivity.OnHandlerResultCallback callback) {
        PhotoPreviewActivity.callback = callback;
        fromActivity = activity;
        Intent intent = new Intent(activity, PhotoPreviewActivity.class);
        intent.putParcelableArrayListExtra("allPhotoList", allPhotoList);
        intent.putParcelableArrayListExtra("selectPhotoList", selectPhotoList);
        intent.putExtra("position", position);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("photoConfigure", photoConfigure);
        intent.putExtras(mBundle);
        activity.startActivity(intent);
    }

    //初始化数据
    private void initData() {
        Intent intent = getIntent();
        allPhotoList = intent.getParcelableArrayListExtra("allPhotoList");
        selectPhotoList = intent.getParcelableArrayListExtra("selectPhotoList");
        currentPosition = intent.getIntExtra("position", 1);
        photoConfigure = (PhotoConfigure) intent.getExtras().getSerializable("photoConfigure");
        if (photoConfigure == null) {
            photoConfigure = new PhotoConfigure(intent.getBooleanExtra("camara", true), intent.getIntExtra("num", 0));
        }
        if (photoConfigure.isCamera()) {
            firstNumber = 1;
        }
        //初始化viewPage的左右item
        pageviews = new ArrayList<>();
        page00 = LayoutInflater.from(this).inflate(R.layout.album_photo_preview_item, null);
        page01 = LayoutInflater.from(this).inflate(R.layout.album_photo_preview_item, null);
        page02 = LayoutInflater.from(this).inflate(R.layout.album_photo_preview_item, null);

        //分是否有相机的情况
        if (allPhotoList.size() == 1 + firstNumber) {
            pageviews.add(page00);
        } else if (allPhotoList.size() == 2 + firstNumber) {
            pageviews.add(page00);
            pageviews.add(page01);
        } else {
            pageviews.add(page00);
            pageviews.add(page01);
            pageviews.add(page02);
        }
    }

    //初始化view
    private void initView() {
        //初始化标题
        getTitleBar().setLeftIcon(R.drawable.ic_base_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onHandlerSuccess(selectPhotoList, allPhotoList);
                Intent intent = getIntent();
                intent.putParcelableArrayListExtra("select_list", selectPhotoList);
                intent.putParcelableArrayListExtra("all_list", allPhotoList);
                setResult(RESULT_OK, intent);
                finish();
            }
        }).setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setRightAsButton(R.drawable.selector_base_circular);

        if (photoConfigure.isCamera()) {
            getTitleBar().setToolbar(currentPosition + "/" + (allPhotoList.size() - 1));
        } else {
            getTitleBar().setToolbar(currentPosition + 1 + "/" + (allPhotoList.size()));
        }
        getTitleBar().setRightText("完成", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO(确定按钮点击事件)
                if (fromActivity instanceof PhotoWallActivity) {
                    fromActivity.finish();
                    if (PhotoWallActivity.callback != null)
                        PhotoWallActivity.callback.onHandlerSuccess(selectPhotoList);
                    //设置返回只
                }
                Intent intent = getIntent();
                intent.putParcelableArrayListExtra("select_list", selectPhotoList);
                intent.putParcelableArrayListExtra("all_list", allPhotoList);
                setResult(RESULT_OK, intent);
                if (callback != null)
                    callback.onHandlerSuccess(selectPhotoList, allPhotoList);
                finish();
            }
        });
        //初始化
        updateRightView();
        //初始化控件
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(pageviews));
        image_00 = (PinchImageView) page00.findViewById(R.id.pinchImageView);
        image_01 = (PinchImageView) page01.findViewById(R.id.pinchImageView);
        image_02 = (PinchImageView) page02.findViewById(R.id.pinchImageView);

        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中的点击事件
                if (allPhotoList.get(currentPosition).isSelect()) { //已选
                    Log.i("yuanye", "11111111111111");
                    for (int i = 0; i < selectPhotoList.size(); i++) {
                        if (selectPhotoList.get(i).getPosition() == allPhotoList.get(currentPosition).getPosition()) {
                            selectPhotoList.remove(i);
                        }
                    }
//                    selectPhotoList.remove(allPhotoList.get(currentPosition));
                    checkbox.setChecked(false);
                    allPhotoList.get(currentPosition).setSelect(false);
                } else if (selectPhotoList.size() < photoConfigure.getNum()) {
                    selectPhotoList.add(allPhotoList.get(currentPosition));
                    checkbox.setChecked(true);
                    allPhotoList.get(currentPosition).setSelect(true);
                } else {
                    checkbox.setChecked(false);
                    Toast.makeText(PhotoPreviewActivity.this, "你最多只能选择" + photoConfigure.getNum() + "张照片", Toast.LENGTH_SHORT).show();
                }
                //更新(确定按钮)显示
                updateRightView();
            }
        });
        //设置无线滑动
        //初始化viewPage显示的页数
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("yuanye", "selected---------" + position);
                switch (position) {
                    case 0:
                        if (currentPosition > firstNumber) {
                            currentPosition = currentPosition - 1;
                        } else {
                            currentPosition = 1;
                        }
                        break;
                    case 1:
                        if (currentPosition == firstNumber) { //对第一张的处理
                            currentPosition++;
                        } else if (currentPosition == allPhotoList.size() - 1) { //最后一张的处理
                            if (allPhotoList.size() == 2) {
                                currentPosition = 1;
                            } else {
                                currentPosition--;
                            }
                        } else {
                            Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 1).getImage_url())
                                    .thumbnail(0.2f).crossFade(0).into(image_00);
                            Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition).getImage_url())
                                    .thumbnail(0.2f).crossFade(0).into(image_01);
                            Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition + 1).getImage_url())
                                    .thumbnail(0.2f).crossFade(0).into(image_02);
                            image_00.reset();
                            image_01.reset();
                            image_02.reset();
                        }
                        //当切换成功后判断是否选择过
                        break;
                    case 2:
                        if (currentPosition < allPhotoList.size() - 1) {
                            currentPosition = currentPosition + 1;
                        } else {
                            currentPosition = allPhotoList.size() - 1;
                        }
                        break;
                }
                //更新选择显示
                setSelect(allPhotoList.get(currentPosition).isSelect());
                //更新标题
                if (photoConfigure.isCamera()) {
                    getTitleBar().setToolbar(currentPosition + "/" + (allPhotoList.size() - 1));
                } else {
                    getTitleBar().setToolbar(currentPosition + 1 + "/" + (allPhotoList.size()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("yuanye222", "state-----" + state);
                if (state == 0) {
                    Log.i("yuanye", "current---" + currentPosition);
                    if (currentPosition == firstNumber) {
                        viewPager.setCurrentItem(0, false);
                    } else if (currentPosition == allPhotoList.size() - 1) {
                        viewPager.setCurrentItem(2, false);
                    } else {
                        viewPager.setCurrentItem(1, false);
                    }
                }
            }
        });
        //预加载处理
        if (currentPosition == firstNumber) {
            viewPager.setCurrentItem(0);
            Log.i("yuanye", "--------" + allPhotoList.size());
            setSelect(allPhotoList.get(currentPosition).isSelect());
            Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition).getImage_url()).into(image_00);
            if (allPhotoList.size() == 2 + firstNumber) {
                Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition + 1).getImage_url()).into(image_01);
            } else if (allPhotoList.size() > 2 + firstNumber) {
                Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition + 1).getImage_url()).into(image_01);
                Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition + 2).getImage_url()).into(image_02);
            }
        } else if (currentPosition == allPhotoList.size() - 1) {
            if (allPhotoList.size() <= 3) {
                if (currentPosition == 1) {
                    //只有两张的情况下的最后一张
                    viewPager.setCurrentItem(1);
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition).getImage_url()).into(image_01);
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 1).getImage_url()).into(image_00);
                } else {
                    viewPager.setCurrentItem(2);
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition).getImage_url()).into(image_02);
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 1).getImage_url()).into(image_01);
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 2).getImage_url()).into(image_00);
                }
            } else {
                //最后一张，其他情况下
                viewPager.setCurrentItem(2);
                Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition).getImage_url()).into(image_02);
                if (currentPosition == 2 + firstNumber) {
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 1).getImage_url()).into(image_01);
                } else if (currentPosition > 2 + firstNumber) {
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 1).getImage_url()).into(image_01);
                    Glide.with(PhotoPreviewActivity.this).load(allPhotoList.get(currentPosition - 2).getImage_url()).into(image_00);
                }
            }

        } else {
            viewPager.setCurrentItem(1);
        }
        //初始化点击图片隐藏显示的布局
        ll_action = (LinearLayout) findViewById(R.id.ll_action);
    }

    //更改多选按钮
    public void updateRightView() {
        if (selectPhotoList.size() == 0) {
            getTitleBar().setRightText("完成");
            getTitleBar().setRightClickEnable(false);
        } else if (selectPhotoList.size() <= photoConfigure.getNum()) {
            getTitleBar().setRightClickEnable(true);
            getTitleBar().setRightText("完成(" + selectPhotoList.size() + "/" + photoConfigure.getNum() + ")");
        }
    }

    //设置选择按钮的状态
    public void setSelect(boolean isSelect) {
        if (isSelect) { //如果已选择
            checkbox.setChecked(true);
            return;
        }
        checkbox.setChecked(false);
    }


    //重写返回按钮
    @Override
    public void onBackPressed() {
        //取消的返回
        if (callback != null)
            callback.onHandlerSuccess(selectPhotoList, allPhotoList);
        Intent intent = getIntent();
        intent.putParcelableArrayListExtra("select_list", selectPhotoList);
        intent.putParcelableArrayListExtra("all_list", allPhotoList);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public int getLayoutId() {
        return R.layout.album_photo_preview;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initData();
        initView();
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private ArrayList<View> Pageviews;

        public ViewPagerAdapter(ArrayList<View> Pageviews) {
            this.Pageviews = Pageviews;
        }

        @Override
        public int getCount() {
            Log.i("count", "count---" + Pageviews.size());
            return Pageviews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(Pageviews.get(position));
            isClick = false;
            final PinchImageView imageView = (PinchImageView) Pageviews.get(position).findViewById(R.id.pinchImageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //图片的点击事件
                    if (isClick) { //显示
                        getTitleBar().setAnimationTitleBarIn();
                        TranslateAnimation animation = new TranslateAnimation(0, 0, ll_action.getHeight(), 0);
                        animation.setDuration(300);//设置动画持续时间
                        ll_action.setAnimation(animation);
                        animation.startNow();
                        ll_action.setVisibility(View.VISIBLE);
                        isClick = false;
                    } else { //隐藏
                        getTitleBar().setAnimationTitleBarOut();
                        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, ll_action.getHeight());
                        animation.setDuration(300);//设置动画持续时间
                        ll_action.setAnimation(animation);
                        animation.startNow();
                        ll_action.setVisibility(View.GONE);
                        isClick = true;
                    }
                }
            });
            return Pageviews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }

    /**
     * 处理结果
     */
    public interface OnHandlerResultCallback {
        /**
         * 处理成功
         *
         * @param selectList
         */
        void onHandlerSuccess(List<PhotoBean> selectList, ArrayList<PhotoBean> allPhotoList);
    }

}

package com.yuan.demo.activity.one.xtoolbar;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.title.ETitleTheme;
import com.yuan.demo.myapplication.R;

@Route(path = "/xtoolbar/ToolbarActivity")
public class ToolbarActivity extends ExtraActivity implements ISwipeBack {

    @Override
    public int getLayoutId() {
        return R.layout.activity_toolbar;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar()
                .setLeftIcon(R.drawable.ic_base_back_white)
                .setDefaultTheme(ETitleTheme.DARK_TRANSPARENT)
                .setTitleAndStatusBgColor(ContextCompat.getColor(this, com.yuan.basemodule.R.color.transparent)).
                setFontColor(ContextCompat.getColor(this, R.color.white))
                .setToolbar("青花瓷");
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        scrollView.setPadding(scrollView.getPaddingLeft(), scrollView.getPaddingTop() + getTitleBar().getTitleBarHeight() +
                        getTitleBar().getStatusBarHeight(),
                scrollView.getPaddingRight(), scrollView.getPaddingBottom());
        ImageView imageView = (ImageView) findViewById(R.id.iv_background);
        GlideHelper.load("http://static.oneplus.cn/data/attachment/forum/201701/05/165732nmebemxb1my1e08e.jpg",imageView);
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }
}

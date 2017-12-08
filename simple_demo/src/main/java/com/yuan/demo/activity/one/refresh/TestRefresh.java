package com.yuan.demo.activity.one.refresh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.IRefresh;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.demo.myapplication.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by YuanYe on 2017/7/12.
 */

@Route(path = "/refresh/TestRefresh")
public class TestRefresh extends ExtraActivity implements IRefresh, ISwipeBack {

    @Override
    public int getLayoutId() {
        return R.layout.refresh_layout;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar().setToolbar("上拉加载、下拉刷新")
                .setRightText("图库", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RouterHelper.from(mContext)
                                .put("camera", false)
                                .put("num", 8)
                                .to("/album/ui/AlbumWallActivity", 1002);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtil.showShort(mContext, "图库返回");
    }

    @Override
    public SmartRefreshLayout getRefreshView() {
        return (SmartRefreshLayout) findViewById(R.id.refreshLayout);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoad() {

    }
}

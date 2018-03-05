package com.yuan.demo.activity.fragmentActivity;


import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yuan.album.ui.AlbumWallActivity;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.router.Interrupt.InterruptCallback;
import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.activity.two.AlbumDemoActivity;
import com.yuan.demo.myapplication.R;
import com.yuan.demo.router.RouterUrl;
import com.yuan.scan.ui.ScanActivity;

import java.util.ArrayList;

/**
 * Created by YuanYe on 2017/7/11.
 */

public class TestFragment2 extends LazyFragement {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_02;
    }

    @Override
    public void initData(Bundle savedInstanceState, View view) {
        getTitleBar().setToolbar("Module功能");
        view.findViewById(R.id.tv_text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(AlbumDemoActivity.class);
            }
        });
        view.findViewById(R.id.tv_text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //城市选择列表
                RouterHelper.from(mContext).to(RouterUrl.mapSelectCityActivity);
            }
        });

        view.findViewById(R.id.tv_text3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转登陆
                RouterHelper.from(mContext).to(RouterUrl.guideActivity);
            }
        });
        view.findViewById(R.id.tv_text9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //扫一扫（二维码）
                startActivityForResult(new Intent(mContext, ScanActivity.class), 1001);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("yuanye", "--------------执行图库回调---");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1001: //扫一扫
                String content = data.getStringExtra(com.yuan.scan.Constant.CODED_CONTENT);
                ToastUtil.showShort(mContext, content);
                break;
        }

    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.SIMPLE_TITLE;
    }
}

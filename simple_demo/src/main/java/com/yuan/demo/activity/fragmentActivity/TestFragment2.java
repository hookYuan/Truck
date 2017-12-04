package com.yuan.demo.activity.fragmentActivity;


import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.demo.myapplication.R;
import com.yuan.demo.router.RouterUrl;
import com.yuan.scan.ui.ScanActivity;

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
                //吊起图库
                RouterHelper.from(mContext)
                        .put("camera", false)
                        .put("num", 8)
                        .to("/album/selectImage/AlbumWallAct");
            }
        });
        view.findViewById(R.id.tv_text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //吊起图库
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
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1001:
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

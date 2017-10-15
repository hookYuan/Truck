package com.yuan.demo.activity.fragmentActivity;


import android.os.Bundle;
import android.view.View;

import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.demo.myapplication.R;
import com.yuan.demo.router.RouterUrl;

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
                RouterHelper.from(mContext).to("/album/selectImage/AlbumWallAct");
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
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.SIMPLE_TITLE;
    }
}

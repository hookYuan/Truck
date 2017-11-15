package com.yuan.demo.activity.fragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.demo.activity.one.shape.ShapeActivity;
import com.yuan.demo.activity.one.textView.TextActivity;
import com.yuan.demo.myapplication.R;
import com.yuan.scan.ui.ScanActivity;

/**
 * Created by YuanYe on 2017/7/10.
 */

public class TestFragment extends LazyFragement {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_01;
    }

    @Override
    public void initData(Bundle savedInstanceState, View view) {
        getTitleBar().setToolbar("基本功能");

        view.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/swipback/SwipBackActivity");
            }
        });

        view.findViewById(R.id.tv_text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/refresh/TestRefresh");
            }
        });
        view.findViewById(R.id.tv_text3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/statusControl/StatueActivity", 1005);
            }
        });
        view.findViewById(R.id.tv_text4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/xtoolbar/ToolbarActivity");
            }
        });
        view.findViewById(R.id.tv_text5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/net/NetActivity");
            }
        });
        view.findViewById(R.id.tv_text6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/dialog/DialogActivity");
            }
        });
        view.findViewById(R.id.tv_text7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterHelper.from(mContext).to("/dialog/PopupActivity");
            }
        });
        view.findViewById(R.id.tv_text8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ShapeActivity.class));
            }
        });
        view.findViewById(R.id.tv_text9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TextActivity.class));
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("yuanye", requestCode + "----" + resultCode);
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.SIMPLE_TITLE;
    }
}

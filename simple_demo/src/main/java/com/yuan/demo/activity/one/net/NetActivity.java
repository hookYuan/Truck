package com.yuan.demo.activity.one.net;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.roundview.RoundTextView;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;
import com.yuan.basemodule.ui.title.OnMenuItemClickListener;
import com.yuan.demo.myapplication.R;
import com.yuan.demo.presenter.PNet;

import java.util.ArrayList;

@Route(path = "/net/NetActivity")
public class NetActivity extends MVPActivity<PNet> implements ISwipeBack, View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("OkHttpUtil");
        menus.add("Retrofit");
        menus.add("说明");
        getTitleBar().setToolbar("OkHttp")
                .setLeftIcon(R.drawable.ic_base_back_white)
                .setRightMenu(R.drawable.ic_base_menu_more_white, menus, new OnMenuItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, AdapterView<?> adapterView, View view, int i) {
                        popupWindow.dismiss();
                        if (i == 2) {
                            new MaterialDialog().alertText(mContext, "说明",
                                    "本例中主要分为两个模块：" +
                                            "\r\n\t\t1.OkHttpUtil。此模块基于OKHttp3.0封装，线程切换采用RxJava.实现了基本的" +
                                            "get、post、上传、下载"
                                            + "\r\n\r\n \t\t\t\t\t\t\t\t\t  ----作者：袁冶"
                                    , null);
                        }
                    }
                });
        initView();
    }


    private void initView() {
        RoundTextView rtvGet = (RoundTextView) findViewById(R.id.rtv_demo_get);
        rtvGet.setOnClickListener(this);
        RoundTextView rtvPost = (RoundTextView) findViewById(R.id.rtv_demo_post);
        rtvPost.setOnClickListener(this);
        RoundTextView rtvDown = (RoundTextView) findViewById(R.id.rtv_demo_down);
        rtvDown.setOnClickListener(this);
        RoundTextView rtvUpdate = (RoundTextView) findViewById(R.id.rtv_demo_update);
        rtvUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_demo_get:
                getP().okUtil();
                break;
            case R.id.rtv_demo_post:
                getP().okHttpPost();
                break;
            case R.id.rtv_demo_down:
                getP().okHttpDown();
                break;
            case R.id.rtv_demo_update:
                getP().okHttpUpdate();
                break;
        }
    }
}

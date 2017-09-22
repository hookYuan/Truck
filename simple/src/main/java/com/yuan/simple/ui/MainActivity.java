package com.yuan.simple.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.ui.base.activity.FragmentActivity;
import com.yuan.simple.R;
import com.yuan.simple.ui.fragment.BaseFragment;
import com.yuan.simple.ui.fragment.FriendsFragment;
import com.yuan.simple.ui.fragment.MineFragment;
import com.yuan.simple.ui.fragment.ShoppingFragment;

@Route(path = "/simple/ui/MainActivity")
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState, View view) {
        initView();
    }

    private void initView() {
        addFragment(R.id.rl_main_content,BaseFragment.class,
                ShoppingFragment.class, FriendsFragment.class, MineFragment.class);

        LinearLayout llMain1 = (LinearLayout) findViewById(R.id.ll_main_01);
        llMain1.setOnClickListener(this);

        LinearLayout llMain2 = (LinearLayout) findViewById(R.id.ll_main_02);
        llMain2.setOnClickListener(this);

        LinearLayout llMain3 = (LinearLayout) findViewById(R.id.ll_main_03);
        llMain3.setOnClickListener(this);

        LinearLayout llMain4 = (LinearLayout) findViewById(R.id.ll_main_04);
        llMain4.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_main_01:
                showFragment(0);
                break;
            case R.id.ll_main_02:
                showFragment(1);
                break;
            case R.id.ll_main_03:
                showFragment(2);
                break;
            case R.id.ll_main_04:
                showFragment(3);
                break;
        }
    }
}

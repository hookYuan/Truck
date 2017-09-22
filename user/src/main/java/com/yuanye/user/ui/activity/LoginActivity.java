package com.yuanye.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.ui.base.activity.FragmentActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuanye.user.R;
import com.yuanye.user.ui.fragment.LoginFragment;


/**
 * 登录页
 * Created by YuanYe on 2017/8/10.
 */
@Route(path = "/user/ui/activity/LoginActivity")
public class LoginActivity extends FragmentActivity implements ISwipeBack {

    @Override
    public int getLayoutId() {
        return R.layout.u_act_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        addFragment(R.id.u_rl_content,LoginFragment.class);
        showFragment(0); //显示第一个Fragment
    }
}

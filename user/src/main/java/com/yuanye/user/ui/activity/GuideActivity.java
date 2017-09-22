package com.yuanye.user.ui.activity;

import android.os.Bundle;
import android.view.View;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuanye.user.R;

/**
 * 引导页(第一次安装的时候展示)
 * Created by YuanYe on 2017/8/11.
 */
public class GuideActivity extends ExtraActivity {

    @Override
    public int getLayoutId() {
        return R.layout.u_act_guide;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}

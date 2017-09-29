package com.yuan.demo.activity.one.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.demo.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/swipback/SwipBackActivity")
public class SwipBackActivity extends ExtraActivity implements ISwipeBack {

    @BindView(R.id.tv_bing)
    TextView tv_bing;

    @OnClick(R.id.tv_bing)
    void Click() {
        ToastUtil.showShort(mContext, "-------袁冶---------");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_swip_back;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar().setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar("右滑返回");
    }

}

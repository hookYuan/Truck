package com.yuan.demo.activity.one.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.dialog.v7.DialogHelper;
import com.yuan.demo.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/swipback/SwipBackActivity")
public class SwipBackActivity extends ExtraActivity implements ISwipeBack {

    @BindView(R.id.tv_bing)
    TextView tv_bing;

    Demoa a;
    Demoa b;

    @OnClick(R.id.tv_bing)
    void Click() {
        a = new Demoa(100, 200, new Demoa.DemoB(300, 400));
        b = a;
//        b = new Demoa(a.getParam01(), a.getParam02(), a.getDemoB());
        a.setParam01(500);
        a.setParam02(600);
        a.setDemoB(new Demoa.DemoB(700, 800));

        new DialogHelper(mContext).alertText("a--" + a.toString() + "---b--" + b.toString());
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

    public static class Demoa {
        int param01;
        int param02;

        DemoB demoB;

        public static class DemoB {
            int param03;
            int param04;

            public DemoB(int param03, int param04) {
                this.param03 = param03;
                this.param04 = param04;
            }

            public int getParam03() {
                return param03;
            }

            public void setParam03(int param03) {
                this.param03 = param03;
            }

            public int getParam04() {
                return param04;
            }

            public void setParam04(int param04) {
                this.param04 = param04;
            }

            @Override
            public String toString() {
                return "DemoB{" +
                        "param03=" + param03 +
                        ", param04=" + param04 +
                        '}';
            }
        }

        public Demoa(int param01, int param02) {
            this.param01 = param01;
            this.param02 = param02;
        }

        public Demoa(int param01, int param02, DemoB demoB) {
            this.param01 = param01;
            this.param02 = param02;
            this.demoB = demoB;
        }

        public DemoB getDemoB() {
            return demoB;
        }

        public void setDemoB(DemoB demoB) {
            this.demoB = demoB;
        }

        public int getParam01() {
            return param01;
        }

        public void setParam01(int param01) {
            this.param01 = param01;
        }

        public int getParam02() {
            return param02;
        }

        public void setParam02(int param02) {
            this.param02 = param02;
        }

        @Override
        public String toString() {
            return "Demoa{" +
                    "param01=" + param01 +
                    ", param02=" + param02 +
                    ", demoB=" + demoB +
                    '}';
        }
    }

}

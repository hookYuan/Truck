package com.yuan.demo.activity.one.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.roundview.RoundTextView;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.dialog.custom.RxDialog;
import com.yuan.basemodule.ui.dialog.custom.RxScaleAnimation;
import com.yuan.basemodule.ui.dialog.custom.RxTranslateAnimation;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;
import com.yuan.basemodule.ui.title.OnMenuItemClickListener;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;

@Route(path = "/dialog/PopupActivity")
public class RxDialogActivity extends MVPActivity implements ISwipeBack, View.OnClickListener {
    private int dialogType = 0; //动画类型
    private final int SCALETYPE = 0;//缩放动画
    private final int TRANSLATEBOTTOM = 1;//底部弹出动画

    private RoundTextView rtv_demo_top, rtv_demo_bottom, rtv_demo_left, rtv_demo_right;

    @Override
    protected void initData(Bundle savedInstanceState) {
        ArrayList<String> data = new ArrayList<>();
        data.add("缩放动画");
        data.add("平移动画");
        data.add("说明");

        getTitleBar().setToolbar("RxDialog")
                .setLeftIcon(R.drawable.ic_base_back_white)
                .setRightMenu(R.drawable.ic_base_menu_more_white, data, new OnMenuItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, AdapterView<?> adapterView, View view, int i) {

                        switch (i) {
                            case SCALETYPE:
                                rtv_demo_bottom.setText("底部弹窗");
                                rtv_demo_top.setText("顶部弹窗");
                                rtv_demo_left.setText("左边弹窗");
                                rtv_demo_right.setText("右边弹窗");
                                dialogType = i;
                                break;

                            case TRANSLATEBOTTOM:
                                rtv_demo_bottom.setText("下向上弹窗");
                                rtv_demo_top.setText("上向下弹窗");
                                rtv_demo_left.setText("左向右弹窗");
                                rtv_demo_right.setText("右向左弹窗");
                                dialogType = i;
                                break;
                            case 2:
                                new MaterialDialog().alertText(mContext, "使用说明"
                                        , "RxDialog为自定义弹窗，一共有两种配置参数方法：" +
                                                "\r\n\t\t\t1.构建RxDialog时，传入RxDialogParams设置." +
                                                "\r\n\t\t\t2.采用Build方式设置基本参数." +
                                                "\r\n\r\nRxDialog支持自定义弹窗动画，本例中实现了缩放和平移动画。如果" +
                                                "需要其他动画，需IDialogAnimation接口。" +
                                                "RxDialog支持三种控制弹窗位置的方式：" +
                                                "\r\n\t\t\t1.通过params直接指定dialog的x、y坐标." +
                                                "\r\n\t\t\t2.通过设置Gravity指定弹窗基于屏幕的位置." +
                                                "\r\n\t\t\t3.通过View设置弹出位置为View四周." +
                                                "\r\n\r\n \t\t\t\t\t\t\t\t\t  ----作者：袁冶"
                                        , null);
                                break;
                        }

                        popupWindow.dismiss();
                    }
                });

        initView();
    }

    private void initView() {
        rtv_demo_top = (RoundTextView) findViewById(R.id.rtv_demo_top);
        rtv_demo_top.setOnClickListener(this);

        rtv_demo_bottom = (RoundTextView) findViewById(R.id.rtv_demo_bottom);
        rtv_demo_bottom.setOnClickListener(this);

        rtv_demo_left = (RoundTextView) findViewById(R.id.rtv_demo_left);
        rtv_demo_left.setOnClickListener(this);

        rtv_demo_right = (RoundTextView) findViewById(R.id.rtv_demo_right);
        rtv_demo_right.setOnClickListener(this);

        dialogView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_popup, null);

        rtv_demo_target = (RoundTextView) findViewById(R.id.rtv_demo_target);

    }

    private RoundTextView rtv_demo_target;
    private View dialogView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_popup;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_demo_top: //按钮一
                switch (dialogType) {
                    case SCALETYPE:
                        new RxDialog(dialogView)
                                .setAnimation(new RxScaleAnimation())
                                .setViewTop(rtv_demo_target)
                                .show();
                        break;
                    case TRANSLATEBOTTOM:
                        new RxDialog(dialogView)
                                .setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
                                .setAnimation(new RxTranslateAnimation(RxTranslateAnimation.topToBottom))
                                .show();
                        break;
                }

                break;
            case R.id.rtv_demo_bottom://按钮二
                switch (dialogType) {
                    case SCALETYPE:
                        new RxDialog(dialogView)
                                .setAnimation(new RxScaleAnimation())
                                .setViewBottom(rtv_demo_target)
                                .show();
                        break;
                    case TRANSLATEBOTTOM:
                        new RxDialog(dialogView)
                                .setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                                .setAnimation(new RxTranslateAnimation(RxTranslateAnimation.bottomToTop))
                                .show();
                        break;
                }
                break;
            case R.id.rtv_demo_left://按钮三
                switch (dialogType) {
                    case SCALETYPE:
                        new RxDialog(dialogView)
                                .setAnimation(new RxScaleAnimation())
                                .setViewLeft(rtv_demo_target)
                                .show();
                        break;
                    case TRANSLATEBOTTOM:
                        new RxDialog(dialogView)
                                .setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL)
                                .setAnimation(new RxTranslateAnimation(RxTranslateAnimation.leftToRight))
                                .show();
                        break;
                }
                break;
            case R.id.rtv_demo_right://按钮四
                switch (dialogType) {
                    case SCALETYPE:
                        new RxDialog(dialogView)
                                .setAnimation(new RxScaleAnimation())
                                .setViewRight(rtv_demo_target)
                                .show();
                        break;
                    case TRANSLATEBOTTOM:
                        new RxDialog(dialogView)
                                .setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                                .setAnimation(new RxTranslateAnimation(RxTranslateAnimation.rightToLeft))
                                .show();
                        break;
                }
                break;
        }
    }


}

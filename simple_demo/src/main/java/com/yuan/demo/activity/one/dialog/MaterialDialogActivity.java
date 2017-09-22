package com.yuan.demo.activity.one.dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;
import com.yuan.basemodule.ui.dialog.v7.OnSelectMultiListener;
import com.yuan.basemodule.ui.dialog.v7.OnSelectSingleListener;
import com.yuan.basemodule.ui.title.OnMenuItemClickListener;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/dialog/DialogActivity")
public class MaterialDialogActivity extends ExtraActivity implements ISwipeBack {

    int i = 0;

    @Override
    protected void initData(Bundle savedInstanceState) {
        List<String> data = new ArrayList<>();
        data.add("说明");
        getTitleBar().setToolbar("MaterialDialog").setLeftIcon(R.drawable.ic_base_back_white)
            .setRightMenu(R.drawable.ic_base_menu_more_white, data, new OnMenuItemClickListener() {
                @Override
                public void onItemClick(PopupWindow popupWindow, AdapterView<?> adapterView, View view, int i) {
                    popupWindow.dismiss();
                    new MaterialDialog().alertText(mContext,"说明",
                            "本例中主要使用MaterialDialog,MaterialDialog主要是基于v7包" +
                                    "下AlertDialog做的一个简单封装，用法详见demo中的事例代码。" +
                                    "对于MaterialDialog如果需要修改标题和文字等颜色，可以通过设置单个MaterialDialog主题来达到" +
                                    "相应的效果。MaterialDialog默认颜色为Style文件中colorAccent所指定的颜色。"+
                                    "\r\n\r\n \t\t\t\t\t\t\t\t\t  ----作者：袁冶"
                            ,null);
                }
            });

        findViewById(R.id.btn_simple_net_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog().alertText(mContext, "这是一个很重要的消息", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShort(mContext, "确定");
                    }
                });
            }
        });
        findViewById(R.id.btn_simple_dialog_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] mData = {"长春", "重庆", "北京", "上海", "成都", "开封", "广东",
                        "长春", "重庆", "北京", "上海", "成都", "开封"};
                new MaterialDialog().alertList(mContext, "城市", mData, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtil.showShort(mContext, "您选择了" + mData[i]);
                    }
                });
            }
        });
        findViewById(R.id.btn_simple_dialog_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] mData = {"长春", "重庆", "北京", "上海", "成都"};
                new MaterialDialog().alertSingle(mContext, "城市", mData, new OnSelectSingleListener() {
                    @Override
                    public void onSelect(int pos) {
                        ToastUtil.showShort(mContext, "您选择的是" + mData[pos]);
                    }
                });
            }
        });
        findViewById(R.id.btn_simple_dialog_multi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] mData = {"长春", "重庆", "北京", "上海", "成都"};
                new MaterialDialog().alertMulti(mContext, "城市", mData, new OnSelectMultiListener() {
                    @Override
                    public void onSelect(List<Integer> selectList) {
                        ToastUtil.showShort(mContext, "选中的有" + selectList.size());
                    }
                });
            }
        });
        findViewById(R.id.btn_simple_dialog_waiting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog().alertWaiting(mContext, "提示", "加载中...");
            }
        });

        findViewById(R.id.btn_simple_dialog_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i >= 100) {
                    new MaterialDialog().alertText(mContext, "已经下载完成，是否重新下载？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            i = 0;
                            findViewById(R.id.btn_simple_dialog_loading).performClick();
                        }
                    });
                    return;
                }
                final ProgressDialog dialog =
                        new MaterialDialog().alertProgress(mContext, "下载", 100);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (i >= 100) {
                                dialog.dismiss();
                                return;
                            }
                            dialog.setProgress(i);
                            i = i + 1;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_simple_dialog_my).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_popup, null);
                new MaterialDialog().alertCustom("",dialogView);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dialog;
    }
}

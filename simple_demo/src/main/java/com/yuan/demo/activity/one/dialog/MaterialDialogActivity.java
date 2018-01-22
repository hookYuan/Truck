package com.yuan.demo.activity.one.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.roundview.RoundTextView;
import com.yuan.basemodule.common.adapter.RLVAdapter;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.dialog.v7.DialogHelper;
import com.yuan.basemodule.ui.dialog.v7.OnMultiListener;
import com.yuan.basemodule.ui.title.OnMenuItemClickListener;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        new DialogHelper(mContext).alertText("说明",
                                "本例中主要使用MaterialDialog,MaterialDialog主要是基于v7包" +
                                        "下AlertDialog做的一个简单封装，用法详见demo中的事例代码。" +
                                        "对于MaterialDialog如果需要修改标题和文字等颜色，可以通过设置单个MaterialDialog主题来达到" +
                                        "相应的效果。MaterialDialog默认颜色为Style文件中colorAccent所指定的颜色。" +
                                        "\r\n\r\n \t\t\t\t\t\t\t\t\t  ----作者：袁冶"
                                , null);
                    }
                });

        final String[] mData = {"普通Dialog", "列表Dialog", "单选Dialog", "多选Dialog", "等待Dialog", "进度条Dialog"
                , "自定义Dialog", "日期Dialog", "时间Dialog"};
        RecyclerView recyclerView = Views.find(mContext, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new RLVAdapter(mContext) {

            DialogHelper dialogHelper = new DialogHelper(mContext);

            @Override
            public int getItemLayout(ViewGroup parent, int viewType) {
                return R.layout.dialog_item;
            }

            @Override
            public void onBindHolder(ViewHolder holder, int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(mData[position]);
            }

            @Override
            public void onItemClick(ViewHolder holder, View view, int position) {
                switch (position) {
                    case 0:
                        new DialogHelper(mContext).alertText("这是一个很重要的消息", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtil.showShort(mContext, "确定");
                            }
                        });
                        break;
                    case 1:
                        final String[] listData = {"长春", "重庆", "北京", "上海", "成都", "开封", "广东",
                                "长春", "重庆", "北京", "上海", "成都", "开封"};
                        new DialogHelper(mContext).alertList("城市", listData, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtil.showShort(mContext, "您选择了" + listData[i]);
                            }
                        });
                        break;
                    case 2:
                        final String[] singleData = {"长春", "重庆", "北京", "上海", "成都"};
                        new DialogHelper(mContext).alertSingle("城市", singleData, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtil.showShort(mContext, "您选择的是" + singleData[i]);
                            }
                        });
                        break;
                    case 3:
                        final String[] mData = {"长春", "重庆", "北京", "上海", "成都"};
                        new DialogHelper(mContext).alertMulti("城市", mData, new OnMultiListener() {
                            @Override
                            public void onClick(DialogInterface dialog, Map<Integer, String> selects) {
                                ToastUtil.showShort(mContext, "选中的有" + selects.toString());
                            }
                        });
                        break;
                    case 4:
                        new DialogHelper(mContext).alertWait("提示", "加载中...");
                        break;
                    case 5:
                        if (i >= 100) {
                            new DialogHelper(mContext).alertText("已经下载完成，是否重新下载？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int pos) {
                                    i = 0;
                                }
                            });
                            return;
                        }
                        final DialogHelper dialog = new DialogHelper(mContext);
                        dialog.alertProgress("下载", 100);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    if (i >= 100) {
                                        dialog.dismiss();
                                        return;
                                    }
                                    dialog.setProgressCurrent(i);
                                    i = i + 1;
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        break;
                    case 6:
                        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_popup, null);
                        new DialogHelper(mContext).alertView(dialogView);
                        break;
                    case 7:
                        new DialogHelper(mContext).alertDate(new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                ToastUtil.showShort(mContext, i + "-" + i1 + "-" + i2);
                            }
                        });
                        break;
                    case 8:
                        new DialogHelper(mContext).alertTime(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                ToastUtil.showShort(mContext, i + "--" + i1);
                            }
                        });
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return mData.length;
            }
        });

//        final RoundTextView textView = Views.find(this, R.id.btn_simple_net_dialog);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DialogHelper(mContext)
//                        .alertText("这是一个很重要的消息", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                ToastUtil.showShort(mContext, "确定");
//                            }
//                        });
//            }
//        });
//        findViewById(R.id.btn_simple_dialog_list).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String[] mData = {"长春", "重庆", "北京", "上海", "成都", "开封", "广东",
//                        "长春", "重庆", "北京", "上海", "成都", "开封"};
//                new DialogHelper(mContext).alertList("城市", mData, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtil.showShort(mContext, "您选择了" + mData[i]);
//                    }
//                });
//            }
//        });
//
//        final DialogHelper singleDialog = new DialogHelper(mContext);
//        findViewById(R.id.btn_simple_dialog_single).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String[] mData = {"长春", "重庆", "北京", "上海", "成都"};
//                singleDialog.alertSingle("城市", mData, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ToastUtil.showShort(mContext, "您选择的是" + mData[i]);
//                    }
//                });
//            }
//        });
//        final DialogHelper multiDialog = new DialogHelper(mContext);
//        findViewById(R.id.btn_simple_dialog_multi).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String[] mData = {"长春", "重庆", "北京", "上海", "成都"};
//                multiDialog.alertMulti("城市", mData, new OnMultiListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, Map<Integer, String> selects) {
//                        ToastUtil.showShort(mContext, "选中的有" + selects.toString());
//                    }
//                });
//            }
//        });
//        findViewById(R.id.btn_simple_dialog_waiting).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DialogHelper(mContext).alertWait("提示", "加载中...");
//            }
//        });
//
//        findViewById(R.id.btn_simple_dialog_loading).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (i >= 100) {
//                    new DialogHelper(mContext).alertText("已经下载完成，是否重新下载？", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int pos) {
//                            i = 0;
//                            findViewById(R.id.btn_simple_dialog_loading).performClick();
//                        }
//                    });
//                    return;
//                }
//                final DialogHelper dialog = new DialogHelper(mContext);
//                dialog.alertProgress("下载", 100);
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            if (i >= 100) {
//                                dialog.dismiss();
//                                return;
//                            }
//                            dialog.setProgressCurrent(i);
//                            i = i + 1;
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//            }
//        });
//        final TextView myTextView = (TextView) findViewById(R.id.btn_simple_dialog_my);
//        myTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View dialogView = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_popup, null);
//                new DialogHelper(mContext)
//                        .alertView(dialogView);
//            }
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dialog;
    }
}

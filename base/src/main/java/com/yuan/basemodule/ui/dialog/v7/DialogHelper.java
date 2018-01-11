package com.yuan.basemodule.ui.dialog.v7;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YuanYe on 2017/9/13.
 * 对v7包下的AlertDialog进行简单封装，方便使用
 * 整体风格采用Material原生风格
 */
public class DialogHelper {

    private AlertDialog.Builder dialog;
    private Context mContext;

    public DialogHelper(Context context) {
        this.mContext = context;
        dialog = new AlertDialog.Builder(context);
    }

    /**
     * @param context
     * @param themeResId 主题样式
     */
    public DialogHelper(Context context, @StyleRes int themeResId) {
        dialog = new AlertDialog.Builder(context);
    }

    /**
     * 关闭弹窗
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.create().dismiss();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * ************************文本Dialog*****************************************************************
     */
    /**
     * @param title            标题
     * @param message          正文
     * @param positveText      右侧按钮
     * @param neutralText      中间按钮
     * @param negativeText     左侧按钮
     * @param positiveListener
     * @param neutralListener
     * @param negativeListener
     * @param isCancel         是否点击外部可以取消  false--不可取消
     */
    public void alertText(String title, String message
            , String positveText, String neutralText, String negativeText
            , DialogInterface.OnClickListener positiveListener
            , DialogInterface.OnClickListener neutralListener
            , DialogInterface.OnClickListener negativeListener
            , boolean isCancel) {
        //可以通过R.style.MaterialDialog修改Dialog颜色等
        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        if (!TextUtils.isEmpty(message)) dialog.setMessage(message);
        if (!TextUtils.isEmpty(positveText))
            dialog.setPositiveButton(positveText, positiveListener);
        if (!TextUtils.isEmpty(neutralText))
            dialog.setNeutralButton(neutralText, neutralListener);
        if (!TextUtils.isEmpty(negativeText))
            dialog.setNegativeButton(negativeText, negativeListener);
        dialog.setCancelable(isCancel);
        // 显示
        dialog.show();
    }

    public void alertText(String message, DialogInterface.OnClickListener positiveListener) {
        alertText("提示", message, "确定", "", "取消", positiveListener, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, false);
    }

    public void alertText(String title, String message, DialogInterface.OnClickListener positiveListener) {
        alertText(title, message, "确定", "", "", positiveListener, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, false);
    }

    public void alertText(String message) {
        alertText("提示", message, "确定", "", "", null, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, false);
    }

    /**
     * ************************列表Dialog*****************************************************************
     */
    public void alertList(String title, List<String> mData, boolean isCancel, DialogInterface.OnClickListener listener) {
        int size = mData.size();
        String[] array = mData.toArray(new String[size]);
        alertList(title, array, isCancel, listener);
    }

    public void alertList(String title, List<String> mData, DialogInterface.OnClickListener listener) {
        alertList(title, mData, true, listener);
    }

    public void alertList(String title, String[] mData, DialogInterface.OnClickListener listener) {
        alertList(title, mData, true, listener);
    }

    public void alertList(String title, String[] mData, boolean isCancel, DialogInterface.OnClickListener listener) {
        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        dialog.setItems(mData, listener);
        dialog.setCancelable(isCancel);
        dialog.show();
    }

    /**
     * ************************单选Dialog*****************************************************************
     */
    private int choicePosition = 0; //默认单选的位置

    public void alertSingle(String title, List<String> mData, final DialogInterface.OnClickListener listener) {
        alertSingle(title, mData, "确定", listener);
    }

    public void alertSingle(String title, List<String> mData, final String positiveText, final DialogInterface.OnClickListener listener) {
        int size = mData.size();
        String[] array = mData.toArray(new String[size]);
        alertSingle(title, array, positiveText, true, listener);
    }

    public void alertSingle(String title, String[] mData, final DialogInterface.OnClickListener listener) {
        alertSingle(title, mData, "确定", true, listener);
    }

    public void alertSingle(String title, String[] mData, final String positiveText, boolean isCancel, final DialogInterface.OnClickListener listener) {
        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);

        dialog.setSingleChoiceItems(mData, choicePosition,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        choicePosition = position;
                        if (TextUtils.isEmpty(positiveText)) {
                            listener.onClick(dialog, choicePosition);
                        }
                    }
                });

        if (!TextUtils.isEmpty(positiveText)) {
            dialog.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.onClick(dialog, choicePosition);
                        }
                    });
        }
        dialog.setCancelable(isCancel);
        dialog.show();
    }

    /**
     * ************************多选Dialog*****************************************************************
     */
    private Map<Integer, String> selects = new HashMap<>();

    private boolean[] choices; //初始化item的选中状态

    public void alertMulti(String title, List<String> mData, boolean isCancel, final OnMultiListener listener) {
        int size = mData.size();
        String[] array = mData.toArray(new String[size]);
        alertMulti(title, array, null, isCancel, listener);
    }

    public void alertMulti(String title, List<String> mData, final OnMultiListener listener) {
        alertMulti(title, mData, true, listener);
    }

    public void alertMulti(String title, final String[] mData, final OnMultiListener listener) {
        alertMulti(title, mData, null, true, listener);
    }

    public void alertMulti(String title, final String[] mData, boolean isCancel, final OnMultiListener listener) {
        alertMulti(title, mData, null, isCancel, listener);
    }

    /**
     * @param title      标题
     * @param mData      数据项
     * @param choiceItem 定义数据每项初始化选中状态数组
     * @param isCancel   是否点击外部取消
     * @param listener   选中数据回调
     */
    public void alertMulti(String title, final String[] mData, final boolean[] choiceItem, boolean isCancel, final OnMultiListener listener) {
        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        if (choices == null && choiceItem != null) this.choices = choiceItem;
        if (choices == null) this.choices = new boolean[mData.length];

        dialog.setMultiChoiceItems(mData, choices,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        choices[which] = isChecked;
                        if (isChecked) {
                            selects.put(which, mData[which]);
                        } else {
                            selects.remove(which);
                        }
                    }
                });

        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClick(dialog, selects);
                    }
                });
        dialog.setCancelable(isCancel);
        dialog.show();
    }

    /**
     * ************************等待Dialog*****************************************************************
     */
    private ProgressDialog progressDialog;

    public void alertWait(String title, String message) {
        alertWait(title, message, true);
    }

    public void alertWait(String title, String message, boolean isCancel) {
        /* 等待Dialog具有屏蔽其他控件的交互能力
         * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
         * 下载等事件完成后，主动调用函数关闭该Dialog
         */
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        if (!TextUtils.isEmpty(title)) progressDialog.setTitle(title);
        if (!TextUtils.isEmpty(message)) progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(isCancel);
        progressDialog.show();
    }


    /**
     * ************************进度条Dialog*****************************************************************
     */
    public void alertProgress(String title, int max, int current, boolean isCancel) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.setProgress(current);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(max);
        progressDialog.setCancelable(isCancel);
        progressDialog.show();
    }

    public void alertProgress(String title, int max) {
        alertProgress(title, max, 0, true);
    }

    /**
     * 设置当前进度
     *
     * @param current
     */
    public void setProgressCurrent(int current) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
        }
        progressDialog.setProgress(current);
    }

    /**
     * ************************自定义Dialog*****************************************************************
     */
    public void alertView(final String title, View view, boolean isCancel) {
        if (!TextUtils.isEmpty(title)) dialog.setTitle(title);
        dialog.setView(view);
        dialog.setCancelable(isCancel);
        dialog.show();
    }

    public void alertView(View view) {
        alertView("", view, true);
    }

}

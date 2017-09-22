package com.yuan.basemodule.ui.dialog.v7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.yuan.basemodule.R;

import java.util.ArrayList;

/**
 * Created by YuanYe on 2017/9/13.
 * 对v7包下的AlertDialog进行简单封装，方便使用
 * 整体风格采用Material原生风格
 */
public class MaterialDialog {


    public MaterialDialog() {

    }

    /**
     * ************************文本Dialog*****************************************************************
     */
    /**
     * @param context
     * @param title            标题
     * @param message          正文
     * @param positveText      右侧按钮
     * @param neutralText      中间按钮
     * @param negativeText     左侧按钮
     * @param positiveListener
     * @param neutralListener
     * @param nagativeListener
     * @param isCancel         是否点击外部可以取消  false--不可取消
     */
    public void alertText(final Context context, String title, String message
            , String positveText, String neutralText, String negativeText
            , DialogInterface.OnClickListener positiveListener
            , DialogInterface.OnClickListener neutralListener
            , DialogInterface.OnClickListener nagativeListener
            , boolean isCancel) {
        //可以通过R.style.MaterialDialog修改Dialog颜色等
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context, R.style.MaterialDialogTheme);
        if (!TextUtils.isEmpty(title))
            normalDialog.setTitle(title);
        normalDialog.setMessage(message);
        if (!TextUtils.isEmpty(positveText))
            normalDialog.setPositiveButton(positveText, positiveListener);
        if (!TextUtils.isEmpty(neutralText))
            normalDialog.setNeutralButton(neutralText, neutralListener);
        if (!TextUtils.isEmpty(negativeText))
            normalDialog.setNegativeButton(negativeText, nagativeListener);
        normalDialog.setCancelable(isCancel);
        // 显示
        normalDialog.show();
    }

    public void alertText(final Context context, String message, DialogInterface.OnClickListener positiveListener) {
        alertText(context, "提示", message, "确定", "", "取消", positiveListener, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, false);
    }
    public void alertText(final Context context,String title, String message, DialogInterface.OnClickListener positiveListener) {
        alertText(context, title, message, "确定", "", "", positiveListener, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, false);
    }

    public void alertText(final Context context, String message) {
        alertText(context, "提示", message, "确定", "", "", null, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, false);
    }

    /**
     * ************************列表Dialog*****************************************************************
     */
    /**
     * @param context
     * @param title
     * @param mData    列表数据源
     * @param listener item 点击事件
     */
    public void alertList(final Context context, String title, String[] mData, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            listDialog.setTitle(title);
        listDialog.setItems(mData, listener);
        listDialog.setCancelable(false);
        listDialog.show();
    }

    /**
     * ************************单选Dialog*****************************************************************
     */
    private int yourChoice;

    public void alertSingle(final Context context, String title, String[] mData, final OnSelectSingleListener listener) {
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle(title);
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(mData, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            listener.onSelect(yourChoice);
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    /**
     * ************************多选选Dialog*****************************************************************
     */
    private ArrayList<Integer> yourChoices = new ArrayList<>();

    public void alertMulti(final Context context, String title, String[] mData, final OnSelectMultiListener listener) {
        // 设置默认选中的选项，全为false默认均未选中
        final boolean initChoiceSets[] = new boolean[mData.length];
        yourChoices.clear();
        AlertDialog.Builder multiChoiceDialog =
                new AlertDialog.Builder(context);
        multiChoiceDialog.setTitle(title);
        multiChoiceDialog.setMultiChoiceItems(mData, initChoiceSets,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            yourChoices.add(which);
                        } else {
                            yourChoices.remove(which);
                        }
                    }
                });
        multiChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSelect(yourChoices);
                    }
                });
        multiChoiceDialog.show();
    }

    /**
     * ************************等待Dialog*****************************************************************
     */
    public void alertWaiting(final Context context, String title, String message) {
    /* 等待Dialog具有屏蔽其他控件的交互能力
     * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
     * 下载等事件完成后，主动调用函数关闭该Dialog
     */
        ProgressDialog waitingDialog =
                new ProgressDialog(context);
        waitingDialog.setTitle(title);
        waitingDialog.setMessage(message);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(true);
        waitingDialog.show();
    }

    /**
     * ************************进度条Dialog*****************************************************************
     */
    public ProgressDialog alertProgress(final Context context, final String title, final int max, int current) {
        ProgressDialog progressDialog =
                new ProgressDialog(context);
        progressDialog.setProgress(current);
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(max);
        progressDialog.setCancelable(true);
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog alertProgress(final Context context, final String title, final int max) {
        return alertProgress(context, title, max, 0);
    }

    /**
     * ************************输入框Dialog*****************************************************************
     */
    public void alertEdit(final Context context, final String title, DialogInterface.OnClickListener listener) {
        final EditText editText = new EditText(context);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            inputDialog.setTitle(title);
        inputDialog.setView(editText);
        inputDialog.setPositiveButton("确定", listener);
        inputDialog.setCancelable(true);
        inputDialog.show();
    }

    /**
     * ************************自定义Dialog*****************************************************************
     */
    public void alertCustom(final String title, View view) {
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(view.getContext());
        if (!TextUtils.isEmpty(title))
            inputDialog.setTitle(title);
        inputDialog.setView(view);
        inputDialog.setCancelable(true);
        inputDialog.show();
    }

    public void alertCustom(View view) {
        alertCustom("", view);
    }

}

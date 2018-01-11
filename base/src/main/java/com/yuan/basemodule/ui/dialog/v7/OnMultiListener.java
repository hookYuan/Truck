package com.yuan.basemodule.ui.dialog.v7;

import android.content.DialogInterface;

import java.util.Map;

/**
 * Created by YuanYe on 2017/9/13.
 */

public interface OnMultiListener {
    void onClick(DialogInterface dialog, Map<Integer, String> selects);
}

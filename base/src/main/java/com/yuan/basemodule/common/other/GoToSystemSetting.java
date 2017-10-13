package com.yuan.basemodule.common.other;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by YuanYe on 2017/10/13.
 * 跳转系统设置界面
 * 常见设置跳转
 * ACTION_SETTINGS                  跳转到设置界面
 * Settings.ACTION_WIFI_SETTINGS    跳转Wifi列表设置
 * ACTION_SOUND_SETTINGS            跳转到声音设置界面
 */
public class GoToSystemSetting {

    // 跳转系统设置界面
    public static void open(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        mContext.startActivity(intent);
    }
}

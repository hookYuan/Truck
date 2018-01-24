package com.yuan.basemodule.common.other;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by YuanYe on 2018/1/24.
 */

public class SysHelper {

    public static class StartSystem {
        // 跳转系统设置界面
        public static void startSetting(Context mContext) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            mContext.startActivity(intent);
        }

        // 跳转Wifi设置界面
        public static void startWifi(Context mContext) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            mContext.startActivity(intent);
        }

        // 跳转声音设置界面
        public static void startSound(Context mContext) {
            Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
            mContext.startActivity(intent);
        }
    }
}

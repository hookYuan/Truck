package com.yuan.album.helper;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/12/8.
 * 用于管理Activity
 */
public class ActivityManagerHelpder {

    private List<Activity> activities;

    private static ActivityManagerHelpder managerHelpder;

    private ActivityManagerHelpder() {
        activities = new ArrayList<>();
    }

    public static ActivityManagerHelpder getInstance() {
        if (managerHelpder == null) {
            managerHelpder = new ActivityManagerHelpder();
        }
        return managerHelpder;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 采用后进先出，先进后出的方式，保证Activity的serResult正常运行
     */
    public void finishAllActivity() {
        for (int i = activities.size() - 1; i >= 0; i--) {
            activities.get(i).finish();
        }
        activities.clear();
    }
}

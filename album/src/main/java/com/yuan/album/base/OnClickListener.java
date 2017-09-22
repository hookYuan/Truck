package com.yuan.album.base;

import android.view.View;

import java.util.Calendar;

/**
 * 作者：yuanYe创建于2016/12/17
 * QQ：962851730
 * 建议只有在页面跳转的时候使用该方法
 */

public abstract class OnClickListener implements View.OnClickListener {

    //两次点击事件的时间 间隔
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    /**
     *  防止出现多次点击
     */
    public abstract void onNoDoubleClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
}

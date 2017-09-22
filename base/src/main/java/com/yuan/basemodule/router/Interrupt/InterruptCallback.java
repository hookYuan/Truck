package com.yuan.basemodule.router.Interrupt;


import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/4/25 16:00
 */

public abstract class InterruptCallback implements NavigationCallback {

    @Override
    public void onFound(Postcard postcard) {
        Log.i("yuanye",postcard.toString()+"---onFound");
    }

    @Override
    public void onLost(Postcard postcard) {
        Log.i("yuanye",postcard.toString()+"---onLost");
    }

    @Override
    public void onArrival(Postcard postcard) {
        Log.i("yuanye",postcard.toString()+"---onArrival");
    }

    @Override
    public abstract void onInterrupt(Postcard postcard);
}

package com.yuan.demo;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;
import com.yuan.basemodule.net.okhttp.okUtil.OKHttpConfig;
import com.yuan.basemodule.net.okhttp.okUtil.OKHttpUtil;
import com.yuan.demo.myapplication.BuildConfig;

/**
 * Created by YuanYe on 2017/7/19.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        //配置联网
        OKHttpConfig config = OKHttpConfig.create()
                .connectTimeout(7 * 1000l) //连接超时设置
                .readTimeoutMills(7 * 1000l)
                .mContext(this) //一定要配置这个参数
                .isReConnection(false).build();
        OKHttpUtil.init(config);

    }
}

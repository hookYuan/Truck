package com.yuan.basemodule.net.okhttp.okUtil;

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.net.okhttp.okUtil.callback.FileBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBaseBack;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YuanYe on 2017/9/26.
 */

public class Execute {

    protected Request.Builder requestBuilder;
    protected OkHttpClient client;
    protected Context mContext;

    public Execute(Context context, Request.Builder request, OkHttpClient _client) {
        this.requestBuilder = request;
        this.client = _client;
        this.mContext = context;
    }

    /**
     * ****************************callBack请求封装****************************************
     */
    //统一对requestBuild处理，
    private Request getRequestBuild() {
        return requestBuilder.build();
    }


    //正常json返回的时候使用
    public void execute(GsonBaseBack call) {
        if (Kits.Empty.check(call)) throw new NullPointerException("回调：RxCall == null");
        call.setmContext(mContext);
        client.newCall(getRequestBuild())
                .enqueue(call);
    }

    //下载文件时使用
    public void execute(FileBack fileBack) {
        if (Kits.Empty.check(fileBack)) throw new NullPointerException("回调：RxCall == null");
        fileBack.setmContext(mContext);
        client.newCall(getRequestBuild())
                .enqueue(fileBack);
    }
}

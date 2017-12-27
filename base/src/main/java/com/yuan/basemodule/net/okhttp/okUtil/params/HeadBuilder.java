package com.yuan.basemodule.net.okhttp.okUtil.params;

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YuanYe on 2017/9/26.
 * 用于构造OKUtil头部
 */
class HeadBuilder<T extends HeadBuilder> {

    protected Request.Builder requestBuilder;
    protected OkHttpClient client;
    protected Context mContext;

    public HeadBuilder(Context context, Request.Builder request, OkHttpClient _client) {
        this.requestBuilder = request;
        this.client = _client;
        this.mContext = context;
    }

    /**
     * ****************************addHead请求封装****************************************
     */
    public T addHead(Map<String, String> params) {
        if (Kits.Empty.check(params)) throw new NullPointerException("参数：params == null");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        return (T)this;
    }

    public T addHead(String key, String value) {
        if (Kits.Empty.check(key)) throw new NullPointerException("参数：params.key == null");
        requestBuilder.addHeader(key, value);
        return (T)this;
    }

}

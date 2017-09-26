package com.yuan.basemodule.net.okhttp.okUtil;

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YuanYe on 2017/9/26.
 * 用于构造Post所需的所有参数
 */
public class PostBuilder extends HeadBuilder {

    private FormBody.Builder builder;

    public PostBuilder(Context context, Request.Builder request, OkHttpClient _client, FormBody.Builder builder) {
        super(context, request, _client);
        this.builder = builder;
    }

    public PostBuilder post(Map<String, String> params) {
        if (Kits.Empty.check(params)) throw new NullPointerException("参数：params == null");
        //设置参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public PostBuilder post(String key, String value) {
        if (Kits.Empty.check(key)) throw new NullPointerException("参数：params.key == null");
        builder.add(key, value);
        return this;
    }

    public Execute build() {
        requestBuilder.post(builder.build());
        return new Execute(mContext, requestBuilder, client);
    }

}

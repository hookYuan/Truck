package com.yuan.basemodule.net.okhttp.okUtil.params;

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.net.okhttp.okUtil.Execute;
import com.yuan.basemodule.net.okhttp.okUtil.callback.BaseJsonBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.FileBack;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YuanYe on 2017/9/26.
 * 用于构造Post所需的所有参数
 */
public class PostBuilder extends HeadBuilder<PostBuilder> {

    private FormBody.Builder builder;

    public PostBuilder(Context context, Request.Builder request, OkHttpClient _client, FormBody.Builder builder) {
        super(context, request, _client);
        this.builder = builder;
    }

    /**
     * 添加参数
     *
     * @param params
     * @return
     */
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

    /**
     * ************************************执行部分****************************************
     *
     * @param fileBack
     */
    public void execute(FileBack fileBack) {
        requestBuilder.post(builder.build());
        new Execute(mContext, requestBuilder, client).execute(fileBack);
    }

    public void execute(BaseJsonBack jsonBack) {
        requestBuilder.post(builder.build());
        new Execute(mContext, requestBuilder, client).execute(jsonBack);
    }

}

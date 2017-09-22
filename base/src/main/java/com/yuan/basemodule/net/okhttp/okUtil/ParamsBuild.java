package com.yuan.basemodule.net.okhttp.okUtil;

/**
 * Created by YuanYe on 2017/9/8.
 */

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.net.okhttp.okUtil.callback.FileBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBack;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 用于构建get(),post()等参数方法
 */
public class ParamsBuild {

    private Request.Builder requestBuilder;
    private OkHttpClient client;
    private Context mContext;

    public ParamsBuild(Context context, Request.Builder request, OkHttpClient _client) {
        this.requestBuilder = request;
        this.client = _client;
        this.mContext = context;
    }

    /**
     * ****************************post请求封装****************************************
     */
    public PostBuild post() {
        return new PostBuild(requestBuilder, this);
    }

    /**
     * ****************************get请求封装****************************************
     */
    public ParamsBuild get() {
        requestBuilder.get();
        return this;
    }

    /**
     * ****************************下载文件请求封装****************************************
     */
    public ParamsBuild download() {
        requestBuilder.get();
        return this;
    }

    /**
     * ****************************Json上传****************************************
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ParamsBuild putJson(String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        requestBuilder.post(requestBody);
        return this;
    }


    /**
     * @param filePath 文件保存路径
     */
    public ParamsBuild download(String filePath) {
        requestBuilder.get();
        return this;
    }

    /**
     * ****************************addHead请求封装****************************************
     */
    public ParamsBuild addHead(Map<String, String> params) {
        if (Kits.Empty.check(params)) throw new NullPointerException("参数：params == null");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public ParamsBuild addHead(String key, String value) {
        if (Kits.Empty.check(key)) throw new NullPointerException("参数：params.key == null");
        requestBuilder.addHeader(key, value);
        return this;
    }

    /**
     * ****************************callBack请求封装****************************************
     */

    //正常json返回的时候使用
    public void execute(GsonBack call) {
        if (Kits.Empty.check(call)) throw new NullPointerException("回调：RxCall == null");
        call.setmContext(mContext);
        client.newCall(requestBuilder.build())
                .enqueue(call);
    }

    //下载文件时使用
    public void execute(FileBack fileBack) {
        if (Kits.Empty.check(fileBack)) throw new NullPointerException("回调：RxCall == null");
        fileBack.setmContext(mContext);
        client.newCall(requestBuilder.build())
                .enqueue(fileBack);
    }
}
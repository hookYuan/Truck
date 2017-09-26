package com.yuan.basemodule.net.okhttp.okUtil;

/**
 * Created by YuanYe on 2017/9/8.
 */

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;

import java.io.File;
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
        builder = new FormBody.Builder();
    }

    /**
     * ****************************post请求封装****************************************
     */
    private FormBody.Builder builder;

    public PostBuilder post(Map<String, String> params) {
        if (Kits.Empty.check(params)) throw new NullPointerException("参数：params == null");
        //设置参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return new PostBuilder(mContext, requestBuilder, client, builder);
    }

    public PostBuilder post(String key, String value) {
        if (Kits.Empty.check(key)) throw new NullPointerException("参数：params.key == null");
        builder.add(key, value);
        return new PostBuilder(mContext, requestBuilder, client, builder);
    }

    /**
     * ****************************get请求封装****************************************
     */
    public Execute get() {
        requestBuilder.get();
        return new Execute(mContext, requestBuilder, client);
    }

    /**
     * ****************************上传文件请求封装****************************************
     */
    public UploadFileBuilder uploadFile(String key, String fileUrl) {
        File file = new File(fileUrl);
        if (file == null) {
            new Throwable("上传文件不存在。。。");
        }
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //上传文件
        RequestBody fileBody = RequestBody.create(MediaType.parse(UploadFileBuilder.getMimeType(fileUrl)), file);
        multipartBody.addFormDataPart(key, file.getName(), fileBody);
        return new UploadFileBuilder(mContext, requestBuilder, client,multipartBody);
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

}
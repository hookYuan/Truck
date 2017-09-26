package com.yuan.basemodule.net.okhttp.okUtil;

import android.content.Context;

import com.yuan.basemodule.common.kit.Kits;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by YuanYe on 2017/9/19.
 * 上传文件+携带其他参数
 */
public class UploadFileBuilder extends HeadBuilder<UploadFileBuilder> {

    private MultipartBody.Builder multipartBody;

    public UploadFileBuilder(Context context, Request.Builder request, OkHttpClient _client, MultipartBody.Builder multipartBody) {
        super(context, request, _client);
        if (multipartBody == null) {
            this.multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        } else {
            this.multipartBody = multipartBody;
        }
    }

    public UploadFileBuilder put(Map<String, String> params) {
        if (Kits.Empty.check(params)) throw new NullPointerException("参数：params == null");
        //设置参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            multipartBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                    RequestBody.create(null, entry.getValue()));
        }
        return this;
    }

    public UploadFileBuilder put(String key, String value) {
        if (Kits.Empty.check(key)) throw new NullPointerException("参数：params.key == null");
        multipartBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                RequestBody.create(null, value));
        return this;
    }

    public UploadFileBuilder putFile(String key, String fileUrl) {
        File file = new File(fileUrl);
        if (file == null) {
            new Throwable("上传文件不存在。。。");
        }
        //上传文件
        RequestBody fileBody = RequestBody.create(MediaType.parse(getMimeType(fileUrl)), file);
        multipartBody.addFormDataPart(key, file.getName(), fileBody);
        return this;
    }

    private static final String IMGUR_CLIENT_ID = "...";

    public Execute build() {
        requestBuilder.post(multipartBody.build());
        requestBuilder.header("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
        return new Execute(mContext, requestBuilder, client);
    }
    /**
     * *******************************Common工具*********************************************
     */

    /**
     * 获取文件MimeType
     *
     * @param filename 文件名
     * @return
     */
    public static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }
}

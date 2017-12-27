package com.yuan.basemodule.net.okhttp.okUtil.params;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YuanYe on 2017/9/26.
 */
public class GetBuilder extends HeadBuilder<GetBuilder> {


    public GetBuilder(Context context, Request.Builder request, OkHttpClient _client) {
        super(context, request, _client);
    }


}

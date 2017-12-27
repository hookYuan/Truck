package com.yuan.basemodule.net.okhttp.okUtil;

import android.content.Context;
import android.text.TextUtils;

import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.net.okhttp.RxHttpClient;
import com.yuan.basemodule.net.okhttp.okUtil.params.ParamsBuild;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YuanYe on 2017/9/8.
 * 基于OKHttp的简单封装。
 * 构建地址，创建Client. 其他参数构建详见MethodBuild
 * 这里的
 */
public class OKHttp {

    private OkHttpClient client;
    private Request.Builder requestBuilder;
    private Context mContext;

    /**
     * 通过创建对象方式启动，可以动态配置每次请求参数
     *
     * @param context
     */
    public OKHttp(Context context) {
        //获取Client
        client = new RxHttpClient(context).getClient();
        requestBuilder = new Request.Builder();
        this.mContext = context;
    }

    /**
     * 对OKHttpUtil进行基本设置
     *
     * @param config
     */
    public OKHttp(Context context, OKHttpConfig config) {
        //获取Client
        client = new RxHttpClient(context, config).getClient();
        requestBuilder = new Request.Builder();
        this.mContext = context;
        if (!TextUtils.isEmpty(config.getCommonHead()) && !TextUtils.isEmpty(config.getCommonHeadKey())) {
            //TODO 公共head,可以统一添加
            requestBuilder.addHeader(config.getCommonHeadKey(), config.getCommonHead());
        }
    }

    public ParamsBuild url(String httpUrl) {
        if (Kits.Empty.check(httpUrl) && Kits.Empty.check(httpUrl)) {
            throw new NullPointerException("地址：url == null");
        }
        requestBuilder.url(httpUrl);
        return new ParamsBuild(mContext, requestBuilder, client);
    }

    public ParamsBuild url(HttpUrl _httpUrl) {
        if (Kits.Empty.check(_httpUrl) && Kits.Empty.check(_httpUrl)) {
            throw new NullPointerException("地址：url == null");
        }
        requestBuilder.url(_httpUrl);
        return new ParamsBuild(mContext, requestBuilder, client);
    }
}

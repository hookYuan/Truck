package com.yuan.basemodule.net.okhttp.retrofit;

import android.content.Context;

import com.yuan.basemodule.net.okhttp.RxHttpClient;
import com.yuan.basemodule.net.okhttp.okUtil.OKHttpConfig;
import com.yuan.basemodule.net.okhttp.retrofit.gsonAdapter.ProtoConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by YuanYe on 2017/9/8.
 * 用于构建Retrofit对象。
 * 本质可以切换：原生Retrofit方式，或者使用
 * OKHttp+Retrofit+Rxjava2+RxAndroid的方式
 */
public class RetrofitUtil {

    private static Retrofit retrofit;
    //统一地址头
    private static final String BASEURL = "http://192.168.0.24:8080/";

    static Context context;

    public RetrofitUtil(Context context) {
        this.context = context;
    }

    public static <T> T create(Class<T> clazz) {
        return create(clazz, OKHttpConfig.create().build());
    }

    public static <T> T create(Class<T> clazz, OKHttpConfig builder) {
        if (retrofit == null) {
            OkHttpClient client = null;
            if (builder != null) {
                client = new RxHttpClient(context, builder).getClient();
            } else {
                client = new RxHttpClient(context).getClient();
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL) //注意，baseUrl必须以'/'结束
                    .client(client)
                    .addConverterFactory(ProtoConverterFactory.create()) //设置数据解析器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(clazz);
    }
}

package com.yuan.basemodule.net.okhttp.okUtil;

import android.content.Context;

import okhttp3.CookieJar;

/**
 * Created by YuanYe on 2017/9/8.
 * 用于配置OkHttpClient，采用build模式进行配置
 * 用于配置单次OkHttp请求参数，在OKHttp创建时初始化使用
 */
public class OKHttpConfig {

    private long connectTimeout; //连接超时时间
    private long readTimeoutMills;//读取超时时间
    private CookieJar cookie;//设置Cookie
    private boolean isReConnection; //是否重新连接
    private String commonHead = "";  //公共头部
    private String commonHeadKey = "";//公共头部key
    private Context mContext;//上下文

    private static Builder builder;

    public static Builder create() {
        if (builder == null) {
            builder = new Builder();
        }
        return builder;
    }

    private OKHttpConfig(Builder builder) {
        connectTimeout = builder.connectTimeout;
        readTimeoutMills = builder.readTimeoutMills;
        cookie = builder.cookie;
        isReConnection = builder.isReConnection;
        commonHead = builder.commonHead;
        commonHeadKey = builder.commonHeadKey;
        mContext = builder.mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeoutMills() {
        return readTimeoutMills;
    }

    public CookieJar getCookie() {
        return cookie;
    }

    public boolean isReConnection() {
        return isReConnection;
    }

    public String getCommonHead() {
        return commonHead;
    }

    public String getCommonHeadKey() {
        return commonHeadKey;
    }

    public static final class Builder {
        private long connectTimeout;
        private long readTimeoutMills;
        private CookieJar cookie;
        private boolean isReConnection;
        private String commonHead;
        private String commonHeadKey;
        private Context mContext;

        public Builder() {
        }

        public Builder connectTimeout(long val) {
            connectTimeout = val;
            return this;
        }

        public Builder readTimeoutMills(long val) {
            readTimeoutMills = val;
            return this;
        }

        public Builder cookie(CookieJar val) {
            cookie = val;
            return this;
        }

        public Builder isReConnection(boolean val) {
            isReConnection = val;
            return this;
        }

        public Builder commonHead(String val) {
            commonHead = val;
            return this;
        }

        public Builder commonHeadKey(String val) {
            commonHeadKey = val;
            return this;
        }

        public Builder mContext(Context val) {
            mContext = val;
            return this;
        }

        public OKHttpConfig build() {
            return new OKHttpConfig(this);
        }
    }
}

package com.yuan.basemodule.net.okhttp.okUtil;

import okhttp3.CookieJar;

/**
 * Created by YuanYe on 2017/9/8.
 * 用于配置OkHttpClient，采用build模式进行配置
 */
public class RxClientBuilder {

    private long connectTimeout; //连接超时时间
    private long readTimeoutMills;//读取超时时间
    private CookieJar cookie;//设置Cookie
    private boolean isReConnection; //是否重新连接
    private boolean isCommonHead = false;  //是否有公共头部

    private RxClientBuilder(Builder builder) {
        connectTimeout = builder.connectTimeout;
        readTimeoutMills = builder.readTimeoutMills;
        cookie = builder.cookie;
        isReConnection = builder.isReConnection;
        isCommonHead = builder.isCommonHead;
    }

    private static Builder builder;

    public static Builder create() {
        if (builder == null) {
            builder = new Builder();
        }
        return builder;
    }

    public static final class Builder {
        private long connectTimeout;
        private long readTimeoutMills;
        private CookieJar cookie;
        private boolean isReConnection;
        private boolean isCommonHead = false;

        private Builder() {

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

        public Builder isCommonHead(boolean val) {
            isCommonHead = val;
            return this;
        }

        public RxClientBuilder build() {
            return new RxClientBuilder(this);
        }
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

    public boolean isCommonHead() {
        return isCommonHead;
    }
}

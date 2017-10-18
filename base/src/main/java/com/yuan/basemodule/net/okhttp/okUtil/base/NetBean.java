package com.yuan.basemodule.net.okhttp.okUtil.base;

/**
 * Created by YuanYe on 2017/10/18.
 * <p>
 * NetBean: 为适应多格式json解析，而优化代码
 * NetBean中的内容为json返回中的公共部分。
 */
public class NetBean<T> {

    protected T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

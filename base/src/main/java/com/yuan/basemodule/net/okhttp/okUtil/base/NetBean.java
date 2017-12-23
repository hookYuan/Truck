package com.yuan.basemodule.net.okhttp.okUtil.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YuanYe on 2017/10/18.
 * <p>
 * NetBean: 为适应多格式json解析，而优化代码
 * NetBean中的内容为json返回中的公共部分。
 */
public class NetBean<T> {


    protected T data;
    /**
     * code : 200
     * message : 这是一条消息
     * data : eb3968cc-0f64-4b58-9406-a7e51a5bbcad
     */

    private int code;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

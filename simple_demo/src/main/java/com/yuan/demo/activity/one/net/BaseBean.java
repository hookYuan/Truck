package com.yuan.demo.activity.one.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YuanYe on 2017/10/18.
 */

public class BaseBean<T> {
    /**
     * success : true
     * data : 访问失败
     */
    private boolean success;

    private T data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}

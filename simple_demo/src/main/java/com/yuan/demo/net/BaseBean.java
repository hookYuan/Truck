package com.yuan.demo.net;


/**
 * Created by YuanYe on 2017/8/2.
 * 统一服务器返回数据类型
 */

public class BaseBean<T> {
    private int status = 0;//访问接口返回状态  1--访问成功   0--访问失败
    private String message = "";//访问成功或者失败的接口表描述
    private T data = null;//服务器实际返回的数据

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
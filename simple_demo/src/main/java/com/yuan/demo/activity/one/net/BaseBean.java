package com.yuan.demo.activity.one.net;

import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;

/**
 * Created by YuanYe on 2017/10/18.
 */

public class BaseBean<T> extends NetBean<T> {


    /**
     * type : 2
     * status : ??
     */

    private String type;
    private String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

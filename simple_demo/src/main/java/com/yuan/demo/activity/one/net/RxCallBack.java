package com.yuan.demo.activity.one.net;

import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBack;

/**
 * Created by YuanYe on 2017/10/18.
 */

public abstract class RxCallBack<T> extends GsonBack<T> {

    @Override
    protected NetBean setUseNetBean() {
        return new BaseBean<T>();
    }
}

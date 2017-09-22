package com.yuan.basemodule.router;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yuan.basemodule.router.Interrupt.InterruptCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by YuanYe on 2017/7/19.
 * 支持跨module启动Activity
 * 默认界面跳转和传递参数的方式，建议所有界面跳转使用该方式跳转
 */

public class RouterHelper {

    private static Activity from; //启动的Activity
    private static RouterHelper router;

    private static HashMap<String, Object> puts;

    private RouterHelper() {
        if (puts == null)
            puts = new HashMap<>();
    }


    public static RouterHelper from(Activity context) {
        if (from != null && from == context) {
            return router;
        }
        router = new RouterHelper();
        from = context;
        if (puts.size()!=0)
            puts.clear();
        return router;
    }

    /**
     * @param url 目标文件路径(跨Moudle启动)
     * @return
     */
    public void to(String url, InterruptCallback callBack) {
        reallyStart(url, 0,callBack);
    }

    public void to(String url, int code) {
        reallyStart(url, code,null);
    }
    public void to(String url, int code,InterruptCallback callBack) {
        reallyStart(url, code,callBack);
    }


    public void to(String url) {
        to(url, null);
    }

    /**
     * 支持类型：String
     *
     * @param key
     * @param str
     */
    public RouterHelper put(String key, Object str) {
        puts.put(key, str);
        return router;
    }

    public RouterHelper put(Bundle bundle) {
        put("bundle", bundle);
        return router;
    }


    /**
     * 调用路由真实启动的过程
     * 类型如果有问题，
     */
    private void reallyStart(String url, int requestCode,InterruptCallback callBack) {
        Postcard postcard = ARouter.getInstance().build(url);
        Iterator iter = puts.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val instanceof String) {
                postcard.withString(key, (String) val);
            } else if (val instanceof Integer) {
                postcard.withInt(key, (int) val);
            } else if (val instanceof Bundle) {
                postcard.with((Bundle) val);
            } else if (val instanceof Boolean) {
                postcard.withBoolean(key, (Boolean) val);
            } else if (val instanceof Byte) {
                postcard.withByte(key, (Byte) val);
            } else if (val instanceof byte[]) {
                postcard.withByteArray(key, (byte[]) val);
            } else if (val instanceof Double) {
                postcard.withDouble(key, (Double) val);
            } else if (val instanceof Float) {
                postcard.withFloat(key, (Float) val);
            } else if (val instanceof ArrayList) {
                postcard.withStringArrayList(key, (ArrayList) val);
            } else if (val instanceof SparseArray) {
                postcard.withSparseParcelableArray(key, (SparseArray<? extends Parcelable>) val);
            } else if (val instanceof Parcelable) {
                postcard.withParcelable(key, (Parcelable) val);
            } else if (val instanceof Character) {
                postcard.withChar(key, (char) val);
            } else {
                Log.e("Router", "参数无法转化，请添加新的类型");
            }
        }
        if (requestCode==0){
            if (callBack != null)
                postcard.navigation(from,callBack);
            else
                postcard.navigation(from);
        }else {
            if (callBack != null)
                postcard.navigation(from,requestCode,callBack);
            else
                postcard.navigation(from,requestCode);
        }
    }

}

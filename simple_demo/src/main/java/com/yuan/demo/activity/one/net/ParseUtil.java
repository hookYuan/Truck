package com.yuan.demo.activity.one.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.net.okhttp.okUtil.base.GsonType;
import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by YuanYe on 2017/12/23.
 */

public class ParseUtil<T> {
    T obj;

    public ParseUtil(String json1) {
        jsonParse(json1);
    }

    public NetBean jsonParse(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray array = null;
        obj = TUtil.getT(ParseUtil.this, 0);
        Class<T> clazz = (Class<T>) obj.getClass();
        try {
            array = jsonObject.getAsJsonArray("data");
        } catch (ClassCastException cce) {
            if ("com.google.gson.JsonPrimitive cannot be cast to com.google.gson.JsonArray"
                    .equals(cce.getMessage())) { //按照String解析
                NetBean bean = new Gson().fromJson(json, new TypeToken<NetBean<String>>() {
                }.getType());
                return bean;
            } else if ("com.google.gson.JsonObject cannot be cast to com.google.gson.JsonArray"
                    .equals(cce.getMessage())) {
                NetBean bean = GsonType.fromJson(json, clazz);
                return bean;
            } else if ("com.google.gson.JsonNull cannot be cast to com.google.gson.JsonArray"
                    .equals(cce.getMessage())) { //json中data数据为null
                NetBean bean = new Gson().fromJson(json, new TypeToken<NetBean<String>>() {
                }.getType());
                return bean;
            } else {
                throw cce;
            }
        }
        if (array != null) { //按照List解析
            NetBean<List<T>> bean = null;
            try {
                bean = GsonType.fromJsonArray(json, clazz);
            } catch (Exception e) {
                Log.i("yuanye", "json解析异常" + e.getMessage());
                //继续解析
                if (bean == null) {
                    NetBean bean2 = new Gson().fromJson(json, NetBean.class);
                    return bean2;
                }
            }
            return bean;
        } else {
            throw new NullPointerException("data - 数组为空");
        }
    }


    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}

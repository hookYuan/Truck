package com.yuan.basemodule.net.okhttp.okUtil.base;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by YuanYe on 2017/10/18.
 * Gson解析的Bean必须继承NetBean,以适配泛型的解析
 */
public class GsonType {

    public static <T> T jsonToBean(String jsonResult, Class<T> clz) {
        Gson gson = new Gson();
        T t = gson.fromJson(jsonResult, clz);
        return t;
    }

    public static NetBean fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(NetBean.class, clazz);
        return gson.fromJson(json, objectType);
    }

    public static <T> NetBean<List<T>> fromJsonArray(String json, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = type(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = type(NetBean.class, new Type[]{listType});
        return new Gson().fromJson(json, type);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}

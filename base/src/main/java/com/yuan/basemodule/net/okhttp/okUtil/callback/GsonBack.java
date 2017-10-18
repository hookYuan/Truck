package com.yuan.basemodule.net.okhttp.okUtil.callback;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.net.okhttp.okUtil.base.GsonType;
import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;

import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.Call;

/**
 * Created by YuanYe on 2017/10/18.
 * <p>
 * 对GsonBaseBack的扩展
 * ***注意：这里的泛型T不能包含泛型，如果包含，需要自定义解析
 * 支持自动解析
 * 支持的json说明：
 * 泛型T说明：
 * 1、当setUseNetBean（）为空时，T代表完整Json的实体对象
 * 2、当setUseNetBean（）不为空时，并且Json样式为：{"success":true,"data":{"msd":"访问失败"}}
 * 自动切换data类型为 T
 * 3、当setUseNetBean（）不为空时，并且Json样式为：{"success":true,"data":[{"starLevel":4},{"starLevel":4}]}
 * 自动切换data类型为 List<T>
 * 4、当setUseNetBean（）不为空时，并且Json样式为：{"success":true,"data":"访问失败"}
 * 自动切换data类型为 String
 */
public abstract class GsonBack<T> extends GsonBaseBack<T> {
    /**
     * 自定义解析json
     *
     * @return 传入泛型
     */
    protected Object parseJson(String json) {
//        if (setUseNetBean() == null) {
//            return super.parseJson(json);
//        } else {
//            //根据NetBean解析Json
//            return parsetyeJson(json);
//        }
        return null;
    }

    public NetBean<T> parsetyeJson(String json) {

        return null;
    }

    public <T> NetBean jsonParse(String json, Class<T> clazz) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray array = null;
        try {
            array = jsonObject.getAsJsonArray("data");
        } catch (ClassCastException cce) {
            if ("com.google.gson.JsonPrimitive cannot be cast to com.google.gson.JsonArray"
                    .equals(cce.getMessage())) { //按照String解析
                NetBean<String> bean = GsonType.fromJson(json, String.class);
                return bean;
            } else if ("com.google.gson.JsonObject cannot be cast to com.google.gson.JsonArray"
                    .equals(cce.getMessage())) {
                NetBean<T> bean = GsonType.fromJson(json, clazz);
                return bean;
            } else {
                throw cce;
            }
        }
        if (array != null && array.size() > 0) { //按照List解析
            NetBean<List<T>> bean = GsonType.fromJsonArray(json, clazz);
            return bean;
        }
        return null;
    }

    @Override
    protected void parseJsonAfter(Call call, @NonNull Object response) {
        //返回主线程
        try {
            if (response instanceof String) {
                onSuccess(call, (String) response);
            } else if (response instanceof NetBean) {
                if (((NetBean) response).getData() instanceof List) {
                    onSuccess(call, (List<T>) ((NetBean) response).getData());
                } else if (((NetBean) response).getData() instanceof String) {
                    onSuccess(call, (String) ((NetBean) response).getData());
                } else {
                    onSuccess(call, (T) ((NetBean) response).getData());
                }
            } else {
                onSuccess(call, (T) response);
            }
        } catch (Exception ex) {
            onFailure(ex); //异常统一处理
        }
    }


    public void onSuccess(Call call, List<T> list) {

    }
}

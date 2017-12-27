package com.yuan.basemodule.net.okhttp.okUtil.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yuan.basemodule.common.log.ToastUtil;
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
public abstract class GsonBack<T> extends BaseJsonBack<T> {
    /**
     * 自定义解析json
     *
     * @return 传入泛型
     */
    protected Object parseJson(String json) {
        Log.i("json", "----->" + json);
        //根据NetBean解析Json
        return jsonParse(json);
    }

    public NetBean jsonParse(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray array = null;
        Class<T> clazz = (Class<T>) t.getClass();
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

    @Override
    protected void parseJsonAfter(Call call, @NonNull Object response) {
        //返回主线程
        try {
            if (response instanceof String) {
                onSuccess(call, (String) response);
            } else if (response instanceof NetBean) {
                if (((NetBean) response).getData() instanceof List) {
                    if (((NetBean) response).getCode() == 200) {
                        onSuccess(call, (List<T>) ((NetBean) response).getData());
                    } else {
                        onFailure(new Exception("错误码：" + ((NetBean) response).getCode() + ((NetBean) response).getMessage()));
                    }
                } else if (((NetBean) response).getData() instanceof String) {
                    if (((NetBean) response).getCode() == 200) {
                        onSuccess(call, (String) ((NetBean) response).getData());
                    } else {
                        onFailure(new Exception("错误码：" + ((NetBean) response).getCode() + ((NetBean) response).getMessage()));
                    }
                } else {
                    if (((NetBean) response).getCode() == 200) {
                        onSuccess(call, (T) ((NetBean) response).getData());
                    } else {
                        onFailure(new Exception("错误码：" + ((NetBean) response).getCode() + ((NetBean) response).getMessage()));
                    }
                }
            } else {
                onSuccess(call, (T) response);
            }
        } catch (Exception ex) {
            onFailure(ex); //异常统一处理
        }
    }

    @Override
    public void onFailure(Exception e) {
        ToastUtil.showShort(mContext, e.getMessage());
    }

    public void onSuccess(Call call, List<T> list) {

    }
}

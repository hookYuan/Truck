package com.yuan.basemodule.net.okhttp.okUtil.callback;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.net.okhttp.okUtil.base.GsonType;
import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;
import com.yuan.basemodule.ui.base.activity.BaseActivity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by YuanYe on 2017/8/4.
 * RxCallBack---用于处理OKHttpUtil返回
 * Gson处理返回--使用RxJava切换处理方法到主线程
 * 类只支持json解析
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
public abstract class GsonBack<T> implements Callback {

    protected Context mContext; //在ParamsBuilder中传递过来，不为空

    public Context getmContext() {
        if (mContext == null) {
            onFailure(new Exception("Context为空,请检查ParamsBuild中Context是否为空。"));
        }
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 子线程失败方法
     *
     * @param call
     */
    @Override
    public void onFailure(Call call, final IOException ioE) {
        Observable.create(new ObservableOnSubscribe<IOException>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<IOException> e) throws Exception {
                e.onNext(ioE);
            }
        }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                .subscribe(new Consumer<IOException>() {
                    @Override
                    public void accept(@NonNull IOException response) throws Exception {
                        onFailure(response);
                    }
                });
    }

    /**
     * 子线程成功方法
     *
     * @param call
     * @param response
     * @throws IOException
     */
    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                /**
                 * TODO 把返回结果当做标准Json统一处理,如果返回结果不是标准json,
                 * TODO 请自行重写该方法。(response.body()只能调用一次)
                 */
                if (TUtil.getT(GsonBack.this, 0) == null) {
                    //不存在泛型的情况,直接返回json
                    e.onNext(response.body().string());
                } else {
                    String json = response.body().string();
                    Object entity = parsJson(parsJsonBefore(json));
                    e.onNext(entity);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object response) {
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

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof Exception) {
                            onFailure((Exception) e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 自定义解析json
     *
     * @return 传入泛型
     */
    private Object parsJson(String json) {
        if (setUseNetBean() == null) {
            T entity = (T) new Gson().fromJson(json, TUtil.getT(GsonBack.this, 0).getClass());
            return entity;
        } else {
            //根据NetBean解析Json
            return parseJson(json);
        }
    }

    public NetBean<T> parseJson(String json) {

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


    /**
     * 主线程成功方法
     */
    public void onSuccess(Call call, String json) {
    }

    public void onSuccess(Call call, T obj) {
    }

    public void onSuccess(Call call, List<T> list) {
    }

    /**
     * 主线程处理异常方法
     */
    public void onFailure(Exception e) {

    }

    /**
     * 是否设置BaseBean
     *
     * @return
     */
    protected NetBean setUseNetBean() {
        return null;
    }

    /**
     * 解析Json之前调用，根据需求统一处理Json
     * 执行在子线程
     *
     * @return 处理后的Json数据
     */
    protected String parsJsonBefore(String json) {
        return json;
    }

}

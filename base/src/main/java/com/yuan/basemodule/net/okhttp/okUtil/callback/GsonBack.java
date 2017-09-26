package com.yuan.basemodule.net.okhttp.okUtil.callback;

import android.content.Context;

import com.google.gson.Gson;
import com.yuan.basemodule.common.other.TUtil;
import com.yuan.basemodule.ui.base.activity.BaseActivity;

import java.io.IOException;

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
 * T为需要解析的类型，B为基本泛型
 */
public abstract class GsonBack<T> implements Callback {

    //    private Class<T> clazz;
    protected Context mContext; //在ParamsBuilder中传递过来，不为空

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
        Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                /**
                 * TODO 把返回结果当做标准Json统一处理,如果返回结果不是标准json,
                 * TODO 请自行重写该方法。(response.body()只能调用一次)
                 */
                if (TUtil.getT(GsonBack.this, 0) == null) {
                    //不存在泛型的情况,直接返回json
                    e.onNext((T) response.body().string());
                } else {
                    T entity = (T) new Gson().fromJson(response.body().string(), TUtil.getT(GsonBack.this, 0).getClass());
                    e.onNext(entity);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull T response) {
                        //返回主线程
                        try {
                            onSuccess(call, response);
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
     * 主线程成功方法
     */
    public abstract void onSuccess(Call call, T t);

    /**
     * 主线程处理异常方法
     */
    public abstract void onFailure(Exception e);


    public Context getmContext() {
        if (mContext == null) {
            onFailure(new Exception("Context为空,请检查ParamsBuild中Context是否为空。"));
        }
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}

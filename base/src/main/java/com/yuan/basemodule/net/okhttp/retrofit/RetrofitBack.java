package com.yuan.basemodule.net.okhttp.retrofit;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by YuanYe on 2017/8/4.
 * <p>
 * 统一进行错误处理
 */
public abstract class RetrofitBack<T> implements Observer {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(@NonNull Object o) {
        onSuccess((T) o);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e);

}

package com.yuan.basemodule.common.other;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by YuanYe on 2017/9/19.
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, ? extends T> io_main() {    //compose简化线程
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(@NonNull Observable observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
                                     @Override
                                     public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception {
                                         try {
                                             subscriber.onNext(t);
                                             subscriber.onComplete();
                                         } catch (Exception e) {
                                             subscriber.onError(e);
                                         }
                                     }
                                 }
        );
    }
}

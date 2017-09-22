package com.yuan.simple.ui;

import android.os.Bundle;
import android.view.View;

import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.simple.R;
import com.yuan.simple.router.RouterUrl;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 闪屏界面
 */
public class SplashActivity extends ExtraActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Observable.interval(0,1, TimeUnit.SECONDS) //立刻执行，1秒间隔发送
                .compose(this.bindToLifecycle())
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Object aLong) {
                        Long time = (Long) aLong;
                        if (time==3){
                            RouterHelper.from(mContext).to(RouterUrl.loginActivity);
                            disposable.dispose();
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }

}

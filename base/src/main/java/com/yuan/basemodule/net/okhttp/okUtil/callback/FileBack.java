package com.yuan.basemodule.net.okhttp.okUtil.callback;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by YuanYe on 2017/9/13.
 * 文件下载
 */
public abstract class FileBack implements Callback {

    private String saveDir = "/Download"; //下载文件保存目录

    protected Context mContext; //在ParamsBuilder中传递过来，不为空(用于统一处理提示、dialog等)

    public FileBack() {

    }
    /**
     * @param _saveDir 下载文件保存的路径
     */
    public FileBack(String _saveDir) {
        this.saveDir = _saveDir;
    }


    @Override
    public void onFailure(Call call, final IOException ioE) {
        Observable.create(new ObservableOnSubscribe<IOException>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<IOException> e) throws Exception {
                e.onNext(ioE);
            }
        }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                .subscribe(new Consumer<IOException>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull IOException response) throws Exception {
                        onDownloadFailed(response);
                    }
                });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        // 储存下载文件的目录
        final String savePath = isExistDir(saveDir);
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            final String fileName = getNameFromUrl(response.request().url().url().getPath()); //文件名
            File file = new File(savePath, fileName);
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                final int progress = (int) (sum * 1.0f / total * 100);
                //切换到主线程
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(progress);
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Integer response) throws Exception {
                                // 下载中
                                onDownloading(response);
                            }
                        });
            }
            fos.flush();
            //切换到主线程
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Integer> e) throws Exception {
                    e.onNext(0);
                }
            }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Integer response) throws Exception {
                            // 下载完成
                            onDownloadSuccess(savePath + File.separator + fileName);
                        }
                    });

        } catch (final Exception ioE) {
            Observable.create(new ObservableOnSubscribe<Exception>() {
                @Override
                public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Exception> e) throws Exception {
                    e.onNext(ioE);
                }
            }).observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程
                    .subscribe(new Consumer<Exception>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Exception response) throws Exception {
                            onDownloadFailed(response);
                        }
                    });
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 下载成功
     */
    public abstract void onDownloadSuccess(String fileDir);

    /**
     * @param progress 下载进度
     */
    public void onDownloading(int progress) {
    }

    /**
     * 下载失败
     */
    public abstract void onDownloadFailed(Exception e);

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}

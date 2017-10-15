package com.yuan.basemodule.net.Glide;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yuan.basemodule.R;

import java.io.File;

/**
 * Created by YuanYe on 2017/7/14.
 * 建议所有使用图片加载的地方都通过这样的方法来实现图片加载
 */
public class GlideHelper {

    private static Activity mActivity;
    private static GlideHelper helper;
    private Object url;//加载图片的类型
    private static boolean isShowCircle = false;//是否显示圆形图片
    private static int rounRadius = 4; //单位dp
    private static boolean isShowRound = false;//是否显示圆形圆角矩形图片
    private static
    @DrawableRes
    int erroRes; //加载错误时的图片
    private static
    @DrawableRes
    int loadingRes;//加载前的图片


    private GlideHelper() {
    }

    public static GlideHelper with(Activity activity) {
        if (helper == null) {
            helper = new GlideHelper();
        } else {
            //初始化数据
            isShowCircle = false;
            isShowRound = false;
            rounRadius = 4;
            loadingRes = R.drawable.ic_default_img;
            erroRes = R.drawable.ic_default_error;
            //

        }
        mActivity = activity;
        return helper;
    }

    public GlideHelper load(String url) {
        this.url = url;
        return helper;
    }
    public GlideHelper load(@DrawableRes int url) {
        this.url = url;
        return helper;
    }

    /**
     * ***************************Glide设置圆角矩形和圆形图片*************************************
     */
    public GlideHelper circle() {
        this.isShowCircle = true;
        return helper;
    }

    public GlideHelper round() {
        isShowRound = true;
        return helper;
    }

    public GlideHelper round(int rounRadius) {
        isShowRound = true;
        this.rounRadius = rounRadius;
        return helper;
    }

    /**
     * *****************************Glide设置默认加载图片*************************************************
     */
    public GlideHelper erro(@DrawableRes int erro) {
        erroRes = erro;
        return helper;
    }

    public GlideHelper loading(@DrawableRes int loading) {
        loadingRes = loading;
        return helper;
    }


    public void into(ImageView imageView) {
        realLoadImageView(imageView);
    }

    /**
     * 真实加载Glide
     *
     * @param imageView
     */
    private void realLoadImageView(ImageView imageView) {
        DrawableRequestBuilder builder = Glide.with(mActivity)
                .load(url)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                //加载前的图片
                .placeholder(loadingRes)
                //加载错误的图片
                .error(erroRes)
                .crossFade(300);
        if (isShowCircle) {
            builder.transform(new CircleTransform(mActivity));
        } else if (isShowRound) {
            builder.transform(new CenterCrop(mActivity), new GlideRoundTransform(mActivity, rounRadius));
        }
        builder.into(imageView);
    }
}

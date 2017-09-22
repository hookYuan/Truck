package com.yuan.basemodule.net.Glide;

import android.app.Activity;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.yuan.basemodule.R;
import com.yuan.basemodule.common.view.CircleTransform;

/**
 * Created by YuanYe on 2017/7/14.
 * 建议所有使用图片加载的地方都通过这样的方法来实现图片加载
 */
public class GlideHelper {

    private static Activity mActivity;
    private static GlideHelper helper;
    private Object url;//加载图片的类型
    private boolean isShowCircle = false;//是否显示圆形图片

    private GlideHelper() {
    }

    public static GlideHelper with(Activity activity) {
        if (helper == null) {
            helper = new GlideHelper();
        }else{
            //初始化数据

        }
        mActivity = activity;
        return helper;
    }

    public GlideHelper load(String url) {
        this.url = url;
        return helper;
    }

    public GlideHelper circle(boolean isShowCircle) {
        this.isShowCircle = isShowCircle;
        return helper;
    }

    public void into(ImageView imageView) {
        realLoadImageView(imageView);
    }

    /**
     * 真实加载Glide
     * @param imageView
     */
    private void realLoadImageView(ImageView imageView) {
        DrawableRequestBuilder builder = Glide.with(mActivity)
                .load(url)
                //加载前的图片
                .placeholder(R.drawable.ic_default_img)
                //加载错误的图片
                .error(R.drawable.ic_default_error)
                .crossFade(300);
        if (isShowCircle) {
            builder.transform(new CircleTransform(mActivity));
        }
        builder.into(imageView);
    }
}

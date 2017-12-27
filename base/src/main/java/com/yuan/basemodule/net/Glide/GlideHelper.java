package com.yuan.basemodule.net.Glide;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.yuan.basemodule.R;
import com.yuan.basemodule.common.kit.Kits;

public class GlideHelper {

    private static final ViewPropertyAnimation.Animator ANIMATOR =
            new ViewPropertyAnimation.Animator() {
                @Override
                public void animate(View view) {
                    view.setAlpha(0f);
                    view.animate().alpha(1f);
                }
            };

    private static
    @DrawableRes
    int placeholder = R.drawable.ic_default_empty; //加载前的占位符

    private GlideHelper() {

    }

    /**
     * 加载本地 资源文件图片
     *
     * @param drawableId
     * @param image
     */
    public static void load(@DrawableRes int drawableId, @NonNull ImageView image) {
        DisplayMetrics metrics = image.getResources().getDisplayMetrics();
        final int displayWidth = metrics.widthPixels;
        final int displayHeight = metrics.heightPixels;
        Glide.with(image.getContext())
                .load(drawableId)
                .asBitmap()
                .animate(ANIMATOR)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(image) {
                    @Override
                    public void getSize(final SizeReadyCallback cb) {
                        // We don't want to load very big images on devices with small screens.
                        // This will help Glide correctly choose images scale when reading them.
                        super.getSize(new SizeReadyCallback() {
                            @Override
                            public void onSizeReady(int width, int height) {
                                cb.onSizeReady(displayWidth / 2, displayHeight / 2);
                            }
                        });
                    }
                });
    }

    /**
     * 加载圆形图片
     *
     * @param path
     * @param image
     */
    public static void loadCircle(@Nullable String path, @NonNull final ImageView image) {
        final String imgPath = path;
        Glide.with(image.getContext())
                .load(imgPath == null ? null : imgPath)
                .crossFade(0)
                .placeholder(placeholder)
                .dontAnimate()
                .transform(new CircleTransform(image.getContext()))
                .thumbnail(Glide.with(image.getContext())  //缩略图
                        .load(imgPath == null ? null : imgPath)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE))
                .into(image);
    }

    /**
     * 加载圆角矩形
     * 默认圆角弧度为4dp
     */
    public static void loadRound(@Nullable String path, @NonNull final ImageView image) {
        loadRound(path, image, (int) Kits.Dimens.dpToPx(image.getContext(), 4f));
    }

    /**
     * 加载圆角矩形
     */
    public static void loadRound(@Nullable String path, @NonNull final ImageView image, int radus) {
        final String imgPath = path;
        Glide.with(image.getContext())
                .load(imgPath == null ? null : imgPath)
                .crossFade(0)
                .placeholder(placeholder)
                .dontAnimate()
                .transform(new GlideRoundTransform(image.getContext(), radus))
                .thumbnail(Glide.with(image.getContext())  //缩略图
                        .load(imgPath == null ? null : imgPath)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE))
                .into(image);
    }


    public static void load(@Nullable String path, @NonNull final ImageView image
            ,@NonNull final @DrawableRes
            int placehol){
        Glide.with(image.getContext())
                .load(path == null ? null : path)
                .crossFade(0)
                .placeholder(placehol)
                .dontAnimate()
                .thumbnail(Glide.with(image.getContext())
                        .load(path == null ? null : path)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE))
                .into(image);
    }

    /**
     * 根据路径或者网络路径加载图片
     *
     * @param path
     * @param image
     */
    public static void load(@Nullable String path, @NonNull final ImageView image) {
        load(path,image,placeholder);
    }


    /**
     * 加载原图
     *
     * @param path
     * @param image
     */
    public static void loadFull(@NonNull String path, @NonNull final ImageView image) {
        loadFull(path, image, new ImageLoadingListener() {
            @Override
            public void onLoaded() {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * 加载原图
     *
     * @param path
     * @param image
     * @param listener
     */
    public static void loadFull(@NonNull String path, @NonNull final ImageView image,
                                @Nullable final ImageLoadingListener listener) {
        final String urlImg = path;
        Glide.with(image.getContext())
                .load(urlImg)
                .asBitmap()
                .dontAnimate()
                .thumbnail(Glide.with(image.getContext())
                        .load(urlImg)
                        .asBitmap()
                        .animate(ANIMATOR)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE))
                .listener(new GlideDrawableListener() {
                    @Override
                    public void onSuccess(String url) {
                        if (url.equals(urlImg)) {
                            if (listener != null) {
                                listener.onLoaded();
                            }
                        }
                    }

                    @Override
                    public void onFail(String url) {
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                })
                .into(new GlideImageTarget(image));
    }


    public interface ImageLoadingListener {
        void onLoaded();

        void onFailed();
    }

}

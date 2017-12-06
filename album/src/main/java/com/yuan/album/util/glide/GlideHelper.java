package com.yuan.album.util.glide;

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
import com.googlecode.flickrjandroid.photos.Photo;
import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;

public class GlideHelper {

    private static final ViewPropertyAnimation.Animator ANIMATOR =
            new ViewPropertyAnimation.Animator() {
                @Override
                public void animate(View view) {
                    view.setAlpha(0f);
                    view.animate().alpha(1f);
                }
            };

    private GlideHelper() {
    }

    public static void loadResource(@DrawableRes int drawableId, @NonNull ImageView image) {
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

    public static void loadFlickrThumb(@Nullable PhotoBean photo, @NonNull final ImageView image) {
        Glide.with(image.getContext())
                .load(photo == null ? null : photo.getImgPath())
                .crossFade(0)
                .placeholder(R.mipmap.album_bg)
                .dontAnimate()
                .thumbnail(Glide.with(image.getContext())
                        .load(photo == null ? null : photo.getImgPath())
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE))
                .into(image);
    }

    public static void loadFlickrFull(@NonNull PhotoBean photo, @NonNull final ImageView image) {
        loadFlickrFull(photo, image, new ImageLoadingListener() {
            @Override
            public void onLoaded() {

            }

            @Override
            public void onFailed() {

            }
        });
    }

    public static void loadFlickrFull(@NonNull PhotoBean photo, @NonNull final ImageView image,
                                      @Nullable final ImageLoadingListener listener) {

        final String urlImg = photo.getImgPath();
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

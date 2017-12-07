package com.yuan.album.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

/**
 * 图片剪裁Activity
 */
public class ImageCropActivity extends MVPActivity {

    private boolean isCrop;                 //是否使用剪裁
    private int cropW;                      //剪裁宽度
    private int cropH;                      //剪裁宽度
    private String imagePath = "";          //图片地址

    private GestureImageView gestureImageView;

    @Override
    protected void initData(Bundle savedInstanceState) {
        isCrop = getIntent().getBooleanExtra(Config.CROP, false);
        cropW = getIntent().getIntExtra(Config.CROPW, 200);
        cropH = getIntent().getIntExtra(Config.CROPH, 200);
        imagePath = getIntent().getStringExtra(Config.CROPPATH);

        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "")
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setTitleBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimary))
                .setStatusBarColor(ContextCompat.getColor(mContext, R.color.album_colorPrimaryDark))
                .setRightAsButton(R.drawable.selector_base_circular)
                .setRightText("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gestureImageView.getController().getSettings()
                                .setFitMethod(Settings.Fit.OUTSIDE)
                                .setFillViewport(true)
                                .setMovementArea(cropW, cropH)
                                .setRotationEnabled(true);
                        Bitmap cropped = gestureImageView.crop();
                        ToastUtil.showShort(mContext, "剪裁成功--" + cropped.getByteCount());
                    }
                });

        gestureImageView = (GestureImageView) findViewById(R.id.giv_crop_image);

        GlideHelper.with(mContext).load(imagePath).into(gestureImageView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_image_crop;
    }
}

package com.yuan.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.State;
import com.alexvasilkov.gestures.commons.FinderView;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.album.util.glide.GlideHelper;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

/**
 * 图片剪裁Activity
 */
public class ImageCropActivity extends MVPActivity {

    private boolean isCrop;                 //是否使用剪裁
    private int cropW;                      //剪裁宽度
    private int cropH;                      //剪裁宽度
    private String imagePath = "";          //图片地址
    private int cropShape;                  //剪切框的形状，默认FinderShape.RECT

    private GestureImageView gestureImageView;
    private FinderView finderView;

    private FinderShape finderShape = FinderShape.SQUARE;

    @Override
    protected void initData(Bundle savedInstanceState) {
        isCrop = getIntent().getBooleanExtra(Config.CROP, false);
        cropW = getIntent().getIntExtra(Config.CROPW, 200);
        cropH = getIntent().getIntExtra(Config.CROPH, 200);
        cropShape = getIntent().getIntExtra(Config.CROPSHAP, 0);
        imagePath = getIntent().getStringExtra(Config.CROPPATH);

        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "")
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setRightText("选取", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Cropping image within selected area
                        Bitmap cropped = gestureImageView.crop();
                        if (cropped != null) {
                            ImageCropResultActivity.open(mContext, cropped, Config.IMAGECROPPREVIEWREQUEST);
                        }
                    }
                });
        initView();
    }

    private void initView() {
        //Initializing gestureImageView
        gestureImageView = Views.find(mContext, R.id.giv_crop_image);
        gestureImageView.getController().getSettings()
                .setMaxZoom(3f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(true)
                .setMovementArea(cropW, cropH)
                .setRestrictRotation(true)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.OUTSIDE)
                .setGravity(Gravity.CENTER);

        //Initializing finderView
        finderView = Views.find(this, R.id.cropping_finder);
        finderView.setSettings(gestureImageView.getController().getSettings());

        GlideHelper.loadFlickrThumb(imagePath, gestureImageView);

        applyFinderShape(false);
    }


    /**
     * 配置剪切框的形状
     *
     * @param animate 是否显示头像剪切框切换动画
     */
    private void applyFinderShape(boolean animate) {
        switch (cropShape) {
            case 0:
                finderShape = FinderShape.SQUARE;
                break;
            case 1:
                finderShape = FinderShape.CIRCLE;
                break;
            case 2:
                finderShape = FinderShape.RECT;
                break;
        }

        finderView.setRounded(finderShape == FinderShape.CIRCLE);

        // Computing cropping area size
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int finderWidth = Math.min(metrics.widthPixels, metrics.heightPixels) * 3 / 4;
        int finderHeight = finderShape == FinderShape.RECT ? finderWidth * 9 / 16 : finderWidth;

        GestureController controller = gestureImageView.getController();

        // Setting cropping area
        controller.getSettings().setMovementArea(finderWidth, finderHeight);

        if (animate) {
            // Animating to zoomed out state, keeping image in bounds
            int pivotX = controller.getSettings().getViewportW() / 2;
            int pivotY = controller.getSettings().getViewportH() / 2;
            State end = controller.getState().copy();
            end.zoomTo(0.001f, pivotX, pivotY); // Zooming out to initial state
            controller.setPivot(pivotX, pivotY);
            controller.animateStateTo(end);
        } else {
            controller.updateState();
        }

        // Updating cropping area changes
        finderView.update(animate);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Config.IMAGECROPPREVIEWREQUEST) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_album_image_crop;
    }

    private enum FinderShape {
        RECT, CIRCLE, SQUARE
    }
}

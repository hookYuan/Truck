package com.yuan.album.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.yuan.album.Config;
import com.yuan.album.R;
import com.yuan.album.helper.ActivityManagerHelpder;
import com.yuan.album.helper.PhotoPreviewHelper;
import com.yuan.album.util.FileUtils;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.title.ETitleTheme;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 图片剪裁完成后查看界面
 * 不支持外部调用
 */
public class ImageCropResultActivity extends MVPActivity implements ISwipeBack {


    private static Bitmap bitmapToShow; // Bad, but works fine for demonstration purpose

    public static void open(Activity context, Bitmap bitmap, int requestCode) {
        bitmapToShow = bitmap;
        context.startActivityForResult(new Intent(context, ImageCropResultActivity.class), requestCode);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getTitleBar().setToolbar(R.drawable.ic_base_back_white, "")
                .setFontColor(ContextCompat.getColor(mContext, R.color.white))
                .setRightText("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Save bitmap as file
                        String path = FileUtils.getAppDirName() + File.separator + Config.CROP_DIR_NAME +
                                File.separator + System.currentTimeMillis() + ".jpg";
                        boolean success = Kits.Files.writeFile(path, bitmapToShow);
                        //Finish all activity and set result
                        if (success) {
                            ArrayList<String> resultList = new ArrayList();
                            resultList.add(path);
                            Intent intent = getIntent();
                            intent.putStringArrayListExtra(Config.KEYRESULT, resultList);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                });
        GestureImageView cropedView = Views.find(mContext, R.id.album_cropped_image);
        cropedView.setImageBitmap(bitmapToShow);

    }


    @Override
    protected ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_album_image_crop_result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

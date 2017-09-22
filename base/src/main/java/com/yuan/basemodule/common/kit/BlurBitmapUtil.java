package com.yuan.basemodule.common.kit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

import com.yuan.basemodule.common.log.XLog;

/**
 * @author Qiushui
 * @description 模糊图片工具类
 * @revision Xiarui 16.09.05
 */
public class BlurBitmapUtil {
    //图片缩放比例
    private static final float BITMAP_SCALE = 0.4f;

    /**
     * 对一个View 进行高斯模糊，该方法应该在
     *   ViewTreeObserver vto2 = state2.getViewTreeObserver();
     vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override
    public void onGlobalLayout() {}中调用
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        XLog.e("w---"+view.getWidth()+"--h----"+view.getHeight());
        Bitmap bitmap= Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        if(bitmap != null){
            XLog.e("获取到了bitmap");
            view.setBackground(new BitmapDrawable(view.getContext().getResources(), blurBitmap(view.getContext(),bitmap,12f)));
        }else {
            XLog.e("bitmap 为空");
        }
        return bitmap;
    }

    /**
     * 模糊图片的具体方法
     *  先将图片缩小然后在进行高斯模糊
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
}
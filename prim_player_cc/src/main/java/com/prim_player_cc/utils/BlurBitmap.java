package com.prim_player_cc.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.view.WindowManager;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：8/22 0022
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BlurBitmap {
    /**
     * 图片缩放比例
     */
    private static final float BITMAP_SCALE = 0.4f;
    /**
     * 最大模糊度(在0.0到25.0之间)
     */
    private static final float BLUR_RADIUS = 25f;

    /**
     * 模糊图片的具体方法
     * FIXME 存在内存泄露风险
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    @SuppressLint("NewApi")
    public static Bitmap blur(Context context, Bitmap image) {
        try {
// 计算图片缩小后的长宽
            int width = Math.round(getScreenWidth(context) * BITMAP_SCALE);
            int height = Math.round(getScreenHeight(context) * BITMAP_SCALE);
//// 将缩小后的图片做为预渲染的图片。
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
// 创建一张渲染后的输出图片。
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
// 创建RenderScript内核对象
            RenderScript rs = RenderScript.create(context);
// 创建一个模糊效果的RenderScript的工具对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
// 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
// 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
            Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
// 设置渲染的模糊程度, 25f是最大模糊度
            blurScript.setRadius(BLUR_RADIUS);
// 设置blurScript对象的输入内存
            blurScript.setInput(tmpIn);
// 将输出数据保存到输出内存中
            blurScript.forEach(tmpOut);
// 将数据填充到Allocation中
            tmpOut.copyTo(outputBitmap);

            tmpIn.destroy();
            tmpOut.destroy();
            blurScript.destroy();
            rs.destroy();
            return outputBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        //Create renderscript
        RenderScript rs = RenderScript.create(context);
        //Create allocation from Bitmap
        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);

        Type t = allocation.getType();
        //Create allocation with the same type
        Allocation blurredAllocation = Allocation.createTyped(rs, t);
        //Create script
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //Set blur radius (maximum 25.0)
        blurScript.setRadius(BLUR_RADIUS);
        //Set input for script
        blurScript.setInput(allocation);
        //Call script for output allocation
        blurScript.forEach(blurredAllocation);
        //Copy script result into bitmap
        blurredAllocation.copyTo(bitmap);
        //Destroy everything to free memory
        allocation.destroy();
        blurredAllocation.destroy();
        blurScript.destroy();
        rs.destroy();
        return bitmap;
    }

    private static int CACHE_SCREEN_HEIGHT = 0;

    private static int CACHE_SCREEN_WIDTH = 0;

    public static int getScreenHeight(Context ctx) {

        if (CACHE_SCREEN_HEIGHT != 0) {
            return CACHE_SCREEN_HEIGHT;
        }

        WindowManager wm = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        CACHE_SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight();

        return CACHE_SCREEN_HEIGHT;
    }

    public static int getScreenWidth(Context ctx) {

        if (CACHE_SCREEN_WIDTH != 0) {
            return CACHE_SCREEN_WIDTH;
        }

        WindowManager wm = (WindowManager) ctx.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        CACHE_SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();

        return CACHE_SCREEN_WIDTH;
    }

}

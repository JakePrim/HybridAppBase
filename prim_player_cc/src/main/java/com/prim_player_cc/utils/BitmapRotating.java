package com.prim_player_cc.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.prim_player_cc.log.PrimLog;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/9 - 10:01 AM
 */
public class BitmapRotating {
    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        if (bitmap == null || angle == 0) return bitmap;
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (resizedBitmap != bitmap && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        return resizedBitmap;
    }

    private static final String TAG = "BitmapRotating";

    public static Bitmap createPhotos(Bitmap bitmap) {
        Bitmap bmp2 = null;
        if (bitmap != null) {
            Matrix m = new Matrix();
            try {
                m.setRotate(180, bitmap.getWidth() / 2, bitmap.getHeight() / 2);//90就是我们需要选择的90度
                bmp2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2, m, true);
                bitmap.recycle();
                PrimLog.e("FeedVideoManager", "创建图片成功");
            } catch (Exception ex) {
                PrimLog.e("FeedVideoManager", "创建图片失败");
                bmp2 = bitmap;
            }
        } else {
            PrimLog.e("FeedVideoManager", "bitmap为空 无法创建");
        }
        return bmp2;
    }

}

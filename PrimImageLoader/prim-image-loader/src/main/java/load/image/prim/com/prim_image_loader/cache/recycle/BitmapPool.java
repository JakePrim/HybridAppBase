package load.image.prim.com.prim_image_loader.cache.recycle;

import android.graphics.Bitmap;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/18 - 下午11:57
 */
public interface BitmapPool {

    void put(Bitmap bitmap);

    Bitmap get(int width,int height,Bitmap.Config config);
}

package load.image.prim.com.prim_image_loader.cache.recycle;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import java.util.NavigableMap;
import java.util.TreeMap;

import load.image.prim.com.prim_image_loader.cache.Key;

/**
 * @author prim
 * @version 1.0.0
 * @desc bitmap 复用池
 * @time 2018/8/18 - 下午11:58
 */
public class LruBitmapPool extends LruCache<Integer, Bitmap> implements BitmapPool {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruBitmapPool(int maxSize) {
        super(maxSize);
    }

    /**
     * 将bitmap放入复用池
     *
     * @param bitmap
     */
    @Override
    public void put(Bitmap bitmap) {
        //isMutable 必须是true
        if (!bitmap.isMutable()) {
            bitmap.recycle();
            return;
        }

        int allocationByteCount = getSizeOf(bitmap);
        if (allocationByteCount > maxSize()) {
            bitmap.recycle();
            return;
        }
        put(allocationByteCount, bitmap);
        map.put(allocationByteCount, 0);

    }

    protected int getSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //在复用的时候 使用此函数 4.4 以上才允许复用bitmap
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }

    @Override
    protected int sizeOf(Integer key, Bitmap value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return value.getAllocationByteCount();
        }
        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, Integer key, Bitmap oldValue, Bitmap newValue) {
        map.remove(key);
        if (!isRemove){
            oldValue.recycle();
        }
    }

    private boolean isRemove;

    /**
     * @param width
     * @param height
     * @param config
     * @return
     */
    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {
        //新bitmap需要的内存大小
        int size = width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
        Integer key = map.ceilingKey(size);//获得一个等于或者大于size的key
        if (null != key && key <= size * MAX_OVER_SIZE) {//size 不能大于key的8倍
            isRemove = true;
            Bitmap remove = remove(key);
            isRemove = false;
            return remove;
        }
        return null;
    }

    private final static int MAX_OVER_SIZE=8;

    //负责筛选
    NavigableMap<Integer, Integer> map = new TreeMap<>();
}

package load.image.prim.com.prim_image_loader.cache;

import android.os.Build;
import android.util.LruCache;

import load.image.prim.com.prim_image_loader.cache.recycle.Resource;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/18 - 下午10:46
 */
public class LruMemoryCache extends LruCache<Key,Resource> implements MemoryCache {

    OnResourceRemoveListener listener;
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }

    /**
     * 计算返回对应的 value的大小
     * @param key
     * @param value
     * @return
     */
    @Override
    protected int sizeOf(Key key, Resource value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //在复用的时候 使用此函数 4.4 以上才允许复用bitmap
            return value.getBitmap().getAllocationByteCount();
        }
        return value.getBitmap().getByteCount();
    }

    /**
     * 当不足存储的时候移除尾部
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, Key key, Resource oldValue, Resource newValue) {
        //给复用池使用 被动移除
        if (listener != null && oldValue != null && !isRemove){
            listener.onResourceRemove(oldValue);
        }
    }

    private boolean isRemove;

    @Override
    public Resource remove2(Key key) {
        isRemove = true;
        Resource remove = remove(key);
        isRemove = false;
        return remove;
    }

    /**
     * 当资源从内存缓存移除的时候,通知注册此监听的类
     * @param onResourceRemoveListener
     */
    @Override
    public void setOnResourceRemoveListener(OnResourceRemoveListener onResourceRemoveListener) {
        this.listener = onResourceRemoveListener;
    }


}

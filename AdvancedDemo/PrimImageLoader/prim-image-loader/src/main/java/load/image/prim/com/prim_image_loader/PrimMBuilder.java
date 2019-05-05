package load.image.prim.com.prim_image_loader;

import android.app.ActivityManager;
import android.content.Context;

import load.image.prim.com.prim_image_loader.cache.LruMemoryCache;
import load.image.prim.com.prim_image_loader.cache.MemoryCache;
import load.image.prim.com.prim_image_loader.cache.disk.DiskLruCacheWrapper;
import load.image.prim.com.prim_image_loader.cache.disk.IDiskCache;
import load.image.prim.com.prim_image_loader.cache.recycle.BitmapPool;
import load.image.prim.com.prim_image_loader.cache.recycle.LruBitmapPool;

/**
 * @author prim
 * @version 1.0.0
 * @desc 构建者
 * @time 2018/8/23 - 下午11:21
 */
public class PrimMBuilder {

    MemoryCache memoryCache;//内存缓存

    IDiskCache diskCache;//磁盘缓存

    BitmapPool bitmapPool;//复用池

    //自己设置缓存实现
    public void setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    public void setDiskCache(IDiskCache diskCache) {
        this.diskCache = diskCache;
    }

    public void setBitmapPool(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    public PrimM build(Context context) {
        if (null == bitmapPool) {
            bitmapPool = new LruBitmapPool(10);
        }

        if (null == memoryCache) {
            memoryCache = new LruMemoryCache(10);
        }

//TODO      memoryCache.setOnResourceRemoveListener();

        if (null == diskCache) {
            diskCache = new DiskLruCacheWrapper(context);
        }

        return new PrimM(context, this);
    }

    /**
     * 获取最大的缓存大小
     *
     * @param activityManager
     * @return
     */
    private static int getMaxSize(ActivityManager activityManager) {
        int memoryClass = activityManager.getMemoryClass() * 1024 * 1024;
        return Math.round(memoryClass * 0.4f);//最大缓存的0.4
    }
}

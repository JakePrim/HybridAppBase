package load.image.prim.com.prim_image_loader.cache;

import android.graphics.Bitmap;

import load.image.prim.com.prim_image_loader.cache.recycle.BitmapPool;
import load.image.prim.com.prim_image_loader.cache.recycle.LruBitmapPool;
import load.image.prim.com.prim_image_loader.cache.recycle.Resource;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/18 - 下午11:25
 */
public class CacheTest implements Resource.ResourceListener,MemoryCache.OnResourceRemoveListener {
    LruMemoryCache lruMemoryCache;
    ActivityCache activityCache;
    BitmapPool bitmapPool;
    public Resource Test(Key key) {
        //内存缓存
         lruMemoryCache = new LruMemoryCache(10);
        lruMemoryCache.setOnResourceRemoveListener(this);
        bitmapPool = new LruBitmapPool(10);
        //活动缓存
         activityCache = new ActivityCache(this);

        //从活动资源中查找是否有正在使用的图片，保证图片不会从内存缓存中移除
        Resource resource = activityCache.get(key);
        if (null != resource) {
            //当不使用的时候将其 resource.released();
            resource.acquired();//引用计数加1
            return resource;
        }

        //从内存缓存查找是否有正在使用的图片
        resource = lruMemoryCache.get(key);
        if (null != resource) {
            //1 为什么从内存缓存移除？
            //因为lru可能移除此图片，也可能recycle掉此图片
            //如果不移除，则下次使用此图片从轰动资源中能找到名，但是这个图片可能已经被recycle
            lruMemoryCache.remove2(key);//如果内存缓存中存在此图片，将其移除内存缓存，加入到活动缓存中去。
            resource.acquired();//引用计数加1
            //加入到活动资源中
            activityCache.activite(key, resource);
            return resource;
        }

        return null;
    }

    /**
     * 引用计数为0 的时候会走此方法，表示资源没有正在使用了
     * 将此重新加入到内存缓存中
     * @param key
     * @param resource
     */
    @Override
    public void resourceReleased(Key key, Resource resource) {
        activityCache.deactivite(key);
        lruMemoryCache.put(key,resource);
    }


    /**
     * 从内存中被动的移除，而不是主动的移除
     * 放入复用池（内存优化 重复利用图片的内存，减少内存的申请，解决内存的抖动，频繁申请内存带来的抖动与碎片的问题，而不能在利用这一张图片了）
     * @param resource
     */
    @Override
    public void onResourceRemove(Resource resource) {
         bitmapPool.put(resource.getBitmap());
    }
}

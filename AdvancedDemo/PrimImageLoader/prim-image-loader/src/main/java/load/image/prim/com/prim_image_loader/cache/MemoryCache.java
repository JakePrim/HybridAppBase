package load.image.prim.com.prim_image_loader.cache;

import load.image.prim.com.prim_image_loader.cache.recycle.Resource;

/**
 * @author prim
 * @version 1.0.0
 * @desc 内存缓存接口
 * @time 2018/8/18 - 下午10:26
 */
public interface MemoryCache {
    Resource put(Key key, Resource resource);

    Resource remove2(Key key);

    interface OnResourceRemoveListener{
        void onResourceRemove(Resource resource);
    }

    void setOnResourceRemoveListener(OnResourceRemoveListener onResourceRemoveListener);
}

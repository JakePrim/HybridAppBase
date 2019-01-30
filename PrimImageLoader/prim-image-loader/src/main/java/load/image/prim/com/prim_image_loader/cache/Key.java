package load.image.prim.com.prim_image_loader.cache;

import java.security.MessageDigest;

/**
 * @author prim
 * @version 1.0.0
 * @desc 缓存中使用的key
 * @time 2018/8/18 - 下午10:41
 */
public interface Key {
    void updateDiskCacheKey(MessageDigest md);
}

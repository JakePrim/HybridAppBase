package load.image.prim.com.prim_image_loader.load;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.security.MessageDigest;
import java.util.Objects;

import load.image.prim.com.prim_image_loader.cache.Key;

/**
 * @author prim
 * @version 1.0.0
 * @desc 标记加载一个图片的唯一性
 * @time 2018/8/21 - 下午10:25
 */
public class ObjectKey implements Key {

    private final Object object;

    public ObjectKey(Object object) {
        this.object = object;
    }

    /**
     * 当磁盘缓存的时候key只能是字符串，将object变成一个字符串
     * @param md md5/sha1 保证object的唯一性
     */
    @Override
    public void updateDiskCacheKey(MessageDigest md) {
        md.update(object.toString().getBytes());//
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectKey objectKey = (ObjectKey) o;
        return Objects.equals(object, objectKey.object);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(object);
    }
}

package load.image.prim.com.prim_image_loader.cache.disk;

import java.io.File;

import load.image.prim.com.prim_image_loader.cache.Key;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/22 - 下午9:28
 */
public interface IDiskCache {
    interface Writer{
        boolean write(File file);
    }

    File get(Key key);

    void put(Key key,Writer writer);

    void delete(Key key);

    void clear();
}

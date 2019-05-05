package load.image.prim.com.prim_image_loader.cache.disk;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import load.image.prim.com.prim_image_loader.cache.Key;
import load.image.prim.com.prim_image_loader.util.Utils;

/**
 * @author prim
 * @version 1.0.0
 * @desc 磁盘缓存
 * @time 2018/8/22 - 下午9:31
 */
public class DiskLruCacheWrapper implements IDiskCache {


    final static int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;

    final static String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";

    private MessageDigest MD;

    private DiskLruCache diskLruCache;

    public DiskLruCacheWrapper(Context context) {
        this(new File(context.getCacheDir(), DEFAULT_DISK_CACHE_DIR), DEFAULT_DISK_CACHE_SIZE);
    }

    private DiskLruCacheWrapper(File file, long maxSize) {
        try {
            MD = MessageDigest.getInstance("SHA-256");

            //打开一个缓存目录，如果没有则首先创建它
            //缓存地址
            //app版本号，当版本号发生改变时，缓存数据会被清除
            //同一个key可以对应多少文件
            //最大可以缓存当数据量
            diskLruCache = DiskLruCache.open(file, 1, 1, maxSize);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getKey(Key key) {
        key.updateDiskCacheKey(MD);
        return Utils.sha256BytesToHex(MD.digest());
    }

    @Override
    public File get(Key key) {
        String key1 = getKey(key);
        File result = null;
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key1);
            snapshot.getInputStream(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void put(Key key, Writer writer) {
        String k = getKey(key);
//        try {
//            DiskLruCache.Value current = diskLruCache.get(k);
//            if (current != null) {
//                return;
//            }
//            DiskLruCache.Editor editor = diskLruCache.edit(k);
//            try {
//                File file = editor.getFile(0);
//                if (writer.write(file)) {
//                    editor.commit();
//                }
//            } finally {
//                editor.abortUnlessCommitted(c);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void delete(Key key) {
        String k = getKey(key);
        try {
            diskLruCache.remove(k);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

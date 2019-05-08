package demo.prim.com.rxjavademo;

import java.util.HashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-05-08 - 14:59
 */
public class CacheManage {
    public static CacheManage cacheManage;

    private HashMap<String, Object> map;

    public static CacheManage getInstance() {
        synchronized (CacheManage.class) {
            if (cacheManage == null) {
                cacheManage = new CacheManage();
            }
        }
        return cacheManage;
    }


    public CacheManage() {
        this.map = new HashMap<>();
    }

    public void put(String key, String json) {
        map.put(key, json);
    }

    public String get(String key) {
        return (String) map.get(key);
    }
}

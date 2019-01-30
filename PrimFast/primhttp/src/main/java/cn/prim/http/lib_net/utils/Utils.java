package cn.prim.http.lib_net.utils;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/2 - 7:34 PM
 */
public class Utils {
    /** 检查是否为空 */
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}

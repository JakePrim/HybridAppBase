package cn.prim.http.lib_net.utils;

import android.text.TextUtils;

/**
 * @author prim
 * @version 1.0.0
 * @desc 工具类
 * @time 2019/1/2 - 7:34 PM
 */
public class Utils {
    /**
     * 检查是否为空,强制不能为空
     */
    public static <T> T checkForceNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        if (object instanceof String) {
            String v = (String) object;
            if (TextUtils.isEmpty(v)) {
                throw new NullPointerException("空字符串:" + message);
            }
        }
        return object;
    }
}

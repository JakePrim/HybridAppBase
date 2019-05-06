package cn.prim.http.lib_net.utils;

import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc Log 控制
 * @time 2019/1/3 - 4:13 PM
 */
public class PrimHttpLog {
    public static boolean IS_LOG = false;

    private static final String TAG = "PrimHttpLog";

    public static void e(String tag, String msg) {
        if (IS_LOG) {
            Log.e(TAG + ":" + tag, "e: " + msg);
        }
    }

    public static void e(String msg) {
        if (IS_LOG) {
            Log.e(TAG, "e: " + msg);
        }
    }

    public static void d(String msg) {
        if (IS_LOG) {
            Log.d(TAG, "d: " + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (IS_LOG) {
            Log.e(TAG + ":" + tag, "e: " + msg);
        }
    }
}

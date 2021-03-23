package com.prim.primweb.core.utils;

import android.util.Log;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/7 - 1:56 PM
 */
public class PWLog {
    public static boolean LOG = true;

    private static String TAG = "PrimWeb";

    public static void e(String msg) {
        if (LOG) {
            Log.e(TAG, "e: " + msg);
        }
    }

    public static void d(String msg) {
        if (LOG) {
            Log.d(TAG, "d: " + msg);
        }
    }
}

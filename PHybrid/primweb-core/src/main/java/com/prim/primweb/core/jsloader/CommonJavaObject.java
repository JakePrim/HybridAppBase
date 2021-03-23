package com.prim.primweb.core.jsloader;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/31 0031
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CommonJavaObject {

    private static final String TAG = "CommonJavaObject";

    private CommonJSListener commonJSListener;

    public CommonJavaObject(CommonJSListener commonJSListener) {
        this.commonJSListener = commonJSListener;
    }

    @JavascriptInterface
    public void jsFunctionExit(String data) {
        Log.e(TAG, "jsFunctionExit: " + data);
        if (null != this.commonJSListener) {
            this.commonJSListener.jsFunExit(data);
        }

    }

    @JavascriptInterface
    public void jsFunctionNo(String data) {
        Log.e(TAG, "jsFunctionNo: " + data);
        if (null != this.commonJSListener) {
            this.commonJSListener.jsFunNoExit(data);
        }
    }
}

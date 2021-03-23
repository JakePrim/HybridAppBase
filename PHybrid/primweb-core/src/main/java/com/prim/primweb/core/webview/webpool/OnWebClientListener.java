package com.prim.primweb.core.webview.webpool;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-06-26 - 15:38
 */
public interface OnWebClientListener {
    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode, String description, String url);

    boolean shouldOverrideUrlLoading(WebView view, String url);

    boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request);

    void shouldInterceptRequest(WebView view, String url);

    void shouldInterceptRequest(WebView view, WebResourceRequest request);
}

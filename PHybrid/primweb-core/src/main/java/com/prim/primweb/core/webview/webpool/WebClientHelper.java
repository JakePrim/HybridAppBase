package com.prim.primweb.core.webview.webpool;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.prim.primweb.core.utils.PWLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-06-26 - 15:33
 */
public class WebClientHelper {
    private WebViewClient webViewClient;

    private WebChromeClient webChromeClient;

    private OnWebClientListener onWebClientListener;

    private static final WebClientHelper ourInstance = new WebClientHelper();

    public static WebClientHelper getInstance() {
        return ourInstance;
    }


    public WebClientHelper() {
        webViewClient = new MyWebViewClient();
        webChromeClient = new MyWebChromeClient();
    }

    public WebViewClient getWebViewClient() {
        return webViewClient;
    }

    public WebChromeClient getWebChromeClient() {
        return webChromeClient;
    }

    public void setOnWebClientListener(OnWebClientListener listener) {
        this.onWebClientListener = listener;
    }

    public void removeOnWebClientListener(OnWebClientListener listener) {
        this.onWebClientListener = null;
    }

    protected class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            PWLog.e("Web-Log onPageStarted:" + url);
            super.onPageStarted(view, url, favicon);
            if (onWebClientListener != null) {
                onWebClientListener.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            PWLog.e("Web-Log onPageFinished:" + url);
            super.onPageFinished(view, url);
            if (onWebClientListener != null) {
                onWebClientListener.onPageFinished(view, url);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            PWLog.e("Web-Log shouldOverrideUrlLoading:" + request.getUrl());
            if (onWebClientListener != null) {
                return onWebClientListener.shouldOverrideUrlLoading(view, request);
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            PWLog.e("Web-Log shouldOverrideUrlLoading:" + url);
            if (onWebClientListener != null) {
                return onWebClientListener.shouldOverrideUrlLoading(view, url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            PWLog.e("Web-Log shouldInterceptRequest:" + request.getUrl());
            if (onWebClientListener != null) {
                onWebClientListener.shouldInterceptRequest(view, request);
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            PWLog.e("Web-Log shouldInterceptRequest:" + url);
            if (onWebClientListener != null) {
                onWebClientListener.shouldInterceptRequest(view, url);
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String url) {
            super.onReceivedError(view, errorCode, description, url);
            PWLog.e("Web-Log onReceivedError:" + description);
            if (onWebClientListener != null) {
                onWebClientListener.onReceivedError(view, errorCode, description, url);
            }
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            PWLog.e("Web-Log doUpdateVisitedHistory:" + url);
            if (needClearHistory) {
                view.clearHistory();
                needClearHistory = false;
            }
        }
    }

    private boolean needClearHistory = true;

    public void setNeedClearHistory(boolean needClearHistory) {
        this.needClearHistory = true;
    }

    protected class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    }
}

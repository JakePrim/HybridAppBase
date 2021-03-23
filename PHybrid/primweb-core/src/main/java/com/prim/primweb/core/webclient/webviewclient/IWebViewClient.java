package com.prim.primweb.core.webclient.webviewclient;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import com.prim.primweb.core.webview.IAgentWebView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/15 0015
 * 描    述：代理WebViewClient 兼容x5 和 android - WebViewClient
 * 修订历史：
 * ================================================
 */
public interface IWebViewClient {
    void onPageStarted(IAgentWebView view, String url, Bitmap favicon);

    boolean shouldOverrideUrlLoading(IAgentWebView view, String url);

    boolean shouldOverrideUrlLoading(IAgentWebView view, WebResourceRequest request);

    boolean shouldOverrideUrlLoading(IAgentWebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request);

    void onPageFinished(IAgentWebView view, String url);

    void onReceivedError(IAgentWebView view, WebResourceRequest request, WebResourceError error);

    void onReceivedError(IAgentWebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request, com.tencent.smtt.export.external.interfaces.WebResourceError error);

    void onReceivedError(IAgentWebView view, int errorCode, String description, String url);

    void onReceivedHttpError(IAgentWebView view, WebResourceRequest request, WebResourceResponse errorResponse);

    void onReceivedHttpError(IAgentWebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request, com.tencent.smtt.export.external.interfaces.WebResourceResponse errorResponse);

    void onReceivedSslError(IAgentWebView view, SslErrorHandler handler, SslError error);

    void onReceivedSslError(IAgentWebView view, com.tencent.smtt.export.external.interfaces.SslErrorHandler handler, com.tencent.smtt.export.external.interfaces.SslError error);

    void onReceivedHttpAuthRequest(IAgentWebView view, HttpAuthHandler handler, String host, String realm);

    void onReceivedHttpAuthRequest(IAgentWebView view, com.tencent.smtt.export.external.interfaces.HttpAuthHandler handler, String host, String realm);

    void onReceivedClientCertRequest(IAgentWebView view, ClientCertRequest request);

    void onReceivedClientCertRequest(IAgentWebView view, com.tencent.smtt.export.external.interfaces.ClientCertRequest request);

    void onReceivedLoginRequest(IAgentWebView view, String realm, String account, String args);

    WebResourceResponse shouldInterceptRequest(IAgentWebView view, WebResourceRequest request);

    com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(IAgentWebView view, com.tencent.smtt.export.external.interfaces.WebResourceRequest request);

    WebResourceResponse shouldInterceptRequest(IAgentWebView view, String url);

    com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(String url);

    boolean shouldOverrideKeyEvent(IAgentWebView view, KeyEvent event);

    void onLoadResource(IAgentWebView view, String url);

    void onPageCommitVisible(IAgentWebView view, String url);

    void doUpdateVisitedHistory(IAgentWebView view, String url, boolean isReload);

    void onUnhandledKeyEvent(IAgentWebView webView, KeyEvent keyEvent);

    void onScaleChanged(IAgentWebView view, float oldScale, float newScale);

    void onFormResubmission(IAgentWebView view, Message dontResend, Message resend);

    void onTooManyRedirects(IAgentWebView view, Message cancelMsg, Message continueMsg);
}

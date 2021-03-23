package com.prim.primweb.core.webclient.webviewclient;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.prim.primweb.core.webview.IAgentWebView;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/15 0015
 * 描    述：agent - WebViewClient
 * 修订历史：
 * ================================================
 */
public abstract class AgentWebViewClient implements IWebViewClient {

    @Override
    public void onPageStarted(IAgentWebView view, String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(IAgentWebView view, String url) {

    }

    @Override
    public boolean shouldOverrideUrlLoading(IAgentWebView view, String url) {
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(IAgentWebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    @SuppressLint("NewApi")
    @Override
    public boolean shouldOverrideUrlLoading(IAgentWebView view, android.webkit.WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(IAgentWebView view, android.webkit.WebResourceRequest request) {
        return shouldInterceptRequest(view, request.getUrl().toString());
    }

    @Override
    public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(IAgentWebView view, WebResourceRequest request) {
        return null;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(IAgentWebView view, String url) {
        return null;
    }

    @Override
    public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(String url) {
        return null;
    }

    @Override
    public boolean shouldOverrideKeyEvent(IAgentWebView view, KeyEvent event) {
        return false;
    }

    @Override
    public void onReceivedSslError(IAgentWebView view, SslErrorHandler handler, SslError error) {
        handler.cancel();
    }

    @Override
    public void onReceivedSslError(IAgentWebView view, com.tencent.smtt.export.external.interfaces.SslErrorHandler handler, com.tencent.smtt.export.external.interfaces.SslError error) {
        handler.cancel();
    }

    @Override
    public void onScaleChanged(IAgentWebView view, float oldScale, float newScale) {

    }

    @Override
    public void onReceivedLoginRequest(IAgentWebView view, String realm, String account, String args) {

    }

    @SuppressLint("NewApi")
    @Override
    public void onReceivedClientCertRequest(IAgentWebView view, ClientCertRequest request) {
        request.cancel();
    }

    @Override
    public void onReceivedClientCertRequest(IAgentWebView view, com.tencent.smtt.export.external.interfaces.ClientCertRequest request) {
        request.cancel();
    }

    @Override
    public void onReceivedHttpAuthRequest(IAgentWebView view, HttpAuthHandler handler, String host, String realm) {
        handler.cancel();
    }

    @Override
    public void onReceivedHttpAuthRequest(IAgentWebView view, com.tencent.smtt.export.external.interfaces.HttpAuthHandler handler, String host, String realm) {
        handler.cancel();
    }

    @Override
    public void onReceivedHttpError(IAgentWebView view, android.webkit.WebResourceRequest request, WebResourceResponse errorResponse) {

    }

    @Override
    public void onReceivedHttpError(IAgentWebView view, WebResourceRequest request, com.tencent.smtt.export.external.interfaces.WebResourceResponse errorResponse) {

    }

    @Override
    public void onReceivedError(IAgentWebView view, int errorCode, String description, String url) {

    }

    @SuppressLint("NewApi")
    @Override
    public void onReceivedError(IAgentWebView view, android.webkit.WebResourceRequest request, WebResourceError error) {
        if (request.isForMainFrame()) {
            onReceivedError(view,
                    error.getErrorCode(), error.getDescription().toString(),
                    request.getUrl().toString());
        }
    }

    @Override
    public void onReceivedError(IAgentWebView view, WebResourceRequest request, com.tencent.smtt.export.external.interfaces.WebResourceError error) {

    }


    @Override
    public void onUnhandledKeyEvent(IAgentWebView webView, KeyEvent keyEvent) {
    }


    @Override
    public void onFormResubmission(IAgentWebView view, Message dontResend, Message resend) {
        dontResend.sendToTarget();
    }

    @Override
    public void onTooManyRedirects(IAgentWebView view, Message cancelMsg, Message continueMsg) {
        cancelMsg.sendToTarget();
    }

    @Override
    public void onPageCommitVisible(IAgentWebView view, String url) {

    }

    @Override
    public void doUpdateVisitedHistory(IAgentWebView view, String url, boolean isReload) {

    }

    @Override
    public void onLoadResource(IAgentWebView view, String url) {

    }
}

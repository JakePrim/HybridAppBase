package com.prim.primweb.core.webclient.webviewclient;

import android.annotation.SuppressLint;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.prim.primweb.core.webclient.webviewclient.AgentWebViewClient;
import com.prim.primweb.core.webclient.webviewclient.IWebViewClient;
import com.prim.primweb.core.webview.IAgentWebView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/15 0015
 * 描    述：作为WebView的基类
 * 可使用原声的WebViewClient 也可以使用具有兼容性代理的AgentWebViewClient
 * 修订历史：
 * ================================================
 */
public abstract class BaseAndroidWebClient extends WebViewClient {
    private WebViewClient webViewClient;

    private AgentWebViewClient agentWebViewClient;

    private IAgentWebView agentWebView;

    public void setWebViewClient(WebViewClient webViewClient) {
        this.webViewClient = webViewClient;
    }

    public void setWebViewClient(AgentWebViewClient webViewClient, IAgentWebView agentWebView) {
        this.agentWebViewClient = webViewClient;
        this.agentWebView = agentWebView;
    }


    @SuppressLint("NewApi")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (this.webViewClient != null) {
            return this.webViewClient.shouldOverrideUrlLoading(view, request);
        }
        if (this.agentWebViewClient != null) {
            return this.agentWebViewClient.shouldOverrideUrlLoading(agentWebView, request);
        }
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (this.webViewClient != null) {
            return this.webViewClient.shouldOverrideUrlLoading(view, url);
        }
        if (this.agentWebViewClient != null) {
            return this.agentWebViewClient.shouldOverrideUrlLoading(agentWebView, url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.webViewClient != null) {
            this.webViewClient.onPageStarted(view, url, favicon);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onPageStarted(agentWebView, url, favicon);
            return;
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (this.webViewClient != null) {
            this.webViewClient.onPageFinished(view, url);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onPageFinished(agentWebView, url);
            return;
        }
        super.onPageFinished(view, url);
    }

    @SuppressLint("NewApi")
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedError(view, request, error);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedError(agentWebView, request, error);
            return;
        }
        super.onReceivedError(view, request, error);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedError(view, errorCode, description, failingUrl);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedError(agentWebView, errorCode, description, failingUrl);
            return;
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @SuppressLint("NewApi")
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedHttpError(view, request, errorResponse);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedHttpError(agentWebView, request, errorResponse);
            return;
        }
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedSslError(view, handler, error);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedSslError(agentWebView, handler, error);
            return;
        }
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedHttpAuthRequest(agentWebView, handler, host, realm);
            return;
        }
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @SuppressLint("NewApi")
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedClientCertRequest(view, request);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedClientCertRequest(agentWebView, request);
            return;
        }
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (this.webViewClient != null) {
            this.webViewClient.onReceivedLoginRequest(view, realm, account, args);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onReceivedLoginRequest(agentWebView, realm, account, args);
            return;
        }
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (this.webViewClient != null) {
            return this.webViewClient.shouldInterceptRequest(view, request);
        }

        if (this.agentWebViewClient != null) {
            return this.agentWebViewClient.shouldInterceptRequest(agentWebView, request);
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (this.webViewClient != null) {
            return this.webViewClient.shouldInterceptRequest(view, url);
        }

        if (this.agentWebViewClient != null) {
            return this.agentWebViewClient.shouldInterceptRequest(agentWebView, url);
        }
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (this.webViewClient != null) {
            return this.webViewClient.shouldOverrideKeyEvent(view, event);
        }

        if (this.agentWebViewClient != null) {
            return this.agentWebViewClient.shouldOverrideKeyEvent(agentWebView, event);
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.webViewClient != null) {
            this.webViewClient.onUnhandledKeyEvent(view, event);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onUnhandledKeyEvent(agentWebView, event);
        }
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        if (this.webViewClient != null) {
            this.webViewClient.onLoadResource(view, url);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onLoadResource(agentWebView, url);
            return;
        }
        super.onLoadResource(view, url);
    }

    @SuppressLint("NewApi")
    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if (this.webViewClient != null) {
            this.webViewClient.onPageCommitVisible(view, url);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onPageCommitVisible(agentWebView, url);
            return;
        }
        super.onPageCommitVisible(view, url);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (this.webViewClient != null) {
            this.webViewClient.onTooManyRedirects(view, cancelMsg, continueMsg);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onTooManyRedirects(agentWebView, cancelMsg, continueMsg);
            return;
        }
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.webViewClient != null) {
            this.webViewClient.onFormResubmission(view, dontResend, resend);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onFormResubmission(agentWebView, dontResend, resend);
            return;
        }
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.webViewClient != null) {
            this.webViewClient.doUpdateVisitedHistory(view, url, isReload);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.doUpdateVisitedHistory(agentWebView, url, isReload);
            return;
        }
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.webViewClient != null) {
            this.webViewClient.onScaleChanged(view, oldScale, newScale);
            return;
        }

        if (this.agentWebViewClient != null) {
            this.agentWebViewClient.onScaleChanged(agentWebView, oldScale, newScale);
            return;
        }
        super.onScaleChanged(view, oldScale, newScale);
    }
}

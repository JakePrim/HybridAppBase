package com.prim.primweb.core.webclient;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebViewClient;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.uicontroller.AbsWebUIController;
import com.prim.primweb.core.webclient.webviewclient.AgentWebViewClient;
import com.prim.primweb.core.webclient.webviewclient.DefaultAndroidWebViewClient;
import com.prim.primweb.core.webclient.webviewclient.DefaultX5WebViewClient;
import com.prim.primweb.core.webview.IAgentWebView;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/15 0015
 * 描    述：所有的WebClient的总处理类
 * 修订历史：
 * ================================================
 */
public class PrimWebClient {
    private WeakReference<Context> mActivity;

    private WebViewClient andWebViewClient;

    private com.tencent.smtt.sdk.WebViewClient x5WebViewClient;

    private AgentWebViewClient agentWebViewClient;

    private DefaultAndroidWebViewClient defaultAndroidWebViewClient;

    private DefaultX5WebViewClient defaultX5WebViewClient;

    private IAgentWebView webView;

    private PrimWeb.WebViewType type;

    private boolean alwaysOpenOtherPage;

    public PrimWebClient(Builder builder) {
        mActivity = new WeakReference<>(builder.activity);
        this.andWebViewClient = builder.andWebViewClient;
        this.x5WebViewClient = builder.x5WebViewClient;
        this.agentWebViewClient = builder.agentWebViewClient;
        this.webView = builder.webView;
        this.type = builder.type;
        this.alwaysOpenOtherPage = builder.alwaysOpenOtherPage;
        if (this.type != null && webView != null) {
            if (this.type.equals(PrimWeb.WebViewType.X5)) {
                this.defaultX5WebViewClient = new DefaultX5WebViewClient(builder.activity, builder);
                if (this.x5WebViewClient != null) {
                    this.defaultX5WebViewClient.setWebViewClient(x5WebViewClient);
                } else if (this.agentWebViewClient != null) {
                    this.defaultX5WebViewClient.setWebViewClient(agentWebViewClient, webView);
                }
                webView.setAgentWebViewClient(defaultX5WebViewClient);
            } else {
                this.defaultAndroidWebViewClient = new DefaultAndroidWebViewClient(builder.activity, builder);
                if (this.andWebViewClient != null) {
                    this.defaultAndroidWebViewClient.setWebViewClient(andWebViewClient);
                } else if (this.agentWebViewClient != null) {
                    this.defaultAndroidWebViewClient.setWebViewClient(agentWebViewClient, webView);
                }
                webView.setAgentWebViewClient(defaultAndroidWebViewClient);
            }
        }
    }

    public static Builder createClientBuilder() {
        return new Builder();
    }

    public static class Builder implements Serializable {
        public Context activity;

        public WebViewClient andWebViewClient;

        public com.tencent.smtt.sdk.WebViewClient x5WebViewClient;

        public AgentWebViewClient agentWebViewClient;

        public IAgentWebView webView;

        public PrimWeb.WebViewType type;

        public boolean alwaysOpenOtherPage;

        public AbsWebUIController absWebUIController;

        public Builder setAbsWebUIController(AbsWebUIController absWebUIController) {
            this.absWebUIController = absWebUIController;
            return this;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setWebViewClient(WebViewClient andWebViewClient) {
            this.andWebViewClient = andWebViewClient;
            return this;
        }

        public Builder setWebViewClient(com.tencent.smtt.sdk.WebViewClient x5WebViewClient) {
            this.x5WebViewClient = x5WebViewClient;
            return this;
        }

        public Builder setWebViewClient(AgentWebViewClient agentWebViewClient) {
            this.agentWebViewClient = agentWebViewClient;
            return this;
        }

        public Builder setWebView(IAgentWebView webView) {
            this.webView = webView;
            return this;
        }

        public Builder setType(PrimWeb.WebViewType type) {
            this.type = type;
            return this;
        }

        public Builder setAlwaysOpenOtherPage(boolean alwaysOpenOtherPage) {
            this.alwaysOpenOtherPage = alwaysOpenOtherPage;
            return this;
        }

        public PrimWebClient build() {
            return new PrimWebClient(this);
        }
    }
}

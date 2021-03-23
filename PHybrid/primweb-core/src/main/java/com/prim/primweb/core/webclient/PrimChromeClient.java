package com.prim.primweb.core.webclient;

import android.app.Activity;
import android.webkit.WebChromeClient;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.uicontroller.AbsWebUIController;
import com.prim.primweb.core.uicontroller.IndicatorController;
import com.prim.primweb.core.webclient.webchromeclient.AgentChromeClient;
import com.prim.primweb.core.webclient.webchromeclient.DefaultAndroidChromeClient;
import com.prim.primweb.core.webclient.webchromeclient.DefaultX5ChromeClient;
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
public class PrimChromeClient {
    private WeakReference<Activity> mActivity;

    private WebChromeClient androidChromeClient;

    private com.tencent.smtt.sdk.WebChromeClient x5ChromeClient;

    private AgentChromeClient agentChromeClient;

    private DefaultAndroidChromeClient defaultAndroidChromeClient;

    private DefaultX5ChromeClient defaultX5ChromeClient;

    private IAgentWebView webView;

    private PrimWeb.WebViewType type;

    public PrimChromeClient(Builder builder) {
        mActivity = new WeakReference<>(builder.activity);
        this.androidChromeClient = builder.androidChromeClient;
        this.x5ChromeClient = builder.x5ChromeClient;
        this.agentChromeClient = builder.agentChromeClient;
        this.webView = builder.webView;
        this.type = builder.type;
        if (this.type != null && webView != null) {
            if (this.type.equals(PrimWeb.WebViewType.X5)) {
                this.defaultX5ChromeClient = new DefaultX5ChromeClient(builder);
                if (this.x5ChromeClient != null) {
                    this.defaultX5ChromeClient.setChromeClient(x5ChromeClient);
                } else if (this.agentChromeClient != null) {
                    this.defaultX5ChromeClient.setChromeClient(agentChromeClient, webView);
                }
                webView.setAgentWebChromeClient(defaultX5ChromeClient);
            } else {
                this.defaultAndroidChromeClient = new DefaultAndroidChromeClient(builder);
                if (this.androidChromeClient != null) {
                    this.defaultAndroidChromeClient.setChromeClient(androidChromeClient);
                } else if (this.agentChromeClient != null) {
                    this.defaultAndroidChromeClient.setChromeClient(agentChromeClient, webView);
                }
                webView.setAgentWebChromeClient(defaultAndroidChromeClient);
            }
        }
    }

    public static Builder createChromeBuilder() {
        return new Builder();
    }

    public static class Builder implements Serializable {
        public Activity activity;

        private WebChromeClient androidChromeClient;

        private com.tencent.smtt.sdk.WebChromeClient x5ChromeClient;

        private AgentChromeClient agentChromeClient;

        public IAgentWebView webView;

        public PrimWeb.WebViewType type;

        public AbsWebUIController absWebUIController;

        public IndicatorController indicatorController;

        public boolean isGeolocation;

        public boolean allowUploadFile;

        /**
         * 上传文件  false 调用系统的文件, true 调用第三库或者自定义的文件
         */
        public boolean invokingThird;

        public Builder setInvokingThird(boolean invokingThird) {
            this.invokingThird = invokingThird;
            return this;
        }

        public Builder setAllowUploadFile(boolean allowUploadFile) {
            this.allowUploadFile = allowUploadFile;
            return this;
        }

        public Builder setGeolocation(boolean geolocation) {
            isGeolocation = geolocation;
            return this;
        }

        public Builder setIndicatorController(IndicatorController indicatorController) {
            this.indicatorController = indicatorController;
            return this;
        }

        public Builder setAbsWebUIController(AbsWebUIController absWebUIController) {
            this.absWebUIController = absWebUIController;
            return this;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setWebChromeClient(WebChromeClient androidChromeClient) {
            this.androidChromeClient = androidChromeClient;
            return this;
        }

        public Builder setWebChromeClient(com.tencent.smtt.sdk.WebChromeClient x5ChromeClient) {
            this.x5ChromeClient = x5ChromeClient;
            return this;
        }

        public Builder setWebChromeClient(AgentChromeClient agentChromeClient) {
            this.agentChromeClient = agentChromeClient;
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

        public PrimChromeClient build() {
            return new PrimChromeClient(this);
        }
    }
}

package com.prim.primweb.core.urlloader;

import com.prim.primweb.core.utils.PrimWebUtils;
import com.prim.primweb.core.webview.IAgentWebView;

import java.util.Map;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/15 0015
 * 描    述：将webview加载url代理出来
 * 修订历史：
 * ================================================
 */
public class UrlLoader implements IUrlLoader {
    private IAgentWebView webView;

    public UrlLoader(IAgentWebView webView) {
        this.webView = webView;
    }

    @Override
    public void loadUrl(String url) {
        this.loadUrl(url, null);
    }

    @Override
    public void loadUrl(final String url, final Map<String, String> headers) {
        if (!PrimWebUtils.isUIThread()) {
            PrimWebUtils.runUIRunable(new Runnable() {
                @Override
                public void run() {
                    loadUrl(url, headers);
                }
            });
        }
        if (headers == null || headers.isEmpty()) {
            webView.loadAgentUrl(url);
        } else {
            webView.loadAgentUrl(url, headers);
        }
    }

    @Override
    public void reload() {
        if (!PrimWebUtils.isUIThread()) {
            PrimWebUtils.runUIRunable(new Runnable() {
                @Override
                public void run() {
                    reload();
                }
            });
        }
        webView.reloadAgent();
    }

    @Override
    public void stopLoading() {
        if (!PrimWebUtils.isUIThread()) {
            PrimWebUtils.runUIRunable(new Runnable() {
                @Override
                public void run() {
                    stopLoading();
                }
            });
        }
        webView.stopLoadingAgent();
    }

    @Override
    public void postUrl(final String url, final byte[] params) {
        if (!PrimWebUtils.isUIThread()) {
            PrimWebUtils.runUIRunable(new Runnable() {
                @Override
                public void run() {
                    postUrl(url, params);
                }
            });
        }
        webView.postUrlAgent(url, params);
    }

    @Override
    public void loadData(final String data, final String mimeType, final String encoding) {
        if (!PrimWebUtils.isUIThread()) {
            PrimWebUtils.runUIRunable(new Runnable() {
                @Override
                public void run() {
                    loadData(data, mimeType, encoding);
                }
            });
        }
        webView.loadDataAgent(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(final String baseUrl, final String data, final String mimeType, final String encoding, final String historyUrl) {
        if (!PrimWebUtils.isUIThread()) {
            PrimWebUtils.runUIRunable(new Runnable() {
                @Override
                public void run() {
                    loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
                }
            });
        }
        webView.loadDataWithBaseURLAgent(baseUrl, data, mimeType, encoding, historyUrl);
    }
}

package com.prim.primweb.core.weblife;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.webview.IAgentWebView;
import com.prim.primweb.core.webview.webpool.WebViewPool;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/16 0016
 * 描    述：webview 生命周期管理
 * 修订历史：
 * ================================================
 */
public class WebLifeCycle implements IWebLifeCycle {

    private IAgentWebView webView;

    public WebLifeCycle(IAgentWebView webView) {
        this.webView = webView;
    }

    @Override
    public void onResume() {
        webView.onAgentResume();
    }

    @Override
    public void onPause() {
        webView.onAgentPause();
    }

    @Override
    public void onDestory() {
        webView.onAgentDestory();
        PrimWeb.removeJsUploadChooserCallback();
        PrimWeb.removeThriedChooserListener();
    }
}

package com.prim.primweb.core.webview.webpool;

import android.content.Context;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.webclient.webchromeclient.AgentChromeClient;
import com.prim.primweb.core.webclient.webviewclient.AgentWebViewClient;
import com.prim.primweb.core.websetting.BaseAgentWebSetting;
import com.prim.primweb.core.webview.IAgentWebView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/23 - 7:01 PM
 */
public interface IWebPool {
    void initPool(Context context, BaseAgentWebSetting setting, Class<? extends IJavascriptInterface> javascriptInterface, String name);

    void setPoolSize(int size);

    IAgentWebView get(PrimWeb.WebViewType type);

    void resetWebView(IAgentWebView webView);
}

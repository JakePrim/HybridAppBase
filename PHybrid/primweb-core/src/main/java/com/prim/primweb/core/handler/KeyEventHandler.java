package com.prim.primweb.core.handler;

import android.view.KeyEvent;

import com.prim.primweb.core.utils.PWLog;
import com.prim.primweb.core.webview.IAgentWebView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/14 0014
 * 描    述：手机返回键的处理
 * 修订历史：
 * ================================================
 */
public class KeyEventHandler implements IKeyEvent {

    private IAgentWebView webView;

    private IKeyEventInterceptor keyEventInterceptor;

    public static KeyEventHandler getInstance(IAgentWebView webView, IKeyEventInterceptor keyEventInterceptor) {
        return new KeyEventHandler(webView, keyEventInterceptor);
    }

    public KeyEventHandler(IAgentWebView webView, IKeyEventInterceptor keyEventInterceptor) {
        this.webView = webView;
        this.keyEventInterceptor = keyEventInterceptor;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK && back();
    }

    @Override
    public boolean back() {
        //拦截处理
        if (keyEventInterceptor != null && keyEventInterceptor.event()) {
            return true;
        }
        PWLog.e("Web-Log:" + webView.getAgentUrl());
        if (webView != null) {
            if (webView.goBackAgent() && !webView.getAgentUrl().equals("about:blank")) {
                return true;
            }
        }
        return false;
    }
}

package com.prim.primweb.core.websetting;

import com.prim.primweb.core.webview.IAgentWebView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IAgentWebSetting<T> {
    T getWebSetting();

    IAgentWebSetting setSetting(IAgentWebView webView);
}

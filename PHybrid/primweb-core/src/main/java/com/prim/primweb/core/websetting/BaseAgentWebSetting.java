package com.prim.primweb.core.websetting;

import android.content.Context;

import com.prim.primweb.core.webview.IAgentWebView;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：代理websetting的基类
 * 修订历史：
 * ================================================
 */
public abstract class BaseAgentWebSetting<T> implements IAgentWebSetting<T> {

    protected Context context;

    public BaseAgentWebSetting(Context context) {
        this.context = context;
    }

    private T mSettings;

    private static final String TAG = "BaseAgentWebSetting";

    @Override
    public T getWebSetting() {
        return mSettings;
    }

    @Override
    public IAgentWebSetting setSetting(IAgentWebView webView) {
        try {
            mSettings = (T) webView.getWebSetting();
            toSetting(mSettings);
        } catch (Exception e) {

        }
        return this;
    }

    protected abstract void toSetting(T webSetting);
}

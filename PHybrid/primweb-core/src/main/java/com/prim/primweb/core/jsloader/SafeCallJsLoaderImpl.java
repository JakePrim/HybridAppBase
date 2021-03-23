package com.prim.primweb.core.jsloader;

import android.os.Handler;
import android.os.Looper;

import com.prim.primweb.core.webview.IAgentWebView;
import com.prim.primweb.core.utils.PrimWebUtils;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：安全的js方法加载器实现
 * 修订历史：
 * ================================================
 */
public class SafeCallJsLoaderImpl extends BaseCallJsLoader {
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static SafeCallJsLoaderImpl getInstance(IAgentWebView webView) {
        return new SafeCallJsLoaderImpl(webView);
    }

    private SafeCallJsLoaderImpl(IAgentWebView webView) {
        super(webView);
    }

    private void safeCallJs(final String js, final AgentValueCallback<String> callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                call(js, callback);
            }
        });
    }

    @Override
    protected void call(String js, AgentValueCallback<String> callback) {
        if (!PrimWebUtils.isUIThread()) {
            safeCallJs(js, callback);
            return;
        }
        super.call(js, callback);
    }
}

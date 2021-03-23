package com.prim.primweb.core.webview.webpool;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.config.ConfigKey;
import com.prim.primweb.core.config.Configurator;
import com.prim.primweb.core.utils.PWLog;
import com.prim.primweb.core.websetting.BaseAgentWebSetting;
import com.prim.primweb.core.webview.AndroidAgentWebView;
import com.prim.primweb.core.webview.IAgentWebView;

import java.util.Stack;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-11-04 - 11:25
 * @contact https://jakeprim.cn
 * @name PeopleDaily_Android_CN
 */
public class PreloadWebView {

    private static final int CACHE_WEBVIEW_MAX_NUM = 3;

    private final Stack<IAgentWebView> mCacheWebViewStack = new Stack<>();

    public static PreloadWebView getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PreloadWebView INSTANCE = new PreloadWebView();
    }

    private IJavascriptInterface javascriptInterface;

    private String jsName;

    private final byte[] lock = new byte[]{};//synchronized 字节 优化性能

    private String baseUrl;

    private BaseAgentWebSetting setting;

    private Context mContext;

    public void init(Context context, BaseAgentWebSetting setting, IJavascriptInterface javascriptInterface, String name) {
        this.mContext = context;
        this.baseUrl = Configurator.getInstance().getConfiguration(ConfigKey.API_HOST);
        this.javascriptInterface = javascriptInterface;
        this.jsName = name;
        this.setting = setting;
    }

    public void preload() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (mCacheWebViewStack.size() < CACHE_WEBVIEW_MAX_NUM) {
                    PWLog.e("PreloadWebView.queueIdle 创建webview 加入缓存池");
                    mCacheWebViewStack.push(createWebView());
                }
                return false;
            }
        });
    }

    private IAgentWebView createWebView() {
        AndroidAgentWebView androidAgentWebView = new AndroidAgentWebView(new MutableContextWrapper(mContext));
        androidAgentWebView.setWebViewClient(WebClientHelper.getInstance().getWebViewClient());
        androidAgentWebView.setWebChromeClient(WebClientHelper.getInstance().getWebChromeClient());
        setting.setSetting(androidAgentWebView);
        androidAgentWebView.loadAgentUrl(baseUrl);
        androidAgentWebView.addJavascriptInterfaceAgent(javascriptInterface, jsName);//设置JavaScriptInterface
        return androidAgentWebView;
    }

    public IAgentWebView getWebView(PrimWeb.WebViewType type, Context context) {
        synchronized (lock) {
            if (mCacheWebViewStack == null || mCacheWebViewStack.isEmpty()) {
                PWLog.e("PreloadWebView.getWebView 从缓存池中没有重新创建");
                IAgentWebView webView = createWebView();
//                MutableContextWrapper contextWrapper = (MutableContextWrapper) webView.getWebContext();
//                contextWrapper.setBaseContext(context);
                return webView;
            }
            PWLog.e("PreloadWebView.getWebView 缓存池中获取");
            IAgentWebView webView = mCacheWebViewStack.pop();
            // webView不为空，则开始使用预创建的WebView,并且替换Context
//            MutableContextWrapper contextWrapper = (MutableContextWrapper) webView.getWebContext();
//            contextWrapper.setBaseContext(context);
            return webView;
        }
    }
}

package com.prim.primweb.core.webview.webpool;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.config.ConfigKey;
import com.prim.primweb.core.config.Configurator;
import com.prim.primweb.core.utils.PWLog;
import com.prim.primweb.core.utils.PrimWebUtils;
import com.prim.primweb.core.webclient.webchromeclient.AgentChromeClient;
import com.prim.primweb.core.webclient.webchromeclient.DefaultX5ChromeClient;
import com.prim.primweb.core.webclient.webviewclient.AgentWebViewClient;
import com.prim.primweb.core.webclient.webviewclient.DefaultX5WebViewClient;
import com.prim.primweb.core.websetting.BaseAgentWebSetting;
import com.prim.primweb.core.webview.AndroidAgentWebView;
import com.prim.primweb.core.webview.IAgentWebView;
import com.prim.primweb.core.webview.X5AgentWebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author prim
 * @version 1.0.0
 * @desc WebView复用池
 * @time 2019/1/23 - 6:55 PM
 */
public class WebViewPool implements IWebPool {

    private volatile static WebViewPool webViewPool;//DCL
    //当前正在使用的webview
    private List<IAgentWebView> mInUse;

    private List<IAgentWebView> mX5InUse;

    //可复用的webview
    private List<IAgentWebView> mAvailable;

    private Context mContext;

    //池子的默认大小
    private int mPoolSize = 3;

    private int mCurrentSize = 0;

    //是否初始化了x5的内核
    private boolean isX5Core = false;

    private PrimWeb.WebViewType mType;

    private IAgentWebView webView = null;

    private final byte[] lock = new byte[]{};//synchronized 字节 优化性能

    private String baseUrl;

    private BaseAgentWebSetting setting;

    private Handler handler;

    private Map<IAgentWebView, Object> javaBridgeMap;

    public WebViewPool() {
        javaBridgeMap = new LinkedHashMap<>();
        mAvailable = new ArrayList<>();
        mInUse = new CopyOnWriteArrayList<>();
        mX5InUse = new ArrayList<>();
        handler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    public static WebViewPool getInstance() {
        if (webViewPool == null) {
            synchronized (WebViewPool.class) {
                if (webViewPool == null) {
                    webViewPool = new WebViewPool();
                }
            }
        }
        return webViewPool;
    }

    private boolean isInitPool = false;

    /**
     * 将初始化标记 和 功能绑定
     */
    private Map<String,Boolean> initPoolMap = new LinkedHashMap<>();

    public boolean isInitPool() {
        return isInitPool;
    }

    private Class<? extends IJavascriptInterface> javascriptInterface;

    private String jsName;

    public Object getJavaBridge(IAgentWebView agentWebView) {
        return javaBridgeMap.get(agentWebView);
    }

    @Override
    public void initPool(@NonNull final Context context, BaseAgentWebSetting setting, Class<? extends IJavascriptInterface> javascriptInterface, String name) {
        if (isInitPool) {
            return;
        }
        this.isInitPool = true;
        this.mContext = context;
        this.isX5Core = Configurator.getInstance().getConfiguration(ConfigKey.WEB_X5CORE);
        this.baseUrl = Configurator.getInstance().getConfiguration(ConfigKey.API_HOST);
        this.javascriptInterface = javascriptInterface;
        this.jsName = name;
        this.setting = setting;
        for (int i = 0; i < mPoolSize; i++) {//new 出多个复用池
            try {
                if (isX5Core) {
                    X5AgentWebView x5AgentWebView = new X5AgentWebView(new MutableContextWrapper(context));//引入Context中间层MutableContextWrapper
                    setting.setSetting(x5AgentWebView);
                    x5AgentWebView.loadAgentUrl(baseUrl);//提前加载好模版

                    if (javascriptInterface != null) {
                        Object iJavascriptInterface = javascriptInterface.newInstance();
                        javaBridgeMap.put(x5AgentWebView, iJavascriptInterface);
                        x5AgentWebView.addJavascriptInterface(javascriptInterface, name);//设置JavaScriptInterface
                    }
                    mAvailable.add(x5AgentWebView);
                    PWLog.d("Web-Log -> 预初始化WebView完成");
                } else {
                    AndroidAgentWebView androidAgentWebView = new AndroidAgentWebView(new MutableContextWrapper(context));
                    androidAgentWebView.setWebViewClient(WebClientHelper.getInstance().getWebViewClient());
                    androidAgentWebView.setWebChromeClient(WebClientHelper.getInstance().getWebChromeClient());
                    if (setting != null) {
                        setting.setSetting(androidAgentWebView);
                    }
                    androidAgentWebView.loadAgentUrl(baseUrl);

                    if (javascriptInterface != null) {
                        Object iJavascriptInterface = javascriptInterface.newInstance();
                        PWLog.e("WebViewPool -> " + iJavascriptInterface);
                        javaBridgeMap.put(androidAgentWebView, iJavascriptInterface);
                        androidAgentWebView.addJavascriptInterfaceAgent(iJavascriptInterface, name);//设置JavaScriptInterface
                    }
                    mAvailable.add(androidAgentWebView);
                }
            } catch (Exception e) {
                PWLog.e("WebViewPool X5 内核没有初始化或者下载完毕");
                AndroidAgentWebView androidAgentWebView = new AndroidAgentWebView(new MutableContextWrapper(context));
                setting.setSetting(androidAgentWebView);
                androidAgentWebView.setWebViewClient(WebClientHelper.getInstance().getWebViewClient());
                androidAgentWebView.setWebChromeClient(WebClientHelper.getInstance().getWebChromeClient());
                androidAgentWebView.loadAgentUrl(baseUrl);

                if (javascriptInterface != null) {
                    Object iJavascriptInterface = null;
                    try {
                        iJavascriptInterface = javascriptInterface.newInstance();
                        PWLog.e("WebViewPool -> " + iJavascriptInterface.getClass().getName());
                        javaBridgeMap.put(androidAgentWebView, iJavascriptInterface);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    androidAgentWebView.addJavascriptInterfaceAgent(javascriptInterface, name);//设置JavaScriptInterface
                }
                mAvailable.add(androidAgentWebView);
            }
        }
        PWLog.e("WebViewPool 复用池初始化完毕:" + mAvailable.size());
    }

    @Override
    public void setPoolSize(int size) {
        this.mPoolSize = size;
    }

    @Override
    public synchronized IAgentWebView get(final PrimWeb.WebViewType type) {
        synchronized (lock) {
            this.mType = type;
            this.isX5Core = Configurator.getInstance().getConfiguration(ConfigKey.WEB_X5CORE);
            handleWebView(type);
            return webView;
        }
    }

    private void handleWebView(PrimWeb.WebViewType type) {
        isResetWeb = false;
        PWLog.e("WebViewPool type:" + type + " isX5Core:" + isX5Core);
        if (type == PrimWeb.WebViewType.X5 && isX5Core) {
            if (mAvailable.size() > 0) {
                webView = mAvailable.get(0);
                mAvailable.remove(0);
                mCurrentSize++;
                PWLog.e("WebViewPool get: 获取已经初始化好的webview：" + webView);
            } else {
                //可用容量太小时,webview自动扩容
                PWLog.e("WebViewPool 可用容量大小 webview自动扩容");
                addX5WebView();
            }
            mX5InUse.add(webView);
        } else {
            if (mAvailable.size() > 0) {
                webView = mAvailable.get(0);
                mAvailable.remove(0);
                mCurrentSize++;
                PWLog.e("WebViewPool get: 获取已经初始化好的webview：" + webView + " mAvailable:" + mAvailable.size());
            } else {
                addAndroidWebView();
                PWLog.e("WebViewPool get: 获取新建的webview：" + webView);
            }
            mInUse.add(webView);
        }
    }

    private void addX5WebView() {
        webView = new X5AgentWebView(new MutableContextWrapper(mContext));
        webView.loadAgentUrl(baseUrl);
        setting.setSetting(webView);
//        X5AgentWebView androidAgentWebView = (X5AgentWebView) webView;
//        androidAgentWebView.setwebviewc(WebClientHelper.getInstance().getWebViewClient());
//        androidAgentWebView.setWebChromeClient(WebClientHelper.getInstance().getWebChromeClient());
        if (javascriptInterface != null) {
            Object iJavascriptInterface = null;
            try {
                iJavascriptInterface = javascriptInterface.newInstance();
                PWLog.e("WebViewPool -> " + iJavascriptInterface.getClass().getName());
                javaBridgeMap.put(webView, iJavascriptInterface);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            webView.addJavascriptInterfaceAgent(javascriptInterface, jsName);//设置JavaScriptInterface
        }
        mCurrentSize++;
    }

    private void addAndroidWebView() {
        webView = new AndroidAgentWebView(new MutableContextWrapper(mContext));
        webView.loadAgentUrl(baseUrl);
        setting.setSetting(webView);
        AndroidAgentWebView androidAgentWebView = (AndroidAgentWebView) webView;
        androidAgentWebView.setWebViewClient(WebClientHelper.getInstance().getWebViewClient());
        androidAgentWebView.setWebChromeClient(WebClientHelper.getInstance().getWebChromeClient());

        if (javascriptInterface != null) {
            Object iJavascriptInterface = null;
            try {
                iJavascriptInterface = javascriptInterface.newInstance();
                PWLog.e("WebViewPool -> " + iJavascriptInterface.getClass().getName());
                javaBridgeMap.put(androidAgentWebView, iJavascriptInterface);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            webView.addJavascriptInterfaceAgent(iJavascriptInterface, jsName);//设置JavaScriptInterface
        }
        mCurrentSize++;
    }

    private boolean isResetWeb = false;

    public boolean isResetWeb() {
        return isResetWeb;
    }

    public IAgentWebView getUseWebView() {
        if (mInUse != null && mInUse.size() > 0) {
            return mInUse.get(0);
        }
        return null;
    }

    public List<IAgentWebView> getUseWebViews() {
        return mInUse;
    }

    @Override
    public synchronized void resetWebView(final IAgentWebView webView) {
        if (isX5Core) {
            synchronized (lock) {
                isResetWeb = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.clearHistoryAgent();//清空web view不去销毁它，放到最后，备用
                        webView.loadAgentUrl(baseUrl);
                    }
                }, 200);
                mX5InUse.remove(webView);
                if (mAvailable.size() < mPoolSize) {
                    mAvailable.add(webView);//复用上一个webView
                }
                mCurrentSize--;
            }
        } else {
            synchronized (lock) {
                isResetWeb = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WebClientHelper.getInstance().setNeedClearHistory(true);
                        webView.clearHistoryAgent();//清空web view不去销毁它，放到最后，备用
                        webView.loadAgentUrl(baseUrl);
                    }
                }, 200);
                mInUse.remove(webView);
                if (mAvailable.size() < mPoolSize) {
                    mAvailable.add(webView);//复用上一个webView
                }
                mCurrentSize--;
            }
        }
    }
}

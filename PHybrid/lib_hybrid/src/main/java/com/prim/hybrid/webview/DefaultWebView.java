package com.prim.hybrid.webview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.prim.hybrid.PHybrid;
import com.prim.hybrid.config.LoadWebConfig;
import com.prim.hybrid.entry.Configuration;
import com.prim.hybrid.entry.WebEntry;
import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.config.Configurator;
import com.prim.primweb.core.websetting.BaseAgentWebSetting;

import java.lang.reflect.InvocationTargetException;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认实现的WebView
 * @time 2/22/21 - 10:32 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class DefaultWebView implements IWebView {
    private Context context;
    private WebEntry webEntry;
    private ViewGroup viewGroup;
    private String url;
    private LoadWebConfig loadWebConfig;
    private PrimWeb primWeb;
    private String poolKey;

    /**
     * 构造方法
     *
     * @param context   上下文
     * @param webEntry  webview的配置
     * @param viewGroup 父view
     */
    public DefaultWebView(Context context, String poolKey, String url, WebEntry webEntry, ViewGroup viewGroup) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.context = context;
        this.webEntry = webEntry;
        this.viewGroup = viewGroup;
        this.poolKey = poolKey;
        this.url = url;
        // 加载webview
        loadWebConfig = new LoadWebConfig(context, webEntry);
        //只解析一遍即可 不用每次进来都要解析
        loadWebConfig.parseWebConfig();
        //框架初始化 配置
        PrimWeb.lazyInit(PHybrid.getInstance().getApplication(), false)
                .enableWebPool(webEntry != null && webEntry.isEnable())
                .setDebugLog(false)//LOG_DEBUG
                .withWebHost(url)
                .configure(new Configurator.OnX5CoreCallback() {
                    @Override
                    public void onCoreInitFinished() {

                    }

                    @Override
                    public void onInitFinished(boolean b) {
                        initWebView();
                    }
                });
    }

    @Override
    public void loadUrl(String url) {
        primWeb.getUrlLoader().loadUrl(url);
    }

    @Override
    public void initWebView() {
        // 配置webview
        if (webEntry != null && webEntry.isEnable()) {//缓存池没有初始化与要使用缓存池
            primWeb = PrimWeb
                    .withWebPool((Activity) context, PHybrid.getInstance().getApplication(), poolKey)
                    .setPoolWebSetting(loadWebConfig.getWebPoolSetting())
                    .setPoolJavaScriptBridge(loadWebConfig.getPoolJavaScriptBridgeClass(), webEntry != null ? webEntry.getPoolJavaScriptBridgeName() : "")
                    .setPoolWebClientListener(loadWebConfig.getWebClientListener())
                    .buildWebPool()
                    .setWebParent(viewGroup, 0)
                    .useCustomUI(loadWebConfig.getErrorLayout(), loadWebConfig.getLoadLayout(), loadWebConfig.getClickId())
                    .useDefaultTopIndicator(webEntry != null && webEntry.isEnableTopIndicator(),
                            loadWebConfig.getTopIndicatorColor() != null ? context.getResources().getColor(loadWebConfig.getTopIndicatorColor()) : -1)
                    .setWebViewType(PrimWeb.WebViewType.valueOf(webEntry != null ? webEntry.getWebViewType() : "Android"))
                    .isWebClient(false)//复用池无法使用自定义的webClient 需要进行监听
                    .setModeType(PrimWeb.ModeType.Strict)
                    .setListenerCheckJsFunction(loadWebConfig.getListenerCheckJsFunction())
                    .buildWeb()
                    .launch();
        } else {
            primWeb = PrimWeb
                    .with((Activity) context)
                    .setWebParent(viewGroup, 0)
                    .useCustomUI(loadWebConfig.getErrorLayout(), loadWebConfig.getLoadLayout(), loadWebConfig.getClickId())
                    .useDefaultTopIndicator(webEntry != null && webEntry.isEnableTopIndicator(),
                            loadWebConfig.getTopIndicatorColor() != null ? context.getResources().getColor(loadWebConfig.getTopIndicatorColor()) : -1)
                    .setWebViewType(PrimWeb.WebViewType.valueOf(webEntry != null ? webEntry.getWebViewType() : "Android"))
                    .setModeType(PrimWeb.ModeType.Strict)
                    .setWebSetting(loadWebConfig.getWebSettings())
                    .setWebViewClient(loadWebConfig.getWebViewClient())
                    .setWebChromeClient(loadWebConfig.getChromeClient())
                    .setListenerCheckJsFunction(loadWebConfig.getListenerCheckJsFunction())
                    .addJavascriptInterface(webEntry != null ? webEntry.getJavaScriptBridgeName() : "", loadWebConfig.getJavaScriptBridge())
                    .buildWeb()
                    .launch(url);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void callJS(String method, Object... params) {
        primWeb.getCallJsLoader().callJS(method, params);
    }

    @Override
    public void callJS(String method) {
        primWeb.getCallJsLoader().callJS(method);
    }

    @Override
    public void checkJsMethod(String method) {
        primWeb.getCallJsLoader().checkJsMethod(method);
    }

    @Override
    public View getWebView() {
        return primWeb.getRealWebView();
    }

    @Override
    public View getRootView() {
        return primWeb.getRootView();
    }

    @Override
    public boolean handlerBack() {
        return primWeb.handlerBack();
    }

    @Override
    public void onResume() {
        primWeb.webLifeCycle().onResume();
    }

    @Override
    public void onPause() {
        primWeb.webLifeCycle().onPause();
    }

    @Override
    public void onDestroy() {
        primWeb.webLifeCycle().onDestory();
    }
}

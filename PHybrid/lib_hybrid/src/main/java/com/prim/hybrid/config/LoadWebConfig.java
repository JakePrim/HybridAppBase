package com.prim.hybrid.config;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.prim.hybrid.entry.WebEntry;
import com.prim.hybrid.io.Resources;
import com.prim.primweb.core.jsloader.CommonJSListener;
import com.prim.primweb.core.webclient.webchromeclient.AgentChromeClient;
import com.prim.primweb.core.webclient.webviewclient.AgentWebViewClient;
import com.prim.primweb.core.websetting.BaseAgentWebSetting;
import com.prim.primweb.core.websetting.DefaultWebSetting;
import com.prim.primweb.core.webview.webpool.IJavascriptInterface;
import com.prim.primweb.core.webview.webpool.OnWebClientListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author prim
 * @version 1.0.0
 * @desc 加载webview配置，将配置转换
 * @time 2/22/21 - 11:49 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class LoadWebConfig {
    private WebEntry webEntry;

    private Context context;

    private BaseAgentWebSetting webSettings;

    private BaseAgentWebSetting webPoolSetting;

    private AgentWebViewClient webViewClient;

    private AgentChromeClient chromeClient;

    private CommonJSListener listenerCheckJsFunction;

    private Integer errorLayout;

    private Integer clickId;

    private Integer loadLayout;

    private Integer topIndicatorColor;

    private Object javaScriptBridge;

    private Class poolJavaScriptBridgeClass;

    private OnWebClientListener webClientListener;

    public LoadWebConfig(Context context, WebEntry webEntry) {
        this.context = context;
        this.webEntry = webEntry;
        webSettings = new DefaultWebSetting(context);
    }

    private static final String TAG = "LoadWebConfig";

    public LoadWebConfig parseWebConfig() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (webEntry == null) {
            return this;
        }
        String webSetting = webEntry.getWebSetting();
        if (!TextUtils.isEmpty(webSetting)) {
            Class<?> webSettingClass = Class.forName(webSetting);
            //判断 webSettingClass是否继承BaseAgentWebSetting
            if (webSettingClass != null && BaseAgentWebSetting.class.isAssignableFrom(webSettingClass)) {
                Constructor<?> constructor = webSettingClass.getConstructor(Context.class);
                webSettings = (BaseAgentWebSetting) constructor.newInstance(context);
            }
        }
        String webPoolSettingName = webEntry.getWebPoolSetting();
        if (!TextUtils.isEmpty(webPoolSettingName)) {
            Class<?> webSettingClass = Class.forName(webPoolSettingName);
            //判断 webSettingClass是否继承BaseAgentWebSetting
            if (webSettingClass != null && BaseAgentWebSetting.class.isAssignableFrom(webSettingClass)) {
                Constructor<?> constructor = webSettingClass.getConstructor(Context.class);
                webPoolSetting = (BaseAgentWebSetting) constructor.newInstance(context);
            }
        }

        String webViewClientName = webEntry.getWebViewClient();
        if (!TextUtils.isEmpty(webViewClientName)) {
            Class<?> webViewClientClass = Class.forName(webViewClientName);
            if (webViewClientClass != null && AgentWebViewClient.class.isAssignableFrom(webViewClientClass)) {
                webViewClient = (AgentWebViewClient) webViewClientClass.newInstance();
            }
        }

        String chromeClientName = webEntry.getWebChromeClient();
        if (!TextUtils.isEmpty(chromeClientName)) {
            Class<?> chromeClientClass = Class.forName(chromeClientName);
            if (chromeClientClass != null && AgentChromeClient.class.isAssignableFrom(chromeClientClass)) {
                chromeClient = (AgentChromeClient) chromeClientClass.newInstance();
            }
        }

        String listenerCheckJsFunctionName = webEntry.getListenerCheckJsFunction();
        if (!TextUtils.isEmpty(listenerCheckJsFunctionName)) {
            Class<?> listenerCheckJsFunctionClass = Class.forName(listenerCheckJsFunctionName);
            if (listenerCheckJsFunctionClass != null && CommonJSListener.class.isAssignableFrom(listenerCheckJsFunctionClass)) {
                this.listenerCheckJsFunction = (CommonJSListener) listenerCheckJsFunctionClass.newInstance();
            }
        }

        if (!TextUtils.isEmpty(webEntry.getErrorLayout())) {
            errorLayout = Resources.getLayoutResource(context, webEntry.getErrorLayout());
        }

        if (!TextUtils.isEmpty(webEntry.getLoadLayout())) {
            loadLayout = Resources.getLayoutResource(context, webEntry.getLoadLayout());
        }


        if (!TextUtils.isEmpty(webEntry.getClickId())) {
            clickId = Resources.getIdResource(context, webEntry.getClickId());
        }

        if (!TextUtils.isEmpty(webEntry.getTopIndicatorColorInt())) {
            topIndicatorColor = Resources.getColorResource(context, webEntry.getTopIndicatorColorInt());
        }

        String javaScriptBridgeClassName = webEntry.getJavaScriptBridgeClass();
        if (!TextUtils.isEmpty(javaScriptBridgeClassName)) {
            Class<?> javaScriptBridgeClass = Class.forName(javaScriptBridgeClassName);
            javaScriptBridge = javaScriptBridgeClass.newInstance();
        }

        String poolJavaScriptBridgeClassName = webEntry.getPoolJavaScriptBridgeClass();
        if (!TextUtils.isEmpty(poolJavaScriptBridgeClassName)) {
            Class<?> poolClass = Class.forName(poolJavaScriptBridgeClassName);
            if (poolClass != null && IJavascriptInterface.class.isAssignableFrom(poolClass)) {
                poolJavaScriptBridgeClass = poolClass;
            }
        }

        String webClientListenerName = webEntry.getWebClientListener();
        if (!TextUtils.isEmpty(webClientListenerName)) {
            Class<?> aClass = Class.forName(webClientListenerName);
            if (aClass != null && OnWebClientListener.class.isAssignableFrom(aClass)) {
                webClientListener = (OnWebClientListener) aClass.newInstance();
            }
        }

        return this;
    }

    public WebEntry getWebEntry() {
        return webEntry;
    }

    public void setWebEntry(WebEntry webEntry) {
        this.webEntry = webEntry;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public BaseAgentWebSetting getWebSettings() {
        return webSettings;
    }

    public void setWebSettings(BaseAgentWebSetting webSettings) {
        this.webSettings = webSettings;
    }

    public BaseAgentWebSetting getWebPoolSetting() {
        return webPoolSetting;
    }

    public void setWebPoolSetting(BaseAgentWebSetting webPoolSetting) {
        this.webPoolSetting = webPoolSetting;
    }

    public AgentWebViewClient getWebViewClient() {
        return webViewClient;
    }

    public void setWebViewClient(AgentWebViewClient webViewClient) {
        this.webViewClient = webViewClient;
    }

    public AgentChromeClient getChromeClient() {
        return chromeClient;
    }

    public void setChromeClient(AgentChromeClient chromeClient) {
        this.chromeClient = chromeClient;
    }

    public CommonJSListener getListenerCheckJsFunction() {
        return listenerCheckJsFunction;
    }

    public void setListenerCheckJsFunction(CommonJSListener listenerCheckJsFunction) {
        this.listenerCheckJsFunction = listenerCheckJsFunction;
    }

    public Integer getErrorLayout() {
        return errorLayout;
    }

    public void setErrorLayout(Integer errorLayout) {
        this.errorLayout = errorLayout;
    }

    public Integer getClickId() {
        return clickId;
    }

    public void setClickId(Integer clickId) {
        this.clickId = clickId;
    }

    public Integer getLoadLayout() {
        return loadLayout;
    }

    public void setLoadLayout(Integer loadLayout) {
        this.loadLayout = loadLayout;
    }

    public Integer getTopIndicatorColor() {
        return topIndicatorColor;
    }

    public void setTopIndicatorColor(Integer topIndicatorColor) {
        this.topIndicatorColor = topIndicatorColor;
    }

    public Object getJavaScriptBridge() {
        return javaScriptBridge;
    }

    public void setJavaScriptBridge(Object javaScriptBridge) {
        this.javaScriptBridge = javaScriptBridge;
    }

    public Class getPoolJavaScriptBridgeClass() {
        return poolJavaScriptBridgeClass;
    }

    public void setPoolJavaScriptBridgeClass(Class poolJavaScriptBridgeClass) {
        this.poolJavaScriptBridgeClass = poolJavaScriptBridgeClass;
    }

    public OnWebClientListener getWebClientListener() {
        return webClientListener;
    }

    public void setWebClientListener(OnWebClientListener webClientListener) {
        this.webClientListener = webClientListener;
    }
}

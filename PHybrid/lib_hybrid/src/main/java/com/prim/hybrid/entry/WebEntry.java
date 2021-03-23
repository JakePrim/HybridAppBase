package com.prim.hybrid.entry;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc webview 配置实体类
 * @time 2/22/21 - 10:20 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class WebEntry implements Serializable {

    /**
     * 标记为哪个功能唯一值，的webview配置
     */
    private String namespace;

    /**
     * 自定义实现webview容器的 全类名必须实现IWebView
     */
    private String container;

    /**
     * webviewSetting的全类名
     */
    private String webSetting;

    private String webChromeClient;

    private String webViewClient;

    private String webViewType;

    private String listenerCheckJsFunction;

    private String errorLayout;

    private String clickId;

    private String loadLayout;

    private boolean enableTopIndicator;

    private String topIndicatorColorInt;

    private String topIndicatorHeight;

    private String javaScriptBridgeClass;

    private String javaScriptBridgeName;


    //-------------------- 复用池配置 -------------------------//

    /**
     * 是否开启复用池
     */
    private boolean enable;

    private String webPoolSetting;

    private Integer size;

    private String poolJavaScriptBridgeClass;

    private String poolJavaScriptBridgeName;

    private String webClientListener;

    private String webChromeClientListener;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getWebSetting() {
        return webSetting;
    }

    public void setWebSetting(String webSetting) {
        this.webSetting = webSetting;
    }

    public String getWebChromeClient() {
        return webChromeClient;
    }

    public void setWebChromeClient(String webChromeClient) {
        this.webChromeClient = webChromeClient;
    }

    public String getWebViewClient() {
        return webViewClient;
    }

    public void setWebViewClient(String webViewClient) {
        this.webViewClient = webViewClient;
    }

    public String getWebViewType() {
        return webViewType;
    }

    public void setWebViewType(String webViewType) {
        this.webViewType = webViewType;
    }

    public String getListenerCheckJsFunction() {
        return listenerCheckJsFunction;
    }

    public void setListenerCheckJsFunction(String listenerCheckJsFunction) {
        this.listenerCheckJsFunction = listenerCheckJsFunction;
    }

    public String getErrorLayout() {
        return errorLayout;
    }

    public void setErrorLayout(String errorLayout) {
        this.errorLayout = errorLayout;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public String getLoadLayout() {
        return loadLayout;
    }

    public void setLoadLayout(String loadLayout) {
        this.loadLayout = loadLayout;
    }

    public boolean isEnableTopIndicator() {
        return enableTopIndicator;
    }

    public void setEnableTopIndicator(boolean enableTopIndicator) {
        this.enableTopIndicator = enableTopIndicator;
    }

    public String getTopIndicatorColorInt() {
        return topIndicatorColorInt;
    }

    public void setTopIndicatorColorInt(String topIndicatorColorInt) {
        this.topIndicatorColorInt = topIndicatorColorInt;
    }

    public String getTopIndicatorHeight() {
        return topIndicatorHeight;
    }

    public void setTopIndicatorHeight(String topIndicatorHeight) {
        this.topIndicatorHeight = topIndicatorHeight;
    }

    public String getJavaScriptBridgeClass() {
        return javaScriptBridgeClass;
    }

    public void setJavaScriptBridgeClass(String javaScriptBridgeClass) {
        this.javaScriptBridgeClass = javaScriptBridgeClass;
    }

    public String getJavaScriptBridgeName() {
        return javaScriptBridgeName;
    }

    public void setJavaScriptBridgeName(String javaScriptBridgeName) {
        this.javaScriptBridgeName = javaScriptBridgeName;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getWebPoolSetting() {
        return webPoolSetting;
    }

    public void setWebPoolSetting(String webPoolSetting) {
        this.webPoolSetting = webPoolSetting;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPoolJavaScriptBridgeClass() {
        return poolJavaScriptBridgeClass;
    }

    public void setPoolJavaScriptBridgeClass(String poolJavaScriptBridgeClass) {
        this.poolJavaScriptBridgeClass = poolJavaScriptBridgeClass;
    }

    public String getPoolJavaScriptBridgeName() {
        return poolJavaScriptBridgeName;
    }

    public void setPoolJavaScriptBridgeName(String poolJavaScriptBridgeName) {
        this.poolJavaScriptBridgeName = poolJavaScriptBridgeName;
    }

    public String getWebClientListener() {
        return webClientListener;
    }

    public void setWebClientListener(String webClientListener) {
        this.webClientListener = webClientListener;
    }

    public String getWebChromeClientListener() {
        return webChromeClientListener;
    }

    public void setWebChromeClientListener(String webChromeClientListener) {
        this.webChromeClientListener = webChromeClientListener;
    }

    @Override
    public String toString() {
        return "WebEntry{" +
                "namespace='" + namespace + '\'' +
                ", container='" + container + '\'' +
                ", webSetting='" + webSetting + '\'' +
                ", webChromeClient='" + webChromeClient + '\'' +
                ", webViewClient='" + webViewClient + '\'' +
                ", webViewType='" + webViewType + '\'' +
                ", listenerCheckJsFunction='" + listenerCheckJsFunction + '\'' +
                ", errorLayout='" + errorLayout + '\'' +
                ", clickId='" + clickId + '\'' +
                ", loadLayout='" + loadLayout + '\'' +
                ", enableTopIndicator=" + enableTopIndicator +
                ", topIndicatorColorInt='" + topIndicatorColorInt + '\'' +
                ", topIndicatorHeight='" + topIndicatorHeight + '\'' +
                ", javaScriptBridgeClass='" + javaScriptBridgeClass + '\'' +
                ", javaScriptBridgeName='" + javaScriptBridgeName + '\'' +
                ", enable=" + enable +
                ", webPoolSetting='" + webPoolSetting + '\'' +
                ", size=" + size +
                ", poolJavaScriptBridgeClass='" + poolJavaScriptBridgeClass + '\'' +
                ", poolJavaScriptBridgeName='" + poolJavaScriptBridgeName + '\'' +
                ", webClientListener='" + webClientListener + '\'' +
                ", webChromeClientListener='" + webChromeClientListener + '\'' +
                '}';
    }
}

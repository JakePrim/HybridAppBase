package com.prim.primweb.core.jsinterface;

import android.util.Log;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.webview.IAgentWebView;

import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/15 0015
 * 描    述：安全的注入js脚本
 * 修订历史：
 * ================================================
 */
public class SafeJsInterface extends BaseJsInterface {

    private PrimWeb.ModeType modeType;
    private IAgentWebView webView;

    private static final String TAG = "SafeJsInterface";

    public static SafeJsInterface getInstance(IAgentWebView webView, PrimWeb.ModeType modeType) {
        return new SafeJsInterface(modeType, webView);
    }

    public SafeJsInterface(PrimWeb.ModeType modeType, IAgentWebView webView) {
        super(modeType);
        this.modeType = modeType;
        this.webView = webView;
    }

    @Override
    public IJsInterface addJavaObjects(Map<String, Object> maps) {
        Set<Map.Entry<String, Object>> entries = maps.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object value = entry.getValue();
            try {
                webView.addJavascriptInterfaceAgent(value, entry.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public IJsInterface addJavaObject(Object o, String name) {
        try {
            webView.addJavascriptInterfaceAgent(o, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public IJsInterface removeJavaObject(String name) {
        try {
            webView.removeJavascriptInterfaceAgent(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 构建一个“不会重复注入”的js脚本；
     *
     * @param key
     * @param js
     * @return
     */
    public String buildNotRepeatInjectJS(String key, String js) {
        String obj = String.format("__injectFlag_%1$s__", key);
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:try{(function(){if(window.");
        sb.append(obj);
        sb.append("){console.log('");
        sb.append(obj);
        sb.append(" has been injected');return;}window.");
        sb.append(obj);
        sb.append("=true;");
        sb.append(js);
        sb.append("}())}catch(e){console.warn(e)}");
        return sb.toString();
    }

    /**
     * 构建一个“带try catch”的js脚本；
     *
     * @param js
     * @return
     */
    public String buildTryCatchInjectJS(String js) {
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:try{");
        sb.append(js);
        sb.append("}catch(e){console.warn(e)}");
        return sb.toString();
    }
}

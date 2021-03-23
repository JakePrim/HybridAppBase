package com.prim.hybrid.webview;

import android.view.View;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 4:14 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public interface IWebView {

    /**
     * 加载网页
     * @param url
     */
    void loadUrl(String url);

    void initWebView();

    void initListener();

    /**
     * 加载js方法
     * @param method
     * @param params
     */
    void callJS(String method, Object... params);

    void callJS(String method);

    /**
     * 检查js方法是否存在，注意在监听类中监听
     * @param method
     */
    void checkJsMethod(String method);

    /**
     * 获取webview实例
     * @return
     */
    View getWebView();

    View getRootView();

    boolean handlerBack();

    void onResume();

    void onPause();

    void onDestroy();
}

package com.prim.primweb.core.jsloader;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：js方法加载器 接口
 * 修订历史：
 * ================================================
 */
public interface ICallJsLoader {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    void callJs(String method, AgentValueCallback<String> callback, Object... params);

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    void callJs(String method, AgentValueCallback<String> callback);

    void callJS(String method, Object... params);

    void callJS(String method);

    void checkJsMethod(String method);
}

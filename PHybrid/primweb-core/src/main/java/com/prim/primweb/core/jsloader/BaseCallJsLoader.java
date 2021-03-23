package com.prim.primweb.core.jsloader;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.prim.primweb.core.webview.IAgentWebView;
import com.prim.primweb.core.utils.PrimWebUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/14 0014
 * 描    述：js方法加载器的基类
 * 修订历史：
 * ================================================
 */
public abstract class BaseCallJsLoader implements ICallJsLoader {
    private IAgentWebView webView;

    private static final String TAG = "BaseCallJsLoader";


    BaseCallJsLoader(IAgentWebView webView) {
        this.webView = webView;
    }

    protected void call(String js, AgentValueCallback<String> callback) {
//        showLogCompletion(js,4000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.evaluateJs(js, callback);
        } else {
            this.loadJs(js);
        }
    }

    private void loadJs(final String js) {
        webView.loadAgentJs(js);
    }

    private void evaluateJs(final String js, final AgentValueCallback<String> callback) {
        webView.loadAgentJs(js, callback);
    }

    @Override
    public void callJs(String method, AgentValueCallback<String> callback, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append("javascript:").append(method);
        if (params == null || params.length == 0) {
            sb.append("()");
        } else {
            sb.append("(").append(splice(params));
        }
        call(sb.toString(), callback);
    }

    @Override
    public void callJs(String method, AgentValueCallback<String> callback) {
        this.callJs(method, callback, (Object[]) null);
    }

    @Override
    public void callJS(String method, Object... params) {
        this.callJs(method, null, params);
    }

    @Override
    public void callJS(String method) {
        this.callJS(method, (Object[]) null);
    }

    /** 拼接参数 */
    private CharSequence splice(Object... params) {
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            if (param instanceof String) {
                sb.append("'").append(param).append("'");
            } else {
                sb.append(param);
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
//        showLogCompletion(sb.toString(),4000);
        return sb.toString();
    }

    /** 切割json字符串,append 不能传大量的字符串 */
    private void CutString(String source, int cutcount, List<String> data) {
        // 当前字符串长度
        int size = source.length();
        // 取出正常部分
        int count = size / cutcount;
        for (int j = 0; j < count; j++) {
            String s = source.substring(j * cutcount, j * cutcount + cutcount);
            data.add(s);
        }
        // 取出尾部
        int i = size % cutcount;
        String str = source.substring(size - i, size);
        if (!TextUtils.isEmpty(str)) {
            data.add(str);
        }
    }

    private void showLogCompletion(String log, int showCount) {
        if (log.length() > showCount) {
            String show = log.substring(0, showCount);
            Log.i("TAG-PRIM CONSOLE", show + "");
            if ((log.length() - showCount) > showCount) {//剩下的文本还是大于规定长度
                String partLog = log.substring(showCount, log.length());
                showLogCompletion(partLog, showCount);
            } else {
                String surplusLog = log.substring(showCount, log.length());
                Log.i("TAG-PRIM CONSOLE", surplusLog + "");
            }
        } else {
            Log.i("TAG-PRIM CONSOLE", log + "");
        }
    }

    @Override
    public void checkJsMethod(String method) {
        StringBuilder sb = new StringBuilder();
        sb.append("function checkJsFunction(){ if(typeof ")
                .append(method)
                .append(" != \"undefined\" && typeof ")
                .append(method)
                .append(" == \"function\")")
                .append("{console.log(\"")
                .append(method)
                .append("\");")
                .append("checkJsBridge['jsFunctionExit']")
                .append("('")
                .append(method)
                .append("');")
                .append("}else{")
                .append("if(typeof checkJsBridge == \"undefined\") return false;")
                .append("checkJsBridge['jsFunctionNo']('")
                .append(method)
                .append("');}}");
        call("javascript:" + sb.toString() + ";checkJsFunction()", null);
    }
}

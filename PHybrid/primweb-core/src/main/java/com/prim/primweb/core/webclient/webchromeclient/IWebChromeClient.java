package com.prim.primweb.core.webclient.webchromeclient;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.prim.primweb.core.webclient.callback.CustomViewCallback;
import com.prim.primweb.core.webclient.callback.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/19 0019
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IWebChromeClient {
    void onReceivedTitle(View webView, String s);

    void onReceivedIcon(View webView, Bitmap bitmap);

    void onReceivedTouchIconUrl(View webView, String s, boolean b);

    void onRequestFocus(View webView);

    void onGeolocationPermissionsHidePrompt();

    void openFileChooser(ValueCallback<Uri> valueCallback);

    void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType);

    void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture);

    boolean onShowFileChooser(View webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams);

    boolean onShowFileChooser(View webView, ValueCallback<Uri[]> valueCallback, com.tencent.smtt.sdk.WebChromeClient.FileChooserParams fileChooserParams);

    void onProgressChanged(View webView, int newProgress);

    boolean onJsTimeout();

    void onHideCustomView();

    boolean onJsAlert(View webView, String s, String s1, JsResult result);

    boolean onJsAlert(View webView, String s, String s1, android.webkit.JsResult result);

    boolean onJsConfirm(View webView, String s, String s1, JsResult result);

    boolean onJsConfirm(View webView, String s, String s1, android.webkit.JsResult result);

    boolean onJsPrompt(View webView, String s, String s1, String s2, JsPromptResult result);

    boolean onJsPrompt(View webView, String s, String s1, String s2, android.webkit.JsPromptResult result);

    boolean onJsBeforeUnload(View webView, String s, String s1, JsResult result);

    boolean onJsBeforeUnload(View webView, String s, String s1, android.webkit.JsResult result);

    void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback);

    void onShowCustomView(View view, CustomViewCallback callback);

    void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback);

    Bitmap getDefaultVideoPoster();

    View getVideoLoadingProgressView();

    void getVisitedHistory(ValueCallback<String[]> callback);

    boolean onCreateWindow(View view, boolean isDialog, boolean isUserGesture, Message resultMsg);

    void onCloseWindow(View window);

    boolean onConsoleMessage(ConsoleMessage consoleMessage);

    boolean onConsoleMessage(android.webkit.ConsoleMessage consoleMessage);
}

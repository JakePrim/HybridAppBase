package com.prim.primweb.core.webclient.webchromeclient;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import com.prim.primweb.core.webclient.callback.CustomViewCallback;
import com.prim.primweb.core.webclient.callback.GeolocationPermissionsCallback;
import com.prim.primweb.core.webclient.webviewclient.IWebViewClient;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/21 0021
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class AgentChromeClient implements IWebChromeClient {
    @Override
    public void onProgressChanged(View webView, int newProgress) {

    }

    @Override
    public void onReceivedTitle(View webView, String s) {

    }

    @Override
    public void onReceivedIcon(View webView, Bitmap bitmap) {

    }

    @Override
    public boolean onShowFileChooser(View webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return false;
    }

    @Override
    public boolean onShowFileChooser(View webView, ValueCallback<Uri[]> valueCallback, com.tencent.smtt.sdk.WebChromeClient.FileChooserParams fileChooserParams) {
        return false;
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
        geolocationPermissionsCallback.invoke(s, true, true);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {

    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {

    }

    @Override
    public boolean onJsBeforeUnload(View webView, String s, String s1, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsBeforeUnload(View webView, String s, String s1, android.webkit.JsResult result) {
        return false;
    }

    @Override
    public boolean onJsPrompt(View webView, String s, String s1, String s2, JsPromptResult result) {
        return false;
    }

    @Override
    public boolean onJsPrompt(View webView, String s, String s1, String s2, android.webkit.JsPromptResult result) {
        return false;
    }

    @Override
    public boolean onJsConfirm(View webView, String s, String s1, android.webkit.JsResult result) {
        return false;
    }

    @Override
    public boolean onJsConfirm(View webView, String s, String s1, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsAlert(View webView, String s, String s1, android.webkit.JsResult result) {
        return false;
    }

    @Override
    public boolean onJsAlert(View webView, String s, String s1, JsResult result) {
        return false;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return false;
    }

    @Override
    public boolean onConsoleMessage(android.webkit.ConsoleMessage consoleMessage) {
        return false;
    }

    @Override
    public boolean onCreateWindow(View view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return false;
    }

    @Override
    public void onCloseWindow(View window) {

    }

    @Override
    public boolean onJsTimeout() {
        return true;
    }

    @Override
    public void onHideCustomView() {

    }

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {

    }

    @Override
    public void onReceivedTouchIconUrl(View webView, String s, boolean b) {

    }

    @Override
    public void onRequestFocus(View webView) {

    }

    @Override
    public void openFileChooser(ValueCallback<Uri> valueCallback) {

    }

    @Override
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType) {

    }

    @Override
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        valueCallback.onReceiveValue(null);
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    @Override
    public View getVideoLoadingProgressView() {
        return null;
    }

    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {

    }
}

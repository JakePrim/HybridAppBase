package com.prim.primweb.core.webclient.webchromeclient;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;

import com.prim.primweb.core.PrimWeb;
import com.prim.primweb.core.file.FileChooser;
import com.prim.primweb.core.permission.FilePermissionWrap;
import com.prim.primweb.core.permission.PermissionMiddleActivity;
import com.prim.primweb.core.permission.WebPermission;
import com.prim.primweb.core.uicontroller.IndicatorController;
import com.prim.primweb.core.webclient.PrimChromeClient;
import com.prim.primweb.core.webview.IAgentWebView;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：6/21 0021
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DefaultAndroidChromeClient extends BaseAndroidChromeClient {
    private IndicatorController indicatorController;

    private WeakReference<Activity> mActivity;

    private PrimChromeClient.Builder builder;

    public DefaultAndroidChromeClient(PrimChromeClient.Builder builder) {
        this.builder = builder;
        this.indicatorController = builder.indicatorController;
        mActivity = new WeakReference<Activity>(builder.activity);
    }

    @Override
    public void setChromeClient(WebChromeClient delegate) {
        super.setChromeClient(delegate);
    }

    @Override
    public void setChromeClient(AgentChromeClient chromeClient, IAgentWebView webView) {
        super.setChromeClient(chromeClient, webView);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (indicatorController != null) {
            indicatorController.progress(newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
        if (!builder.isGeolocation) {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
            return;
        }
        if (mActivity == null || mActivity.get() == null) {
            callback.invoke(origin, false, false);
            return;
        }
        PermissionMiddleActivity.setPermissionListener(new PermissionMiddleActivity.PermissionListener() {
            @Override
            public void requestPermissionSuccess(String permissionType) {
                callback.invoke(origin, true, false);
            }

            @Override
            public void requestPermissionFailed(String permissionType) {
                callback.invoke(origin, false, false);
            }
        });
        PermissionMiddleActivity.startCheckPermission(mActivity.get(), WebPermission.LOCATION_TYPE);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    @Override
    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        if (!builder.allowUploadFile) {
            super.openFileChooser(valueCallback);
            return;
        }
        super.openFileChooser(valueCallback);
        FilePermissionWrap filePermissionWrap = new FilePermissionWrap(valueCallback);
        fileChooser(filePermissionWrap);
    }

    //  Android  >= 3.0
    @Override
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType) {
        if (!builder.allowUploadFile) {
            super.openFileChooser(valueCallback, acceptType);
            return;
        }
        super.openFileChooser(valueCallback, acceptType);
        FilePermissionWrap filePermissionWrap = new FilePermissionWrap(valueCallback, acceptType);
        fileChooser(filePermissionWrap);
    }

    // Android  >= 4.1
    @Override
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        if (!builder.allowUploadFile) {
            super.openFileChooser(valueCallback, acceptType, capture);
            return;
        }
        super.openFileChooser(valueCallback, acceptType, capture);
        FilePermissionWrap filePermissionWrap = new FilePermissionWrap(valueCallback, acceptType);
        fileChooser(filePermissionWrap);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (!builder.allowUploadFile) {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
        super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        FilePermissionWrap filePermissionWrap = new FilePermissionWrap(null, filePathCallback, fileChooserParams.getAcceptTypes());
        fileChooser(filePermissionWrap);
        return true;
    }

    /** 选择文件上传 */
    private void fileChooser(FilePermissionWrap filePermissionWrap) {
        if (mActivity != null && mActivity.get() != null) {
            new FileChooser(filePermissionWrap, mActivity.get()).updateFile(builder.invokingThird);
        }
    }
}

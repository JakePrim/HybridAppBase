package com.prim.primweb.core.permission;

import android.net.Uri;
import android.webkit.ValueCallback;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/17 0017
 * 描    述：上传文件的包装类,包括
 * 修订历史：
 * ================================================
 */
public class FilePermissionWrap implements Serializable {

    //文件的类型
    public String[] acceptType;

    public ValueCallback<Uri> valueCallback;

    public ValueCallback<Uri[]> valueCallbacks;

    public FilePermissionWrap(ValueCallback<Uri> valueCallback, ValueCallback<Uri[]> valueCallbacks,String... acceptType) {
        this.acceptType = acceptType;
        this.valueCallback = valueCallback;
        this.valueCallbacks = valueCallbacks;
    }

    public FilePermissionWrap(ValueCallback<Uri> valueCallback,String... acceptType) {
        this.acceptType = acceptType;
        this.valueCallback = valueCallback;
    }

    public FilePermissionWrap(ValueCallback<Uri> valueCallback) {
        this.valueCallback = valueCallback;
    }

    public ValueCallback<Uri> getValueCallback() {
        return valueCallback;
    }

    public void setValueCallback(ValueCallback<Uri> valueCallback) {
        this.valueCallback = valueCallback;
    }

    public ValueCallback<Uri[]> getValueCallbacks() {
        return valueCallbacks;
    }

    public void setValueCallbacks(ValueCallback<Uri[]> valueCallbacks) {
        this.valueCallbacks = valueCallbacks;
    }
}

package com.prim.primweb.core.listener;

/**
 * @author prim
 * @version 1.0.0
 * @desc Web下载监听
 * @time 2019/1/17 - 4:45 PM
 */
public interface OnDownloadListener {
    void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength);
}

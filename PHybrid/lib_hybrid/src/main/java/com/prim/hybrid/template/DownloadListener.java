package com.prim.hybrid.template;

import java.io.File;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/23/21 - 4:13 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public interface DownloadListener {
    /**
     * 下载
     *
     * @param url          下载地址
     * @param downloadPath 下载的文件路径
     * @param callback     下载的回调
     */
    void download(String url, String downloadPath, DownloadCallback callback);
}

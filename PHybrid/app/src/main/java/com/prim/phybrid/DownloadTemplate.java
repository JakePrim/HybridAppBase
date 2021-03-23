package com.prim.phybrid;

import com.prim.hybrid.template.DownloadCallback;
import com.prim.hybrid.template.DownloadListener;

import java.util.zip.CRC32;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/23/21 - 4:33 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class DownloadTemplate implements DownloadListener {
    @Override
    public void download(String url, String downloadPath, DownloadCallback callback) {
        callback.onSuccess();
    }
}

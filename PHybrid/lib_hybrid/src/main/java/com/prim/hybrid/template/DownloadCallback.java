package com.prim.hybrid.template;

import java.io.File;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/23/21 - 4:14 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public interface DownloadCallback {
    void onStarted();

    void onLoading(long total, long current, boolean isDownloading);

    void onSuccess();

    void onError(Throwable ex);
}

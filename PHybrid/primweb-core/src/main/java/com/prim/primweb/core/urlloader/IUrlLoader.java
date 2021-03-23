package com.prim.primweb.core.urlloader;

import java.util.Map;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：5/15 0015
 * 描    述：Url加载器接口
 * 修订历史：
 * ================================================
 */
public interface IUrlLoader {
    void loadUrl(String url);

    void loadUrl(String url, Map<String, String> headers);

    void reload();

    void stopLoading();

    void postUrl(String url, byte[] params);

    void loadData(String data, String mimeType, String encoding);

    void loadDataWithBaseURL(String baseUrl, String data,
                             String mimeType, String encoding, String historyUrl);
}

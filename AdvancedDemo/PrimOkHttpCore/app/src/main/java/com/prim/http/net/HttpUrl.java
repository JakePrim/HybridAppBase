package com.prim.http.net;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求的URL的封装
 * @time 2019-06-24 - 07:16 这
 */
public class HttpUrl {
    private String host;
    private String file;
    private String protocol;
    private int port;

    public HttpUrl(String url) throws MalformedURLException {
        URL urls = new URL(url);
        host = urls.getHost();//host
        file = urls.getFile();// /query?.....
        file = TextUtils.isEmpty(file) ? "/" : file;
        protocol = urls.getProtocol();//http/https
        port = urls.getPort();//端口 如：80
        port = port == -1 ? urls.getDefaultPort() : port;
    }

    public String getHost() {
        return host;
    }

    public String getFile() {
        return file;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }
}

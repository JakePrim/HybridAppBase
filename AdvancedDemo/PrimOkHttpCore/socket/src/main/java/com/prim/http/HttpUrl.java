package com.prim.http;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-06-24 - 07:16
 */
public class HttpUrl {
    private String host;
    private String file;
    private String protocol;
    private int port;

    public HttpUrl(String url) {
        try {
            URL urls = new URL(url);
            host = urls.getHost();
            file = urls.getFile();
            protocol = urls.getProtocol();
            port = urls.getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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

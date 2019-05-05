package cn.prim.http.lib_net.model;

import java.io.Serializable;
import java.io.StringReader;
import java.util.LinkedHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求头的包装类
 * @time 2019/1/2 - 2:55 PM
 */
public class HttpHeaders implements Serializable {
    private static final long serialVersionUID = 7369819159227055048L;
    public LinkedHashMap<String, String> commonHeaders;

    public HttpHeaders() {
        init();
    }

    public HttpHeaders(String key, String value) {
        init();
        commonHeaders.put(key, value);
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            commonHeaders.put(key, value);
        }
    }

    public void put(HttpHeaders httpHeaders) {
        if (httpHeaders != null) {
            if (httpHeaders.commonHeaders != null && !httpHeaders.commonHeaders.isEmpty()) {
                commonHeaders.putAll(httpHeaders.commonHeaders);
            }
        }
    }

    public void remove(String key) {
        commonHeaders.remove(key);
    }

    public void clear() {
        commonHeaders.clear();
    }

    private void init() {
        commonHeaders = new LinkedHashMap<>();
    }
}

package com.prim.http.net;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求的包装类 主要包括请求头和请求体以及请求的URL
 * @time 2019-06-24 - 07:04
 */
public class Request {
    //请求头
    private Map<String, String> headers;
    //请求方式 GET/POST
    private Method method;
    //请求URL
    private HttpUrl url;
    //请求体 post请求方式需要用到
    private RequestBody body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Method getMethod() {
        return method;
    }

    public HttpUrl getUrl() {
        return url;
    }

    public RequestBody getBody() {
        return body;
    }

    public Request(Builder builder) {
        this.headers = builder.headers;
        this.method = builder.method;
        this.url = builder.url;
        this.body = builder.body;
    }

    public final static class Builder {
        //请求头
        Map<String, String> headers = new HashMap<>();
        //请求方式 GET/POST
        Method method = Method.GET;
        HttpUrl url;
        RequestBody body;

        public Builder url(String url) {
            try {
                this.url = new HttpUrl(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder addHeader(String name, String value) {
            headers.put(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Builder get() {
            method = Method.GET;
            return this;
        }

        public Builder post(RequestBody body) {
            this.body = body;
            method = Method.POST;
            return this;
        }

        public Request build() {
            if (url == null) {
                throw new IllegalStateException("HttpUrl this is url,not null");
            }

            return new Request(this);
        }
    }
}

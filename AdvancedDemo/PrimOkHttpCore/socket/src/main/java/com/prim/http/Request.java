package com.prim.http;

import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求 主要包括请求头和请求体 请求的URL
 * @time 2019-06-24 - 07:04
 */
public class Request {
    //请求头
    private Map<String,String> headers;
    //请求方式 GET/POST
    private String method;
    //请求URL
    private HttpUrl url;
    //请求体
    private RequestBody body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public HttpUrl getUrl() {
        return url;
    }

    public RequestBody getBody() {
        return body;
    }

    public void Request(Builder builder){
        this.headers = builder.headers;
        this.method = builder.method;
        this.url = builder.url;
        this.body = builder.body;
    }

    public class Builder{
        //请求头
        public Map<String,String> headers;
        //请求方式 GET/POST
        public String method;
        public HttpUrl url;
        public RequestBody body;
    }
}

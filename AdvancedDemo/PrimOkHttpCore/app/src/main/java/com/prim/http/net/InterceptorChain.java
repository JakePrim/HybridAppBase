package com.prim.http.net;


import java.io.IOException;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-14 - 18:02
 */
public class InterceptorChain {
    List<Interceptor> interceptors;
    int index;
    Call call;

    HttpConnection httpConnection;

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call) {
        this(interceptors, index, call, null);
    }

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call, HttpConnection httpConnection) {
        this.interceptors = interceptors;
        this.index = index;
        this.call = call;
        this.httpConnection = httpConnection;
    }

    /**
     * 使下一个拦截器拿到HttpConnection
     *
     * @param connection
     * @return
     */
    public Response process(HttpConnection connection) throws IOException {
        this.httpConnection = connection;
        return process();
    }

    /**
     * 执行拦截器
     */
    public Response process() throws IOException {
        if (index >= interceptors.size()) throw new IOException("Interceptor Chain Error");
        //获得拦截器 从第0个拦截器开始
        Interceptor interceptor = interceptors.get(index);
        //链条一条一条执行
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, call, httpConnection);
        Response response = interceptor.interceptor(next);
        return response;
    }
}

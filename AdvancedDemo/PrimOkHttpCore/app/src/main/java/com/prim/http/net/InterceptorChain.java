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

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call) {
        this.interceptors = interceptors;
        this.index = index;
        this.call = call;
    }

    /**
     * 执行拦截器
     */
    public Response process() throws IOException {
        if (index >= interceptors.size()) throw new IOException("Interceptor Chain Error");
        //获得拦截器 从第0个拦截器开始
        Interceptor interceptor = interceptors.get(index);
        //链条一条一条执行
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, call);
        Response response = interceptor.interceptor(next);
        return response;
    }
}

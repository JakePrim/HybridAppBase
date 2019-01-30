package com.prim.http.test;

import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 模拟创建拦截器的责任链
 * @time 2018/8/3 - 下午4:35
 */
public class RealInterceptorChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;

    private int index;

    private String request;

    public RealInterceptorChain(List<Interceptor> interceptors, int index, String request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    @Override
    public String request() {
        return request;
    }

    @Override
    public String proceed(String request) {
        if (index >= interceptors.size()) return null;

        //获取下一个责任链
        RealInterceptorChain next = new RealInterceptorChain(interceptors, index+1, request);
        // 执行当前的拦截器
        Interceptor interceptor = interceptors.get(index);

        return interceptor.interceptor(next);
    }
}

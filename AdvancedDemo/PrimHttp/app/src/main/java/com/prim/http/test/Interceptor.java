package com.prim.http.test;

/**
 * @author prim
 * @version 1.0.0
 * @desc 模拟okhttp拦截器
 * @time 2018/8/3 - 下午4:29
 */
public interface Interceptor {
    String interceptor(Chain chain);

    interface Chain {
        String request();

        String proceed(String request);
    }
}

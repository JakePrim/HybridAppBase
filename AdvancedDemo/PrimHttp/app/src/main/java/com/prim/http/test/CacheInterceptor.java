package com.prim.http.test;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/3 - 下午4:47
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public String interceptor(Chain chain) {
        System.out.println("执行 CacheInterceptor 最后一个拦截器 返回最终数据");
        return "success";
    }
}

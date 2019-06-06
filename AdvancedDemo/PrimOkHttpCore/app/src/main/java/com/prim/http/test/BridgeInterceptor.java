package com.prim.http.test;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/3 - 下午4:43
 */
public class BridgeInterceptor implements Interceptor {
    @Override
    public String interceptor(Chain chain) {
        System.out.println("执行 BridgeInterceptor 拦截器之前代码");
        String proceed = chain.proceed(chain.request());
        System.out.println("执行 BridgeInterceptor 拦截器之后代码 得到最终数据："+proceed);
        return proceed;
    }
}

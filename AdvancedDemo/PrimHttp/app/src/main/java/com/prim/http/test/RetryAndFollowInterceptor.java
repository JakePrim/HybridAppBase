package com.prim.http.test;

/**
 * @author prim
 * @version 1.0.0
 * @desc 模拟失败和重定向拦截器
 * @time 2018/8/3 - 下午4:33
 */
public class RetryAndFollowInterceptor implements Interceptor {
    @Override
    public String interceptor(Chain chain) {
        System.out.println("执行 RetryAndFollowInterceptor 拦截器之前代码");
        String proceed = chain.proceed(chain.request());
        System.out.println("执行 RetryAndFollowInterceptor 拦截器之后代码 得到最终数据：" + proceed);
        return proceed;
    }
}

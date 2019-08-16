package com.prim.http.net;

import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc 重试与重定向
 * @time 2019-08-14 - 18:09
 */
public class RetryInterceptor implements Interceptor {
    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        Call call = chain.call;
        HttpClient client = call.getClient();
        IOException exception = null;
        for (int i = 0; i < client.getRetrys()+1; i++) {
            //如果取消了则抛出异常
            if (call.isCanceled()) {
                throw new IOException("Canceled");
            }
            try {
                //执行链条中下一个拦截器 如果有返回response 则表示请求成功直接return结束for循环
                Response response = chain.process(httpConnection);
                return response;
            }catch (IOException e){
                exception = e;
            }
        }
        throw exception;
    }
}

package com.prim.http.net;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import static com.prim.http.net.HttpCodec.HEAD_CONNECTION;
import static com.prim.http.net.HttpCodec.HEAD_CONTENT_LENGTH;
import static com.prim.http.net.HttpCodec.HEAD_CONTENT_TYPE;
import static com.prim.http.net.HttpCodec.HEAD_HOST;

/**
 * @author prim
 * @version 1.0.0
 * @desc 请求头拦截器
 * @time 2019-08-15 - 14:35
 */
public class HeaderInterceptor implements Interceptor {
    private static final String TAG = "HeaderInterceptor";

    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        Log.e(TAG, "interceptor: HeaderInterceptor");
        //必须有host connection:keep-alive
        Request request = chain.call.request();
        Map<String, String> headers = request.getHeaders();
        //如果没有配置Connection 默认给添加上
        if (headers.containsKey(HEAD_CONNECTION)) {
            headers.put(HEAD_CONNECTION, "Keep-Alive");
        }
        //host 必须和url中的host一致的
        headers.put(HEAD_HOST, request.getUrl().getHost());
        //是否有请求体
        if (null == request.getBody()) {
            RequestBody body = request.getBody();
            long contentLength = body.contentLength();
            //请求体长度
            if (contentLength != 0) {
                headers.put(HEAD_CONTENT_LENGTH, String.valueOf(contentLength));
            }
            //请求体类型，这里只实现了一种，其他的同样的道理
            String contentType = body.contentType();
            if (null != contentType) {
                headers.put(HEAD_CONTENT_TYPE, contentType);
            }
        }
        //执行下一个
        return chain.process();
    }
}

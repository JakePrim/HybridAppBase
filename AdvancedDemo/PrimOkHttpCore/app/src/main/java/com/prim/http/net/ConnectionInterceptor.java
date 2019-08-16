package com.prim.http.net;

import android.util.Log;

import java.io.IOException;

/**
 * @author prim
 * @version 1.0.0
 * @desc 获得有效链接的拦截器, 主要功能是从连接池中获取可复用的连接，如果没有可复用
 * 的连接则创建连接添加到连接池中，以提供下次的复用
 * @time 2019-08-15 - 14:48
 */
public class ConnectionInterceptor implements Interceptor {
    private static final String TAG = "ConnectionInterceptor";

    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        Log.e(TAG, "interceptor: ConnectionInterceptor");
        Request request = chain.call.request();
        HttpClient client = chain.call.getClient();
        //获取http url
        HttpUrl url = request.getUrl();
        //从连接池中获取连接 需要具有相同的host 和 port
        HttpConnection httpConnection = client.getConnectionPool().get(url.getHost(), url.getPort());
        //没有可复用的连接
        if (httpConnection == null) {
            Log.e(TAG, "ConnectionInterceptor: 新建连接");
            httpConnection = new HttpConnection();
        } else {
            Log.e(TAG, "ConnectionInterceptor: 从连接池中获取连接");
        }
        httpConnection.setRequest(request);
        httpConnection.setClient(client);
        try {
            //执行下一个拦截器，将连接传递给下一个拦截器
            Response process = chain.process(httpConnection);
            //如果服务器返回的响应，如果服务其允许长连接
            if (process.isKeepAlive) {
                //将连接添加到连接池中
                Log.e(TAG, "ConnectionInterceptor: 得到服务器响应：isKeepAlive=true,保持长连接，将此连接加入到连接池中");
                client.getConnectionPool().put(httpConnection);
            } else {
                //如果不允许保持连接 则使用连接完毕后直接关闭连接
                Log.e(TAG, "ConnectionInterceptor: 得到服务器响应：isKeepAlive=false,不保持长连接，关闭连接");
                httpConnection.close();
            }
            return process;
        } catch (IOException e) {
            httpConnection.close();
            throw e;
        }
    }
}

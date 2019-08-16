package com.prim.http.net;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static com.prim.http.net.HttpCodec.HEAD_CONNECTION;
import static com.prim.http.net.HttpCodec.HEAD_CONTENT_LENGTH;
import static com.prim.http.net.HttpCodec.HEAD_CONTENT_TYPE;
import static com.prim.http.net.HttpCodec.HEAD_TRANSFER_ENCODING;
import static com.prim.http.net.HttpCodec.HEAD_VALUE_CHUNKED;
import static com.prim.http.net.HttpCodec.HEAD_VALUE_KEEP_ALIVE;

/**
 * @author prim
 * @version 1.0.0
 * @desc 实现通信的拦截器
 * @time 2019-08-15 - 18:39
 */
public class CallServerInterceptor implements Interceptor {
    private static final String TAG = "CallServerInterceptor";

    @Override
    public Response interceptor(InterceptorChain chain) throws IOException {
        Log.e(TAG, "interceptor: CallServerInterceptor");
        HttpConnection connection = chain.httpConnection;
        //进行I/O操作
        HttpCodec httpCodec = new HttpCodec();
        InputStream in = connection.call(httpCodec);
        //读取响应
        //读取响应行: HTTP/1.1 200 OK\r\n
        String statusLine = httpCodec.readLine(in);
        Log.e(TAG, "CallServerInterceptor: 得到响应行：" + statusLine);
        //读取响应头
        Map<String, String> headers = httpCodec.readHeader(in);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Log.e(TAG, "CallServerInterceptor: 得到响应头 key:" + entry.getKey() + " value:" + entry.getValue());
        }
        //读取响应体
        //判断请求头是否有 content-length 如果有就直接读取这大的长度就可以
        int content_length = -1;
        if (headers.containsKey(HEAD_CONTENT_LENGTH)) {
            content_length = Integer.valueOf(headers.get(HEAD_CONTENT_LENGTH));
        }
        //根据分块编码解析
        boolean isChunked = false;
        if (headers.containsKey(HEAD_TRANSFER_ENCODING)) {
            isChunked = headers.get(HEAD_TRANSFER_ENCODING).equalsIgnoreCase(HEAD_VALUE_CHUNKED);
        }
        String body = null;
        if (content_length > 0) {
            byte[] bytes = httpCodec.readBytes(in, content_length);
            body = new String(bytes);
        } else if (isChunked) {
            body = httpCodec.readChunked(in);
        }
        //status[1] 就是响应码
        String[] status = statusLine.split(" ");
        //判断服务器是否允许长连接
        boolean isKeepAlive = false;
        if (headers.containsKey(HEAD_CONNECTION)) {
            isKeepAlive = headers.get(HEAD_CONNECTION).equalsIgnoreCase(HEAD_VALUE_KEEP_ALIVE);
        }
        //更新一下这个连接的时间
        connection.updateLastUserTime();
        //返回响应包装类
        return new Response(Integer.valueOf(status[1]), content_length, headers, body, isKeepAlive);
    }
}

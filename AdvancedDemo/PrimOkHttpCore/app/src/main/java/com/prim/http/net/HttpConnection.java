package com.prim.http.net;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * @author prim
 * @version 1.0.0
 * @desc 实现socket的网络请求
 * @time 2019-08-15 - 15:17
 */
public class HttpConnection {
    Socket socket;
    Request request;
    private HttpClient client;

    /**
     * 当前链接的socket是否与对应的host port一致
     *
     * @param host
     * @param port
     * @return
     */
    public boolean isSameAddress(String host, int port) {
        if (null == socket) {
            return false;
        }
        return TextUtils.equals(socket.getInetAddress().getHostName(), host) && (port == socket.getPort());
    }

    /**
     * 最后使用的时间
     */
    long lastUseTime;

    /**
     * 释放关闭 socket
     */
    public void close() {
        if (null != socket) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 与服务器通信
     *
     * @return InputStream 服务器返回的数据
     */
    public InputStream call(HttpCodec httpCodec) throws IOException {
        //创建socket
        createSocket();
        //发送请求
        //按格式拼接 GET 地址参数 HTTP
        httpCodec.writeRequest(out, request);
        //返回服务器响应(InputStream)
        return in;
    }

    private InputStream in;
    private OutputStream out;

    /**
     * 创建socket
     */
    private void createSocket() throws IOException {
        if (null == socket || socket.isClosed()) {
            HttpUrl httpUrl = request.getUrl();
            //如果是https
            if (httpUrl.getProtocol().equalsIgnoreCase("https")) {
                //也可以用户自己设置
                socket = client.getSocketFactory().createSocket();
            } else {
                socket = new Socket();
            }
            socket.connect(new InetSocketAddress(httpUrl.getHost(), httpUrl.getPort()));
            in = socket.getInputStream();
            out = socket.getOutputStream();
        }
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public void updateLastUserTime() {
        lastUseTime = System.currentTimeMillis();
    }
}

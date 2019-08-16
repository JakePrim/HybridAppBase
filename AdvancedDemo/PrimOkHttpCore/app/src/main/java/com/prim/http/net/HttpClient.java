package com.prim.http.net;

import java.util.ArrayList;
import java.util.List;

import javax.net.SocketFactory;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-14 - 16:48
 */
public class HttpClient {

    private final Dispatcher dispatcher;

    private final int retrys;

    private final ConnectionPool connectionPool;


    private final List<Interceptor> interceptors;

    private final SocketFactory socketFactory;

    public HttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.retrys = builder.retrys;
        this.connectionPool = builder.connectionPool;
        this.interceptors = builder.interceptors;
        this.socketFactory = builder.socketFactory;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public SocketFactory getSocketFactory() {
        return socketFactory;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public int getRetrys() {
        return retrys;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public Call newCall(Request request) {
        return new Call(request, this);
    }

    public static final class Builder {
        Dispatcher dispatcher;
        int retrys;
        ConnectionPool connectionPool;

        List<Interceptor> interceptors = new ArrayList<>();

        SocketFactory socketFactory;

        /**
         * 用户自定义调度器
         *
         * @param dispatcher
         * @return
         */
        public Builder dispatcher(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        /**
         * 自定义socketFactory 如果是https 需要使用socketFactory
         *
         * @param socketFactory
         * @return
         */
        public Builder setSocketFactory(SocketFactory socketFactory) {
            this.socketFactory = socketFactory;
            return this;
        }

        /**
         * 设置重试的次数
         *
         * @param retrys
         * @return
         */
        public Builder retrys(int retrys) {
            this.retrys = retrys;
            return this;
        }

        /**
         * 添加自定义拦截器
         *
         * @param interceptor
         * @return
         */
        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        /**
         * 添加自定义的连接池
         *
         * @param connectionPool
         * @return
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
            return this;
        }

        public HttpClient build() {
            if (null == dispatcher) {
                dispatcher = new Dispatcher();
            }
            if (null == connectionPool) {
                connectionPool = new ConnectionPool();
            }
            if (null == socketFactory) {
                socketFactory = SocketFactory.getDefault();
            }
            return new HttpClient(this);
        }

    }
}

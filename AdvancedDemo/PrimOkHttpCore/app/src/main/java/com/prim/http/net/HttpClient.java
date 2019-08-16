package com.prim.http.net;

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

    public HttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.retrys = builder.retrys;
        this.connectionPool = builder.connectionPool;
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

        public Builder retrys(int retrys) {
            this.retrys = retrys;
            return this;
        }

        public HttpClient build() {
            if (null == dispatcher) {
                dispatcher = new Dispatcher();
            }
            if (null == connectionPool) {
                connectionPool = new ConnectionPool();
            }
            return new HttpClient(this);
        }

        public Builder connectionPool(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
            return this;
        }
    }
}

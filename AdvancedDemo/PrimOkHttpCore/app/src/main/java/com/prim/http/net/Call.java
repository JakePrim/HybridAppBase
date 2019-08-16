package com.prim.http.net;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-14 - 16:51
 */
public class Call {
    //需要请求的包装类
    Request request;

    //HttpClient 中配置的参数
    HttpClient client;

    //标示是否执行过
    boolean executed = false;

    boolean cancel = false;

    public Call(Request request, HttpClient client) {
        this.request = request;
        this.client = client;
    }

    public void enqueue(CallBack callBack) {
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("已经执行过了");
            }
            //标记已经执行过了
            executed = true;

        }
        //把任务交给调度器调度
        client.getDispatcher().enqueue(new AsyncCall(callBack));
    }

    /**
     * 取消请求
     */
    public void cancel() {
        cancel = true;
    }

    /**
     * 执行网络请求的线程
     */
    class AsyncCall implements Runnable {

        private CallBack callBack;

        public AsyncCall(CallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void run() {
            //信号 是否回调过
            boolean singalledCallbacked = false;
            try {
                //真正的实现请求逻辑
                Response response = getResponse();
                //如果取消了请求，就回调一个onFailure
                if (cancel) {
                    //回调通知过了
                    singalledCallbacked = true;
                    callBack.onFailure(Call.this, new IOException("Canceled"));
                } else {
                    singalledCallbacked = true;
                    //链接成功了
                    callBack.onResponse(Call.this, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //如果信号没有通知过 则回调
                if (!singalledCallbacked) {
                    callBack.onFailure(Call.this, e);
                }
            } finally {
                //将这个任务从调度器移除
                client.getDispatcher().finished(this);
            }
        }

        public String host() {
            return request.getUrl().getHost();
        }


    }

    private Response getResponse() throws Exception {
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        //添加重试拦截器
        interceptors.add(new RetryInterceptor());
        //添加请求头拦截器
        interceptors.add(new HeaderInterceptor());
        //添加连接拦截器
        interceptors.add(new ConnectionInterceptor());
        //添加通信拦截器
        interceptors.add(new CallServerInterceptor());

        InterceptorChain chain = new InterceptorChain(interceptors, 0, this, null);
        return chain.process();
    }

    public HttpClient getClient() {
        return client;
    }

    public Request request() {
        return request;
    }

    public boolean isCanceled() {
        return cancel;
    }
}

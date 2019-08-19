package com.prim.http.net;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author prim
 * @version 1.0.0
 * @desc 调度器
 * @time 2019-08-14 - 16:49
 */
public class Dispatcher {

    //最大同时进行的请求数
    private int maxRequests = 64;

    //同时请求的相同的host的最大数
    private int maxRequestsPreHost = 5;

    //等待执行双端队列
    private Deque<Call.AsyncCall> readyAsyncCalls = new ArrayDeque<>();

    //正在执行双端队列
    private Deque<Call.AsyncCall> runningAsyncCalls = new ArrayDeque<>();

    private Deque<Call> runningSyncCall = new ArrayDeque<>();

    //线程池 所有的任务都交给线程池来管理
    private ExecutorService executorService;

    /**
     * 创建一个默认的线程池
     */
    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            //线程工厂就是创建线程的
            ThreadFactory threadFactory = new ThreadFactory() {

                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "HttpClient");
                }
            };
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
                    TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        }
        return executorService;
    }

    public Dispatcher() {
        this(64, 5);
    }

    public Dispatcher(int maxRequests, int maxRequestsPreHost) {
        this.maxRequests = maxRequests;
        this.maxRequestsPreHost = maxRequestsPreHost;
    }

    /**
     * 异步任务调度
     */
    public void enqueue(Call.AsyncCall call) {
        //将要执行的call，判断正在执行的call不能超过最大请求数与相同host的请求数
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPreHost) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            //如果超过了限制的数量 则将call加入到等待队列中
            readyAsyncCalls.add(call);
        }
    }

    public void execute(Call call) {
        runningSyncCall.add(call);
    }

    /**
     * 当前正在执行的host
     *
     * @param call 正在执行的host
     * @return
     */
    private int runningCallsForHost(Call.AsyncCall call) {
        int result = 0;
        for (Call.AsyncCall aysncCall : runningAsyncCalls) {
            //正在执行队列 和当前将要执行的call的host进行比对，如果相等计数加1
            if (aysncCall.host().equals(call.host())) {
                result++;
            }
        }
        return result;
    }

    public void finished(Call call) {
        synchronized (this) {
            if (!runningSyncCall.remove(call)) throw new AssertionError("Call wasn't in-flight");
        }
    }

    /**
     * 表示一个任务请求完成
     * 将这个任务从runningAsyncCalls移除，并且检查readyAsyncCalls是否还有等待的任务
     *
     * @param asyncCall
     */
    public void finished(Call.AsyncCall asyncCall) {
        synchronized (this) {
            runningAsyncCalls.remove(asyncCall);
            //检查是否可以运行ready
            checkReady();
        }
    }

    private void checkReady() {
        //达到了同时请求最大数
        if (runningAsyncCalls.size() >= maxRequests) {
            return;
        }
        //没有等待执行的任务
        if (readyAsyncCalls.isEmpty()) {
            return;
        }
        Iterator<Call.AsyncCall> iterator = readyAsyncCalls.iterator();
        while (iterator.hasNext()) {
            //获得一个等待执行的任务
            Call.AsyncCall asyncCall = iterator.next();
            //如果等待执行的任务，加入正在执行小于最大相同host数
            if (runningCallsForHost(asyncCall) < maxRequestsPreHost) {
                iterator.remove();//从等待执行列表移除
                runningAsyncCalls.add(asyncCall);
                //线程池中执行任务
                executorService().execute(asyncCall);
            }
            //如果正在执行队列达到了最大值，则不在请求 return
            if (runningAsyncCalls.size() >= maxRequests) {
                return;
            }
        }
    }
}

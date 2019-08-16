package com.prim.http.net;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author prim
 * @version 1.0.0
 * @desc 链接池：限制的链接放到链接池中
 * @time 2019-08-15 - 14:51
 */
public class ConnectionPool {

    /**
     * 每个链接的检查时间
     * <p>
     * 每隔5s检查是否可用，无效则将其从链接池移除
     */
    private long keepAlive;

    private boolean cleanupRunning = false;


    private Deque<HttpConnection> connectionDeque = new ArrayDeque<>();

    /**
     * 清理链接池的线程
     */
    private Runnable cleanupRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                //得到下次的检查时间
                long waitDuration = cleanup(System.currentTimeMillis());
                //如果返回-1 则说明连接池中没有连接 直接结束
                if (waitDuration < 0) {
                    return;
                }
                if (waitDuration > 0) {
                    synchronized (ConnectionPool.this) {
                        try {
                            //线程暂时被挂起
                            ConnectionPool.this.wait(waitDuration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    };

    /**
     * 清理连接池中的闲置连接
     *
     * @param now
     * @return
     */
    private long cleanup(long now) {
        //记录比较每个链接的闲置时间
        long longestIdleDuration = -1;
        synchronized (this) {
            Iterator<HttpConnection> iterator = connectionDeque.iterator();
            //为什么要迭代它呢？ 如果某个链接在最长的闲置时间没有使用则进行移除
            while (iterator.hasNext()) {
                HttpConnection connection = iterator.next();
                //获取这个链接的闲置时间
                long idleDuration = now - connection.lastUseTime;
                //如果闲置时间超过了最大的闲置时间则进行移除
                if (idleDuration > keepAlive) {
                    iterator.remove();
                    //释放关闭连接
                    connection.close();
                    Log.e("connection pool", "cleanup: 超过闲置时间，移除链接池");
                    //继续检查下一个
                    continue;
                }
                //记录最长的闲置时间
                if (longestIdleDuration < idleDuration) {
                    longestIdleDuration = idleDuration;
                }
            }
            //假如keepAlive 10s longestIdleDuration是5s 那么就等5s后在检查连接池中的连接
            if (longestIdleDuration > 0) {
                return keepAlive - longestIdleDuration;
            }
            //标记连接池中没有连接
            cleanupRunning = false;
            return longestIdleDuration;
        }
    }

    /**
     * 执行清理线程的线程池
     */
    private static final Executor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread("Connection Pool");
            //设置为守护线程 有什么用呢？
            thread.setDaemon(true);
            return thread;
        }
    });

    public ConnectionPool() {
        this(1, TimeUnit.MINUTES);
    }

    public ConnectionPool(long keepAlive, TimeUnit utils) {
        this.keepAlive = utils.toMillis(keepAlive);
    }

    /**
     * 加入链接到链接池
     *
     * @param connection
     */
    public void put(HttpConnection connection) {
        //如果没有执行清理线程 则执行
        if (!cleanupRunning) {
            cleanupRunning = true;
            executor.execute(cleanupRunnable);
        }
        connectionDeque.add(connection);
    }

    /**
     * 获得对应的满足条件可复用的链接池
     *
     * @param host host
     * @param port port
     * @return
     */
    public synchronized HttpConnection get(String host, int port) {
        Iterator<HttpConnection> iterator = connectionDeque.iterator();
        while (iterator.hasNext()) {
            HttpConnection next = iterator.next();
            //如果查找到链接池中存在相同host port 的链接就可以直接使用
            if (next.isSameAddress(host, port)) {
                iterator.remove();
                return next;
            }
        }
        return null;
    }
}

package com.prim.http.net;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-08-15 - 15:05
 */
public class Test {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("runing......");
            }
        };
        //跟着进程挂掉
        thread.setDaemon(true);
        thread.start();
    }
}

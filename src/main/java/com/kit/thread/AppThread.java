/*
 * Copyright (c) 2019.
 * Email: joeyzhao1005@gmail.com
 * Author: Zhao
 */

package com.kit.thread;

import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author joeyzhao
 * <p>
 * 线程池管理类
 * 后续所有线程都在此处管理
 */
public class AppThread {

    public static void checkNeedInAsyncThread() throws IllegalThreadStateException {
        if (isMainThread()) {
            throw new IllegalThreadStateException("u need call this method in async thread!!!");
        }

    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 聊天室的多线程管理，不丢，注意这个当线程满的时候会塞回给调用的线程（一般是主线程）处理
     *
     * @return
     */
    public static ExecutorService get() {
        if (chatroomSingleton == null || chatroomSingleton.isShutdown()) {
            synchronized (AppThread.class) {
                if (chatroomSingleton == null) {
                    chatroomSingleton = ThreadPool.create("app", 5, 10, 60L, new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
        return chatroomSingleton;
    }

    private static ExecutorService chatroomSingleton;

    /**
     * 默认的单线程
     *
     * @return
     */
    public static ExecutorService single() {
        return ThreadPool.get();
    }


    /**
     * 可能会乱，但不会抛弃的单线程，注意这个当线程满的时候会塞回给调用的线程（一般是主线程）处理
     *
     * @return
     */
    public static ExecutorService singleCallerRuns() {
        return ThreadPool.callerRunsSingle();
    }

    /**
     * 抛弃老的，使用最新的单线程
     *
     * @return
     */
    public static ExecutorService singleDiscardOldest() {
        return ThreadPool.discardOldestSingle();
    }
}

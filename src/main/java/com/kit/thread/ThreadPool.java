/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.kit.thread;


import androidx.annotation.NonNull;

import com.kit.utils.log.Zog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author joeyzhao
 * 线程池创建类
 */
public class ThreadPool {


    public static ExecutorService get() {
        if (singleton == null || singleton.isShutdown()) {
            synchronized (ThreadPool.class) {
                if (singleton == null) {
                    singleton = createSingle("singleThread");
                }
            }
        }

        return singleton;
    }

    private static ExecutorService singleton;


    public static ExecutorService discardOldestSingle() {
        if (discardOldestSingleton == null || discardOldestSingleton.isShutdown()) {
            synchronized (ThreadPool.class) {
                if (discardOldestSingleton == null) {
                    discardOldestSingleton = createSingle("singleThread-discardOldest", new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }

        return discardOldestSingleton;
    }

    private static ExecutorService discardOldestSingleton;


    public static ExecutorService callerRunsSingle() {
        if (callerRunsSingleton == null || callerRunsSingleton.isShutdown()) {
            synchronized (ThreadPool.class) {
                if (callerRunsSingleton == null) {
                    callerRunsSingleton = createSingle("singleThread-callerRuns", new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }

        return callerRunsSingleton;
    }

    private static ExecutorService callerRunsSingleton;


    public static ExecutorService createSingle(String name, RejectedExecutionHandler rejectedExecutionHandler) {
        return create(name, 1, 1, 60L, rejectedExecutionHandler);
    }


    public static ExecutorService createSingle(String name) {
        return create(name, 1, 1, 60L, new ThreadPoolExecutor.DiscardPolicy());
    }


    public static ExecutorService create(String name, int threadPriority, int corePoolSize, int maxPoolSize, long keepAliveTimeSeconds, RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTimeSeconds, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(name, threadPriority));
        threadPoolExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        return threadPoolExecutor;
    }


    public static ExecutorService create(String name, int corePoolSize, int maxPoolSize, long keepAliveTimeSeconds, RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTimeSeconds, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(name));
        threadPoolExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        return threadPoolExecutor;
    }


    private static final class DefaultThreadFactory implements ThreadFactory {
        private static final int DEFAULT_PRIORITY = android.os.Process.THREAD_PRIORITY_BACKGROUND
                + android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

        private final String name;
        private int threadNum;
        private int threadPriority;

        DefaultThreadFactory(String name) {
            this.name = name;
            this.threadPriority = DEFAULT_PRIORITY;
        }

        DefaultThreadFactory(String name, int threadPriority) {
            this.name = name;
            this.threadPriority = threadPriority;
        }

        @Override
        public synchronized Thread newThread(@NonNull Runnable runnable) {
            final Thread result = new Thread(runnable, "ThreadPool-" + name + "-thread-" + threadNum) {
                @Override
                public void run() {
                    android.os.Process.setThreadPriority(threadPriority);
                    try {
                        super.run();
                    } catch (Throwable t) {
                        Zog.showException(t);
                    }
                }
            };
            threadNum++;
            return result;
        }
    }

}

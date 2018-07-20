package com.kit.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleThread {

    public static ExecutorService get() {
        if (singleton == null) {
            synchronized (SingleThread.class) {
                if (singleton == null) {
                    singleton = new ThreadPoolExecutor(1, 1,
                            60L, TimeUnit.SECONDS,
                            new SynchronousQueue<Runnable>(), new DefaultThreadFactory("singleThread"));
                }
            }
        }
        return singleton;
    }

    private static ExecutorService singleton;


    private static final class DefaultThreadFactory implements ThreadFactory {
        private static final int DEFAULT_PRIORITY = android.os.Process.THREAD_PRIORITY_BACKGROUND
                + android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

        private final String name;
        private int threadNum;

        DefaultThreadFactory(String name) {
            this.name = name;
        }

        @Override
        public synchronized Thread newThread(@NonNull Runnable runnable) {
            final Thread result = new Thread(runnable, "glide-" + name + "-thread-" + threadNum) {
                @Override
                public void run() {
                    android.os.Process.setThreadPriority(DEFAULT_PRIORITY);
                    try {
                        super.run();
                    } catch (Throwable t) {
                    }
                }
            };
            threadNum++;
            return result;
        }
    }

}

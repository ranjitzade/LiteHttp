package com.ranjitzade.litehttp.lib.core;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ranjit
 */
public class ThreadPoolManager {
    private static volatile ThreadPoolManager instance;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private ThreadPoolExecutor poolExecutor;
    private LinkedBlockingQueue<Future<?>> service = new LinkedBlockingQueue<>();


    private ThreadPoolManager() {
        final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    service.put(new FutureTask<>(r, null));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        poolExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2,
                10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), handler);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    FutureTask futureTask = null;
                    try {
                        futureTask = (FutureTask) service.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (futureTask != null) {
                        poolExecutor.execute(futureTask);
                    }
                }
            }
        };
        poolExecutor.execute(runnable);
    }

    static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    <T> void execute(FutureTask<T> futureTask) {
        if (futureTask != null) {
            try {
                service.put(futureTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void cancelAllTasks() {
        synchronized (ThreadPoolManager.class) {
            poolExecutor.shutdownNow();
            if (service != null) {
                service.clear();
            }
        }

    }
}

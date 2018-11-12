package com.example.luca.stockchartbeta;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    // singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;

    // constructor
    public AppExecutors(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }


    // create new instance if it doesn't exist
    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                // pass a newSingleThreadExecutor for diskIO
                // and a MainThreadExecutor to execute in the UI
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }


    public Executor getDiskIO() {
        return diskIO;
    }


    public Executor getMainThread() {
        return mainThread;
    }


    // executes runnable on the main thread
    public static class MainThreadExecutor implements Executor {

        // get handler to the main thread
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        // execute command on main thread
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}

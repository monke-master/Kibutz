package com.monke.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {

    public static void postOnUiThread(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);
    }

    public static Executor runOnBackground(Runnable r) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(r);
        return executor;
    }

}

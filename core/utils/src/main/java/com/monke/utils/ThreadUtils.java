package com.monke.utils;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {

    public static void postOnUiThread(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);
    }

}

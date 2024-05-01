package com.monke.utils;

import android.app.Application;

import javax.inject.Inject;

public class StringProvider {

    private static Application context;

    public static void init(Application context) {
        StringProvider.context = context;
    }

    public static String getString(int resId) {
        return context.getString(resId);
    }

}

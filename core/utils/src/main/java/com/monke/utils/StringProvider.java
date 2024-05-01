package com.monke.utils;

import android.app.Application;

import javax.inject.Inject;

public class StringProvider {

    private final Application context;

    @Inject
    public StringProvider(Application context) {
        this.context = context;
    }

    public String getString(int resId) {
        return context.getString(resId);
    }

}

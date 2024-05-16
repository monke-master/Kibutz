package com.monke.ui;

import android.content.Context;

public class DimensionsHelper {

    private static Context context;

    public static void init(Context context) {
        DimensionsHelper.context = context;
    }

    public static int getDp(int dimenId) {
        return (int)(context.getResources().getDimension(dimenId));
    }
}

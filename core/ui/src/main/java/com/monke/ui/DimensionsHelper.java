package com.monke.ui;

import android.content.Context;
import android.util.DisplayMetrics;

public class DimensionsHelper {

    private static Context context;

    public static void init(Context context) {
        DimensionsHelper.context = context;
    }

    public static int getDp(int dimenId) {
        return (int)(context.getResources().getDimension(dimenId));
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}

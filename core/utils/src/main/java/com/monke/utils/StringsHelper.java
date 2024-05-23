package com.monke.utils;

import android.content.Context;

import java.util.Formatter;
import java.util.Locale;

public class StringsHelper {

    private static Context context;

    public static void init(Context context) {
        StringsHelper.context = context;
    }

    public static String getIntOrEmpty(Integer integer) {
        if (integer == null) return "";
        return String.valueOf(integer);
    }

    public static String getFloatOrEmpty(Float x) {
        if (x == null) return "";
        return String.valueOf(x);
    }

    public static String getLongOrEmpty(Long l) {
        if (l == null) return "";
        return String.valueOf(l);
    }

    public static String formatFloat(float x) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, getLocale());
        formatter.format("%.2f", x);
        return sb.toString();
    }

    public static Locale getLocale() {
        return context.getResources().getConfiguration().getLocales().get(0);
    }
}

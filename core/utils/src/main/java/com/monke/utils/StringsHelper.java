package com.monke.utils;

import android.content.Context;
import android.util.Log;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
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

    public static long getAge(long dateOfBirth) {
        long now = Calendar.getInstance().getTimeInMillis();
        long diff = now - dateOfBirth;

        return diff / 1000 / 3600 / 24 / 365;
    }
}

package com.monke.auth.di;

public class AuthComponentProvider {
    public static AuthComponent component;

    public static void initialize() {
        component = DaggerAuthComponent.builder().build();
    }

    public static void clear() {
        component = null;
    }
}

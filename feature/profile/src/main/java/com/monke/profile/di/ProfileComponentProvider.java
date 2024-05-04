package com.monke.profile.di;

public class ProfileComponentProvider {
    public static ProfileComponent component = null;

    public static ProfileComponent getInstance() {
        if (component == null) {
            component = DaggerProfileComponent.builder().build();
        }
        return component;
    }

    public static void clear() {
        component = null;
    }
}

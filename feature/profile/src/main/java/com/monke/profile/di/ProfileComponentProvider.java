package com.monke.profile.di;

public class ProfileComponentProvider {
    private static ProfileComponent component = null;
    private static ProfileComponentDeps dependencies;

    public static ProfileComponent getInstance() {
        if (component == null) {
            component = DaggerProfileComponent
                    .builder()
                    .setDependencies(dependencies)
                    .build();
        }
        return component;
    }

    public static void setDependencies(ProfileComponentDeps deps) {
        dependencies = deps;
    }

    public static void clear() {
        component = null;
    }
}

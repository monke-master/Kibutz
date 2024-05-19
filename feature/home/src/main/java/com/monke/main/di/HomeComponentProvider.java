package com.monke.main.di;

public class HomeComponentProvider {

    private static HomeComponentDeps dependencies;
    private static HomeComponent component = null;

    public static HomeComponent getInstance() {
        if (component == null) {
            component = DaggerHomeComponent
                    .builder()
                    .setDependencies(dependencies)
                    .build();
        }
        return component;
    }

    public static void setDependencies(HomeComponentDeps deps) {
        dependencies = deps;
    }

    public static void clear() {
        component = null;
    }
}

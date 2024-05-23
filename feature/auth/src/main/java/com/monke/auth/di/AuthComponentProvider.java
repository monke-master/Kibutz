package com.monke.auth.di;

public class AuthComponentProvider {
    public static AuthComponent component;
    private static AuthComponentDeps dependencies;

    public static void initialize() {
        if (component == null) {
            component = DaggerAuthComponent
                    .builder()
                    .setDependencies(dependencies)
                    .build();
        }
    }

    public static void setDependencies(AuthComponentDeps deps) {
        dependencies = deps;
    }

    public static void clear() {
        component = null;
    }
}

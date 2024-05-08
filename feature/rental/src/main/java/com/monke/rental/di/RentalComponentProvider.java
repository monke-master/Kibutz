package com.monke.rental.di;

public class RentalComponentProvider {

    private static RentalComponentDeps dependencies;
    private static RentalComponent component = null;

    public static RentalComponent getInstance() {
        if (component == null) {
            component = DaggerRentalComponent
                            .builder()
                            .setDependencies(dependencies)
                            .build();
        }
        return component;
    }

    public static void setDependencies(RentalComponentDeps deps) {
        dependencies = deps;
    }

    public static void clear() {
        component = null;
    }
}

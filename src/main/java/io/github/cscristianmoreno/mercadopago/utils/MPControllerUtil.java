package io.github.cscristianmoreno.mercadopago.utils;

import java.util.HashSet;
import java.util.Set;

public abstract class MPControllerUtil {

    private static Set<Class<?>> controllers = new HashSet<>();

    public static void addController(Class<?> clazz) {
        controllers.add(clazz);
    }

    public static Set<Class<?>> getControllers() {
        return controllers;

    }
}

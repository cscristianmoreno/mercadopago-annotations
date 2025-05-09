package io.github.cscristianmoreno.utils;

import java.lang.reflect.InvocationTargetException;

public abstract class ClassUtil {
    
    
    /** 
     * @param clazz
     * @return T
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static <T> T getInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        T instance = (T) clazz.getDeclaredConstructor().newInstance();
        return instance;
    }
}

package com.javarush.chebotarev.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectRepository {

    private static final ConcurrentHashMap<Class<?>, Object> objects = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T find(Class<T> aClass) {
        Object object = objects.get(aClass);
        if (object == null) {
            try {
                Constructor<?> constructor = aClass.getConstructor();
                object = constructor.newInstance();
            } catch (NoSuchMethodException
                     | InvocationTargetException
                     | InstantiationException
                     | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            objects.put(aClass, object);
        }
        return (T) object;
    }
}

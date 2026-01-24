package com.javarush.chebotarev.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectRepository {

    public static final ConcurrentHashMap<Class<?>, Object> objects = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T find(Class<T> aClass) {
        Object object = objects.get(aClass);
        if (object == null) {
            Constructor<?> constructor = aClass.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = ObjectRepository.find(parameterTypes[i]);
            }
            Object newInstance;
            try {
                newInstance = constructor.newInstance(parameters);
            } catch (InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            objects.put(aClass, newInstance);
        }
        return (T) objects.get(aClass);
    }
}

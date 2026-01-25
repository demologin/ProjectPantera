package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.service.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public interface Repository<T> {

    Map<Long, T> map = new HashMap<>();

    T get(long id);

    Optional<T> findBy(String name);

    Map<Long, T> getAll();

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}

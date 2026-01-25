package com.javarush.goncharov.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<T> {

    T get(long id);

    Optional<T> findBy(String name);

    Map<Long, T> getAll();

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}

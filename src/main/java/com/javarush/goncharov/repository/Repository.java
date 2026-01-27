package com.javarush.goncharov.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> get(long id);

    Optional<T> findBy(String value1, String valiue2);

    Map<Long, T> getAll();

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}

package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.User;

import java.util.Map;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> get(long id);

    Optional<T> findBy(String value1, String valiue2);

    Map<Long, T> getAll();

    Optional<T> create(T entity);

    Optional<T> update(T entity);

    Boolean delete(T entity);
}

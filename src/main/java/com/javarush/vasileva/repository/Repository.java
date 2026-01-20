package com.javarush.vasileva.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    List<T> getAll();

    Optional<T> get(long id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}

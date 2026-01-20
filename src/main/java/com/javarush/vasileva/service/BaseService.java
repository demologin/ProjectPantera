package com.javarush.vasileva.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    List<T> getAll();

    Optional<T> get(Long id);

    void create(T entity);
}

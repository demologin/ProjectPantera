package com.javarush.aleinik.repository;

import java.util.List;

public interface Repository<T, ID>{

    List<T> findAll();

    T findById(ID id);

    T save(T entity);

    void deleteById(ID id);

}

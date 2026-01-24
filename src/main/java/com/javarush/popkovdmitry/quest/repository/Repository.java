package com.javarush.popkovdmitry.quest.repository;

import com.javarush.popkovdmitry.quest.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {

    Collection<User> getAll();

    Optional<T> get(long id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}

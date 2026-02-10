package com.javarush.trukhanova.repository;

public interface Repository<T> {

    T getById(int id);

    void load();
}
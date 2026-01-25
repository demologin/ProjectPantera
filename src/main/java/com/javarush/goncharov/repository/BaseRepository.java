package com.javarush.goncharov.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseRepository<T> implements Repository<T> {

    protected final Map<Long, T> map = new ConcurrentHashMap<>();
}

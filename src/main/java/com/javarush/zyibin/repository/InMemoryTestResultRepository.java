package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.TestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTestResultRepository implements TestResultRepository{

    private ConcurrentHashMap<Long, TestResult> results = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);


    @Override
    public void save(TestResult result) {
        if (result.getId() == 0) {
            result.setId(idGenerator.getAndIncrement());
        }
        results.put(result.getId(), result);
    }

    @Override
    public List<TestResult> findByUserId(long userId) {
        List<TestResult> list = new ArrayList<>();
        for (TestResult result : results.values()) {
            if (result.getUserId() == userId) {
                list.add(result);
            }
        }
        return  list;
    }

    @Override
    public List<TestResult> findAll() {
        return new ArrayList<>(results.values());
    }
}

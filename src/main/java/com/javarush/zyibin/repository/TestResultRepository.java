package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.TestResult;

import java.util.List;

public interface TestResultRepository {

    void save(TestResult result);
    List<TestResult> findByUserId(long userId);
    List<TestResult> findAll();
}

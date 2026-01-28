package com.javarush.zyibin.service;

public class UserTestStats extends BaseStats {

    private final String testName;

    public UserTestStats(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

}

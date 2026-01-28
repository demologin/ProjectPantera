package com.javarush.zyibin.service;

public class UserTestStats {

    private final String testName;
    private int total;
    private int passed;

    public UserTestStats(String testName) {
        this.testName = testName;
    }

    public void incrementTotal() {
        total++;
    }

    public void incrementPassed() {
        passed++;
    }

    public String getTestName() {
        return testName;
    }

    public int getTotal() {
        return total;
    }

    public int getPassed() {
        return passed;
    }

    public int getSuccessRate() {
        if (total == 0) {
            return 0;
        }
        return (passed * 100) / total;
    }
}

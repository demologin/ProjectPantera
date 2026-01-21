package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.User;

public class UserStats {

    private final String username;
    private int total;
    private int passed;

    public UserStats(User user) {
        this.username = user.getUsername();
    }

    public void incrementTotal() {
        total++;
    }

    public void incrementPassed() {
        passed++;
    }

    public String getUsername() {
        return username;
    }
    public int getTotal() {
        return total;
    }
    public int getPassed() {
        return passed;
    }
}

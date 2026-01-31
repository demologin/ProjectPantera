package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.entity.UserStats;

public class UserStatsService {

    public UserStats getStatsByUser(User user) {
        UserStats stats = new UserStats();
        stats.setUser(user);
        stats.setCompletedQuests(user.getGameNumber());
        return stats;
    }
}

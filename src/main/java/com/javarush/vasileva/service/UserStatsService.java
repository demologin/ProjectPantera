package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.entity.UserStats;

public class UserStatsService {

    public UserStats getStatsByUser(User user) {
        UserStats stats = new UserStats();
        stats.setUser(user);
        stats.setCompletedQuests(5);
        stats.setWins(3);
        stats.setLosses(2);
        stats.setTotalPlayTimeSeconds(formatPlayTime(7250)); // 2 часа 0 минут 50 секунд
        return stats;
    }

    public String formatPlayTime(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%d ч %d м %d с", hours, minutes, seconds);
    }
}

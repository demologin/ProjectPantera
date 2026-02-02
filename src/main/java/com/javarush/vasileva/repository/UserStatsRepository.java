package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.UserStats;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserStatsRepository {
    private final Map<Long, UserStats> map = new ConcurrentHashMap<>();
    private final AtomicLong generatedId = new AtomicLong(0);

    public UserStats createUserStats(long userId) {
        UserStats userStats = UserStats.builder()
                .id(generatedId.incrementAndGet())
                .userId(userId)
                .wins(0)
                .losses(0)
                .build();
        map.put(userStats.getId(), userStats);
        return userStats;
    }

    public Optional<UserStats> getUserStats(long userId) {
        UserStats stats = map.values().stream()
                .filter(userStats -> userStats.getUserId() == userId)
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(stats);
    }

    public void updateUserStats(UserStats userStats) {
        map.put(userStats.getId(), userStats);
    }
}

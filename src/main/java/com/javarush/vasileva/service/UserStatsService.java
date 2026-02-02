package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.repository.UserStatsRepository;

import java.util.Optional;

import static com.javarush.vasileva.util.Value.LOSS;
import static com.javarush.vasileva.util.Value.WIN;

public class UserStatsService {
    private final UserStatsRepository userStatsRepository;

    public UserStatsService(UserStatsRepository userStatsRepository) {
        this.userStatsRepository = userStatsRepository;
    }

    public UserStats createUserStats(long userId) {
        return userStatsRepository.createUserStats(userId);
    }

    public Optional<UserStats> getUserStats(long userId) {
        return userStatsRepository.getUserStats(userId);
    }

    public void updateUserStats(Question question, UserStats userStats) {
        userStats.setTotal(userStats.getTotal() + 1);
        if (question.getLabel().contains(WIN)) {
            userStats.setWins(userStats.getWins() + 1);
        }  else if (question.getLabel().contains(LOSS)) {
            userStats.setLosses(userStats.getLosses() + 1);
        }
        userStatsRepository.updateUserStats(userStats);
    }

}

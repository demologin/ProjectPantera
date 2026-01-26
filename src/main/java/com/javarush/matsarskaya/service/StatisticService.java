package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.repository.StatisticRepository;

public class StatisticService {
    private final StatisticRepository repository;

    public StatisticService(StatisticRepository repository) {
        this.repository = repository;
    }

    private Statistic getOrCreate(String username) {
        return repository.findByUsername(username)
                .orElseGet(() -> new Statistic(username, 0, 0, 0));
    }

    public void registerAttempt(String username) {
        Statistic statistic = getOrCreate(username);
        statistic.incrementAttempts();
        repository.save(statistic);
    }

    public void registerWin(String username) {
        Statistic statistic = getOrCreate(username);
        statistic.incrementWins();
        repository.save(statistic);
    }

    public void registerLoss(String username) {
        Statistic statistic = getOrCreate(username);
        statistic.incrementLosses();
        repository.save(statistic);
    }

    public Statistic getStatistic(String username) {
        return getOrCreate(username);
    }
}

package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.repository.StatisticRepository;

import java.util.Optional;

public class StatisticService {
    private final StatisticRepository repository;

    public StatisticService(StatisticRepository repository) {
        this.repository = repository;
    }

    /**
     * Получает или создаёт статистику для пользователя.
     * @param username имя пользователя
     * @return статистика пользователя
     */
    private Statistic getOrCreate(String username) {
        return repository.findByUsername(username)
                .orElseGet(() -> new Statistic(username, 0, 0, 0));
    }

    /**
     * Регистрирует попытку прохождения квеста.
     * @param username имя пользователя
     */
    public void registerAttempt(String username) {
        Statistic statistic = getOrCreate(username);
        statistic.incrementAttempts();
        repository.save(statistic);
    }

    /**
     * Регистрирует победу в квесте.
     * @param username имя пользователя
     */
    public void registerWin(String username) {
        Statistic statistic = getOrCreate(username);
        statistic.incrementWins();
        repository.save(statistic);
    }

    /**
     * Регистрирует поражение в квесте.
     * @param username имя пользователя
     */
    public void registerLoss(String username) {
        Statistic statistic = getOrCreate(username);
        statistic.incrementLosses();
        repository.save(statistic);
    }

    /**
     * Возвращает статистику пользователя.
     * @param username имя пользователя
     * @return Optional со статистикой
     */
    public Optional<Statistic> getStatistic(String username) {
        return repository.findByUsername(username);
    }
}

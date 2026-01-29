package com.javarush.matsarskaya.service;

import com.javarush.matsarskaya.entity.Statistic;
import com.javarush.matsarskaya.repository.StatisticRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticService.class);
    private final StatisticRepository repository;

    public StatisticService(StatisticRepository repository) {
        this.repository = repository;
        logger.info("StatisticService инициализирован");
    }

    /**
     * Получает или создаёт статистику для пользователя.
     * @param username имя пользователя
     * @return статистика пользователя
     */
    private Statistic getOrCreate(String username) {
        logger.debug("Получение или создание статистики для пользователя: {}", username);
        return repository.findByUsername(username)
                .orElseGet(() -> new Statistic(username, 0, 0, 0));
    }

    /**
     * Регистрирует попытку прохождения квеста.
     * @param username имя пользователя
     */
    public void registerAttempt(String username) {
        logger.debug("Регистрация попытки для пользователя: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementAttempts();
        repository.save(statistic);
    }

    /**
     * Регистрирует победу в квесте.
     * @param username имя пользователя
     */
    public void registerWin(String username) {
        logger.info("Регистрация победы для пользователя: {}", username);
        Statistic statistic = getOrCreate(username);
        statistic.incrementWins();
        repository.save(statistic);
    }

    /**
     * Регистрирует поражение в квесте.
     * @param username имя пользователя
     */
    public void registerLoss(String username) {
        logger.info("Регистрация поражения для пользователя: {}", username);
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
        logger.debug("Получение статистики для пользователя: {}", username);
        return repository.findByUsername(username);
    }
}

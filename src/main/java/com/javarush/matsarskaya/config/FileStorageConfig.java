package com.javarush.matsarskaya.config;

public class FileStorageConfig {
    private static final String USERS_FILE_PATH = "users.txt";
    private static final String STATISTICS_FILE_PATH = "statistics.txt";

    private FileStorageConfig() {
        // Приватный конструктор для предотвращения создания экземпляров
    }

    /**
     * Возвращает путь к файлу пользователей.
     * @return путь к файлу users.txt
     */
    public static String getUsersFilePath() {
        return USERS_FILE_PATH;
    }

    /**
     * Возвращает путь к файлу статистики.
     * @return путь к файлу statistics.txt
     */
    public static String getStatisticsFilePath() {
        return STATISTICS_FILE_PATH;
    }
}

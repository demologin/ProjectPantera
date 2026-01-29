package com.javarush.matsarskaya.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты для FileStorageConfig")
class FileStorageConfigTest {

    @Test
    @DisplayName("Получение пути к файлу пользователей")
    void testGetUsersFilePath() {
        String path = FileStorageConfig.getUsersFilePath();

        assertThat(path).isEqualTo("users.txt");
    }

    @Test
    @DisplayName("Получение пути к файлу статистики")
    void testGetStatisticsFilePath() {
        String path = FileStorageConfig.getStatisticsFilePath();

        assertThat(path).isEqualTo("statistics.txt");
    }

    @Test
    @DisplayName("Пути не являются null")
    void testPathsAreNotNull() {
        assertThat(FileStorageConfig.getUsersFilePath()).isNotNull();
        assertThat(FileStorageConfig.getStatisticsFilePath()).isNotNull();
    }

    @Test
    @DisplayName("Пути не являются пустыми")
    void testPathsAreNotEmpty() {
        assertThat(FileStorageConfig.getUsersFilePath()).isNotEmpty();
        assertThat(FileStorageConfig.getStatisticsFilePath()).isNotEmpty();
    }
}

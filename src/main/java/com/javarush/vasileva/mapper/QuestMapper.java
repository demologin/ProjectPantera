package com.javarush.vasileva.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.vasileva.entity.Quest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class QuestMapper {
    private final ObjectMapper objectMapper;

    public QuestMapper() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Читает JSON файл и преобразует в объект Quest.
     * @param filePath Путь к JSON файлу
     * @return Объект Quest
     * @throws IOException Если файл не найден или ошибка парсинга
     */
    public Quest readFromJson(String filePath) throws IOException {
//        File file = new File(filePath);
//        return objectMapper.readValue(file, Quest.class);

        InputStream inputStream = getClass().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IOException("Ресурс не найден: " + filePath);
        }

        try (inputStream) { // try-with-resources для автоматического закрытия
            return objectMapper.readValue(inputStream, Quest.class);
        }
    }

    /**
     * Сохраняет объект Quest в JSON файл.
     * @param quest Объект квеста
     * @param filePath Путь для сохранения
     * @throws IOException Если ошибка записи
     */
    public void writeToJson(Quest quest, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), quest);
    }

    /**
     * Преобразует JSON строку в объект Quest.
     * @param jsonString JSON в виде строки
     * @return Объект Quest
     * @throws IOException Если ошибка парсинга
     */
    public Quest fromJsonString(String jsonString) throws IOException {
        return objectMapper.readValue(jsonString, Quest.class);
    }

    /**
     * Преобразует объект Quest в JSON строку.
     * @param quest Объект квеста
     * @return JSON строка
     * @throws IOException Если ошибка преобразования
     */
    public String toJsonString(Quest quest) throws IOException {
        return objectMapper.writeValueAsString(quest);
    }
}

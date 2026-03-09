package com.javarush.vasileva.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.vasileva.entity.Quest;

import java.io.IOException;
import java.io.InputStream;

public class QuestMapper {
    private final ObjectMapper objectMapper;

    public QuestMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public Quest readFromJson(String filePath) throws IOException {

        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("Ресурс не найден: " + filePath);
        }
        try (inputStream) {
            return objectMapper.readValue(inputStream, Quest.class);
        }
    }

    public Quest fromJsonString(String jsonString) throws IOException {
        return objectMapper.readValue(jsonString, Quest.class);
    }

    public String toJsonString(Quest quest) throws IOException {
        return objectMapper.writeValueAsString(quest);
    }
}

package com.javarush.aleinik.data.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.exception.QuestMappingException;
import com.javarush.aleinik.util.ResourcePathResolver;

import java.io.IOException;
import java.net.URL;

public class QuestDataLoader {

    private final ObjectMapper yamlMapper;

    public QuestDataLoader(ObjectMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
    }

    public QuestDefinition load(String fileName) {
        URL questUrl = ResourcePathResolver.getQuestUrl(fileName);

        try {
            return yamlMapper.readValue(
                    questUrl,
                    QuestDefinition.class
            );

        } catch (IOException exception) {
            throw new QuestMappingException(
                    "Failed to map quest to QuestDefinition",
                    exception
            );
        }
    }
}
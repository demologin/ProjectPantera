package com.javarush.chebotarev.component;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectRepository {

    private static volatile ObjectRepository instance;
    private final QuestService questService;
    private final ObjectMapper objectMapper;

    public static QuestService getQuestService() {
        return getInstance().questService;
    }

    public static ObjectMapper getObjectMapper() {
        return getInstance().objectMapper;
    }

    private ObjectRepository() {
        questService = new QuestService();
        objectMapper = new ObjectMapper();
    }

    private static ObjectRepository getInstance() {
        if (instance == null) {
            synchronized (ObjectRepository.class) {
                if (instance == null) {
                    instance = new ObjectRepository();
                }
            }
        }
        return instance;
    }
}

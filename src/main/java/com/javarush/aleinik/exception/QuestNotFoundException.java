package com.javarush.aleinik.exception;

public class QuestNotFoundException extends RuntimeException {

    public QuestNotFoundException(Long questId) {
        super("Quest not found: " + questId);
    }
}

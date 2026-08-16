package com.javarush.aleinik.exception;

public class QuestStepNotFoundException extends RuntimeException {

    public QuestStepNotFoundException(Long questId, Long stepId) {
        super(
                "Quest step not found: questId="
                        + questId
                        + ", stepId="
                        + stepId
        );
    }
}

package com.javarush.ushanov.exception;

/**
 * Кастомное исключение — бросается когда шаг квеста не найден.
 */
public class QuestStepNotFoundException extends RuntimeException {

    public QuestStepNotFoundException(String message) {
        super(message);
    }
}

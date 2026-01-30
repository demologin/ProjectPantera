package com.javarush.vasileva.util;

public class Value {
    public static final String JSON_SAMPLE = """
            {
              "title": "Название",
              "description": "Краткое описание",
              "text": "Текст",
              "questions": [
                {
                  "label": "1",
                  "text": "Вопрос 1",
                  "answers": [
                    {
                      "nextQuestionLabel": "2",
                      "text": "Вариант ответа 1"
                    }
                  ]
                }
              ]
            }""";

    public static final String JSON_SAVE_ERROR = "Ошибка при сохранении квеста";
    public static final String QUEST_NOT_FOUND = "Квест не найден: id=";
    public static final String QUESTION_NOT_FOUND = "Вопрос не найден: label=";
    public static final String QUEST_SERIALIZATION_ERROR = "Ошибка сериализации квеста";
    public static final String EDIT_QUEST_AUTH_ERROR = "Редактировать квесты могут только пользователи с правами ADMIN";
    public static final String DELETE_QUEST_AUTH_ERROR = "Удалять квесты могут только пользователи с правами ADMIN";
    public static final String USER_LIST_AUTH_ERROR = "Получить список пользователей могут только пользователи с правами ADMIN";
    public static final String USER_NOT_FOUND = "Пользователь не найден: id=";
    public static final String EMPTY_DATA_ERROR = "Необходимо ввести данные";
    public static final String INVALID_DATA_ERROR = "Неверный email или пароль";
}

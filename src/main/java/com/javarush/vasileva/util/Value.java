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
    public static final String QUEST_SERIALIZATION_ERROR = "Ошибка сериализации квеста";
    public static final String EDIT_QUEST_AUTH_ERROR = "Редактировать квесты могут только пользователи с правами ADMIN";
}

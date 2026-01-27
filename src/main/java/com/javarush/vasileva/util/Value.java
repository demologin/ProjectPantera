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

    public static final String JSON_EMPTY_ERROR = "JSON не может быть пустым";
    public static final String JSON_SAVE_ERROR = "Ошибка при сохранении квеста";
    public static final String QUEST_SUCCESS = "Квест успешно создан/обновлен";
}

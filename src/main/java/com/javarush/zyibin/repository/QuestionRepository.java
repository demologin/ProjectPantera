package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    public static List<Question> createQuestions() {
            List<Question> questions = new ArrayList<>();
            questions.add(new Question(
                    "Что такое JVM?",
                    List.of(
                            "Среда выполнения Java-приложений",
                            "Компилятор Java",
                            "Фреймворк для веб-приложений"
                    ),
                    0
            ));
            questions.add(new Question(
                    "Какой метод вызывается при старте сервлета?",
                    List.of(
                            "doGet()",
                            "init()",
                            "service()"
                    ),
                    1
            ));
            return questions;
    }
}

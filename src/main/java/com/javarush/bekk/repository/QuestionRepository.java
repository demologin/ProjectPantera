package com.javarush.bekk.repository;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;
import com.javarush.bekk.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class QuestionRepository extends BaseRepository<Question> {

    private final Map<Long, Question> map = new HashMap<>();

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

    public QuestionRepository() {
        Answer answer = new Answer(1L, 1L, "Что-то", 2L);

        map.put(1L, new Question(1L, "1. S — Single Responsibility Principle (Принцип единственной ответственности)"));
        map.put(2L, new Question(2L, "2. O — Open/Closed Principle (Принцип открытости/закрытости)"));
        map.put(3L, new Question(3L, "3. L — Liskov Substitution Principle (Принцип подстановки Барбары Лисков)"));
        map.put(4L, new Question(4L, "4. I — Interface Segregation Principle (Принцип разделения интерфейсов)"));
        map.put(5L, new Question(5L, "5. D — Dependency Inversion Principle (Принцип инверсии зависимостей)"));
    }









    /*@Override
    public Stream<Question> find(Question pattern) {
        return map.values()
                .stream()
                .filter(question -> nullOrEquals(pattern.getId(), question.getId()))
                .filter(question -> nullOrEquals(pattern.getText(), question.getText()));
    }*/
}

package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Question;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class QuestionRepository implements Repository<Question> {
    private final Map<Long, Question> questions = new ConcurrentHashMap<>();

    @Override
    public List<Question> getAll() {
        return new ArrayList<>(questions.values());
    }

    @Override
    public Optional<Question> get(long id) {
        return Optional.ofNullable(questions.get(id));
    }

    @Override
    public void create(Question question) {
        questions.put(question.getId(), question);
    }

    @Override
    public void update(Question entity) {

    }

    @Override
    public void delete(Question entity) {

    }
}

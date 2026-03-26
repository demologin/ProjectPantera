package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class AnswerRepository implements Repository<Answer> {
    private final Map<Long, Answer> answers = new ConcurrentHashMap<>();
    private static final AtomicLong generatedId = new AtomicLong();

    @Override
    public List<Answer> getAll() {
        return new ArrayList<>(answers.values());
    }

    @Override
    public Optional<Answer> findById(long id) {
        return Optional.ofNullable(answers.get(id));
    }

    @Override
    public void create(Answer answer) {
        answer.setId(generatedId.incrementAndGet());
        answers.put(answer.getId(), answer);
    }

    @Override
    public void update(Answer entity) {
    }

    @Override
    public void delete(Answer entity) {
    }
}

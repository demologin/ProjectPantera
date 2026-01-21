package com.javarush.vasileva.repository;

import com.javarush.vasileva.entity.Question;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor
public class QuestionRepository implements Repository<Question> {
    private final Map<Long, Question> questions = new ConcurrentHashMap<>();
    private static final AtomicLong generatedId = new AtomicLong(0);

    @Override
    public List<Question> getAll() {
        return new ArrayList<>(questions.values());
    }

    @Override
    public Optional<Question> get(long id) {
        return Optional.ofNullable(questions.get(id));
    }

    public void create(Question question) {
        question.setGeneratedId(generatedId.incrementAndGet());
        questions.put(question.getGeneratedId(), question);
    }

    @Override
    public void update(Question entity) {

    }

    @Override
    public void delete(Question entity) {

    }
}

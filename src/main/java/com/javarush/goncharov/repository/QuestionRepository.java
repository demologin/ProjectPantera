package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Question;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionRepository implements Repository<Question>{
    private final Map<Long, Question> map;
    public static final AtomicLong id = new AtomicLong();

    public QuestionRepository(Storage questionStorage) {
        this.map = questionStorage.getQuestions();
    }

    @Override
    public Optional<Question> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Question> findBy(String questName, String text) {
        return map.values()
                .stream()
                .filter(u -> u.getQuestName().equals(questName))
                .filter(u -> u.getText().equals(text))
                .findAny();
    }

    @Override
    public Map<Long, Question> getAll() {
        return map;
    }

    @Override
    public void create(Question question) {
        question.setId(id.incrementAndGet());
        update(question);
    }

    @Override
    public void delete(Question question) {
        map.remove(question.getId());
    }

    @Override
    public void update(Question question) {
        map.put(question.getId(), question);
    }
}

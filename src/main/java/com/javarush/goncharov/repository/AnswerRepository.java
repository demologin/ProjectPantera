package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Answer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class AnswerRepository implements Repository<Answer>{
    private final Map<Long, Answer> map;
    public static final AtomicLong id = new AtomicLong();

    public AnswerRepository(Storage answerStorage) {
        this.map = answerStorage.getAnswers();
    }

    @Override
    public Optional<Answer> get(long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Answer> findBy(String questName, String text) {
        return map.values()
                .stream()
                .filter(u -> u.getQuestName().equals(questName))
                .filter(u -> u.getText().equals(text))
                .findAny();
    }

    @Override
    public Map<Long, Answer> getAll() {
        return map;
    }

    @Override
    public void create(Answer answer) {
        answer.setId(id.incrementAndGet());
        update(answer);
    }

    @Override
    public void delete(Answer answer) {
        map.remove(answer.getId());
    }

    @Override
    public void update(Answer answer) {
        map.put(answer.getId(), answer);
    }
}


package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.Answer;
import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.model.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;

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
    public Optional<Answer> create(Answer answer) {
        answer.setId(id.incrementAndGet());
        update(answer);
        return Optional.of(answer);
    }

    @Override
    public Boolean delete(Answer answer) {
        int sizeBeforeDelete = map.size();
        map.remove(answer.getId());
        int sizeAfterDelete = map.size();
        return sizeBeforeDelete > sizeAfterDelete ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Optional<Answer> update(Answer answer) {
        map.put(answer.getId(), answer);
        return Optional.of(answer);
    }
}


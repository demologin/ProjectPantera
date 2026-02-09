package com.javarush.trukhanova.service;

import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.repository.QuestRepository;
import lombok.Getter;

public class GameService {
    private final QuestRepository repository;

    @Getter
    private int gamesPlayed = 0;

    public GameService(QuestRepository repository) {
        this.repository = repository;
    }

    public QuestStep getNextStep(int id) {
        if (id == 1) {
            gamesPlayed++;
        }
        return repository.getStep(id);
    }

    public boolean isGameOver(QuestStep step) {
        if (step == null) return true;

        boolean noMoreAnswers = step.getAnswers() == null || step.getAnswers().isEmpty();

        String title = step.getTitle().toLowerCase();
        boolean hasEndWord = title.contains("победа") || title.contains("смерть")
                || title.contains("конец") || title.contains("плен");

        return noMoreAnswers || hasEndWord;
    }
}
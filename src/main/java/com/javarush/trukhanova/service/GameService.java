package com.javarush.trukhanova.service;

import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.repository.Repository;

public class GameService implements GameLogic {

    private final Repository<QuestStep> repository;

    public GameService(Repository<QuestStep> repository) {
        this.repository = repository;
    }

    @Override
    public QuestStep getNextStep(int id) {
        return repository.getById(id);
    }

    public boolean isGameOver(QuestStep step) {
        if (step == null) return true;

        boolean noMoreAnswers = step.getAnswers() == null || step.getAnswers().isEmpty();

        String title = step.getTitle().toLowerCase();
        boolean hasEndWord = title.contains("победа") || title.contains("конец")
                || title.contains("плен") || title.contains("яд");

        return noMoreAnswers || hasEndWord;
    }
}
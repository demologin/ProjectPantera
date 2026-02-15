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

    @Override
    public boolean isGameOver(QuestStep step) {
        if (step == null || step.getTitle() == null) {
            return step != null && (step.getAnswers() == null || step.getAnswers().isEmpty());
        }

        String title = step.getTitle().toLowerCase();
        return title.contains("победа") ||
                title.contains("поражение") ||
                step.getAnswers() == null ||
                step.getAnswers().isEmpty();
    }
}
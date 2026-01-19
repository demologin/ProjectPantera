package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.model.*;
import com.javarush.golikov.quest.repository.QuestRepository;

import java.util.Collection;

public class QuestService {

    public QuestSession startQuest(String questId) {
        Quest quest = QuestRepository.get(questId);

        if (quest == null) {
            throw new IllegalArgumentException("Quest not found: " + questId);
        }

        return new QuestSession(questId, quest.getStart().id());
    }

    public QuestNode getCurrentNode(QuestSession session) {
        Quest quest = QuestRepository.get(session.getQuestId());
        return quest.getNode(session.getCurrentNode());
    }

    public boolean isFinalNode(QuestNode node) {
        return node.choices().isEmpty();
    }

    public boolean isWinNode(QuestNode node) {
        return node.text().toLowerCase().contains("победа");
    }

    public void applyChoice(QuestSession session, String next, String answer) {

        QuestNode node = getCurrentNode(session);

        Choice choice = node.choices().stream()
                .filter(c -> c.next().equals(next))
                .findFirst()
                .orElseThrow();

        session.addStep(
                node.text(),
                answer,
                choice.positive()
        );

        session.setCurrentNode(next);
    }

    public Collection<Quest> getAllQuests() {
        return QuestRepository.all();
    }

    public void exitQuest(QuestSession session) {
        session.lose();
    }

    public String getQuestTitle(QuestSession qs) {
        return QuestRepository.get(qs.getQuestId()).getTitle();
    }
}
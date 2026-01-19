package com.javarush.golikov.quest.service;

import com.javarush.golikov.quest.model.QuestResult;
import com.javarush.golikov.quest.model.QuestStatRow;
import com.javarush.golikov.quest.repository.StatisticsRepository;

import java.util.*;

public class StatisticsService {

    public void saveResult(String login, String questId, boolean win) {
        StatisticsRepository.add(
                new QuestResult(login, questId, win)
        );
    }

    public List<QuestStatRow> getStats() {

        Map<String, QuestStatRowBuilder> map = new HashMap<>();

        for (QuestResult r : StatisticsRepository.all()) {

            String key = r.login() + "|" + r.questId();

            map.computeIfAbsent(key,
                    k -> new QuestStatRowBuilder(r.login(), r.questId())
            ).add(r.win());
        }

        List<QuestStatRow> result = new ArrayList<>();
        for (QuestStatRowBuilder b : map.values()) {
            result.add(b.build());
        }

        result.sort(
                Comparator
                        .comparing(QuestStatRow::getLogin)
                        .thenComparing(QuestStatRow::getQuestId)
        );

        return result;
    }

    private static class QuestStatRowBuilder {
        String login;
        String questId;
        int wins;
        int loses;

        QuestStatRowBuilder(String login, String questId) {
            this.login = login;
            this.questId = questId;
        }

        void add(boolean win) {
            if (win) wins++;
            else loses++;
        }

        QuestStatRow build() {
            return new QuestStatRow(login, questId, wins, loses);
        }
    }
}
package com.javarush.golikov.quest.repository;

import com.javarush.golikov.quest.model.QuestResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsRepository {

    private static final List<QuestResult> results = new ArrayList<>();

    public static void add(QuestResult result) {
        results.add(result);
    }

    public static List<QuestResult> all() {
        return Collections.unmodifiableList(results);
    }

    public static void clear() {
        results.clear();
    }
}
package com.javarush.golikov.quest.loader;

import com.javarush.golikov.quest.model.*;

import java.io.*;
import java.util.*;

public class TxtQuestLoader {

    public static Quest load(InputStream in, String questId) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String title = null;
        String startNodeId = null;

        Map<String, QuestNode> nodes = new LinkedHashMap<>();

        String currentId = null;
        String currentText = null;
        List<Choice> choices = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // ===== TITLE =====
            if (line.startsWith("!")) {
                title = line.substring(1).trim();
                continue;
            }

            // ===== START NODE =====
            if (line.startsWith("*")) {
                startNodeId = line.substring(1).trim();
                continue;
            }

            // ===== NODE ID =====
            if (line.startsWith("@")) {

                if (currentId != null) {
                    if (currentText == null) {
                        throw new RuntimeException(
                                "Node '" + currentId + "' has no text (? ...)"
                        );
                    }
                    nodes.put(currentId,
                            new QuestNode(currentId, currentText, choices));
                }

                currentId = line.substring(1).trim();
                currentText = null;
                choices = new ArrayList<>();
                continue;
            }

            // ===== NODE TEXT =====
            if (line.startsWith("?")) {
                if (currentId == null) {
                    throw new RuntimeException(
                            "Text before node id: " + line
                    );
                }
                currentText = line.substring(1).trim();
                continue;
            }

            // ===== CHOICES =====
            if (line.startsWith("+") || line.startsWith("-")) {
                if (currentId == null) {
                    throw new RuntimeException(
                            "Choice before node id: " + line
                    );
                }

                String[] parts = line.substring(1).trim().split("->");
                if (parts.length != 2) {
                    throw new RuntimeException(
                            "Invalid choice format: " + line
                    );
                }

                choices.add(new Choice(
                        parts[0].trim(),
                        parts[1].trim(),
                        line.startsWith("+")
                ));
            }
        }

        // ===== последний узел =====
        if (currentId != null) {
            if (currentText == null) {
                throw new RuntimeException(
                        "Node '" + currentId + "' has no text (? ...)"
                );
            }
            nodes.put(currentId,
                    new QuestNode(currentId, currentText, choices));
        }

        // ===== ВАЛИДАЦИЯ =====
        if (title == null) {
            throw new RuntimeException("Quest has no title (!...)");
        }
        if (startNodeId == null) {
            throw new RuntimeException("Quest has no start node (*...)");
        }
        if (!nodes.containsKey(startNodeId)) {
            throw new RuntimeException(
                    "Start node not found: " + startNodeId
            );
        }

        return new Quest(questId, title, startNodeId, nodes);
    }
}
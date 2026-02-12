package com.javarush.khmelov.config;

import com.javarush.khmelov.entity.Choice;
import com.javarush.khmelov.entity.EndingType;
import com.javarush.khmelov.entity.Story;
import com.javarush.khmelov.entity.StoryNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class StoryLoader {

    // Analyze story file
    public Story load(String code, InputStream in) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            String title = "Untitled Story";
            Map<String, MutableNode> temp = new LinkedHashMap<>();
            String currentKey = null;

            String raw;
            int lineNo = 0;

            while ((raw = br.readLine()) != null) {
                lineNo++;
                String line = raw.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("TITLE:")) {
                    title = line.substring("TITLE:".length()).trim();
                    continue;
                }

                if (line.startsWith("[") && line.endsWith("]") && line.length() > 2) {
                    currentKey = line.substring(1, line.length() - 1).trim();
                    temp.putIfAbsent(currentKey, new MutableNode(currentKey));
                    continue;
                }

                if (currentKey == null) {
                    throw new StoryParseException("Line outside node block at " + lineNo + ": " + raw);
                }

                MutableNode node = temp.get(currentKey);

                if (line.startsWith("TEXT:")) {
                    node.text = line.substring("TEXT:".length()).trim();
                    continue;
                }

                if (line.startsWith("CHOICE:")) {
                    String rest = line.substring("CHOICE:".length()).trim();
                    int arrow = rest.indexOf("->");
                    if (arrow < 0) throw new StoryParseException("Bad CHOICE at " + lineNo + ": " + raw);
                    String label = rest.substring(0, arrow).trim();
                    String next = rest.substring(arrow + 2).trim();
                    node.choices.add(new Choice(label, next));
                    continue;
                }

                if (line.startsWith("END:")) {
                    String val = line.substring("END:".length()).trim();
                    node.endingType = EndingType.fromRussian(val);
                    continue;
                }

                throw new StoryParseException("Unknown directive at " + lineNo + ": " + raw);
            }

            if (!temp.containsKey("START")) throw new StoryParseException("Missing [START] node.");

            // build nodes
            Map<String, StoryNode> nodes = new LinkedHashMap<>();
            for (MutableNode mn : temp.values()) {
                if (mn.text == null) throw new StoryParseException("Node [" + mn.key + "] missing TEXT:");
                nodes.put(mn.key, new StoryNode(mn.key, mn.text, mn.choices, mn.endingType));
            }

            // validate links
            for (StoryNode n : nodes.values()) {
                for (Choice c : n.getChoices()) {
                    if (!nodes.containsKey(c.getNextNodeKey())) {
                        throw new StoryParseException("Node [" + n.getKey() + "] points to missing node: " + c.getNextNodeKey());
                    }
                }
            }

            return new Story(code, title, nodes);

        } catch (IOException e) {
            throw new StoryParseException("Failed to load story: " + code, e);
        }
    }

    private static final class MutableNode {
        final String key;
        String text;
        EndingType endingType;
        final List<Choice> choices = new ArrayList<>();

        MutableNode(String key) { this.key = key; }
    }
}
package com.javarush.khmelov.lesson13.service;

import com.javarush.khmelov.lesson13.model.Choice;
import com.javarush.khmelov.lesson13.model.Scene;
import com.javarush.khmelov.lesson13.quest.*;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestLoader {

    public static LoadedQuest load(String path) {
        LoaderOptions options = new LoaderOptions();
        Constructor constructor = new Constructor(QuestConfig.class, options);
        Yaml yaml = new Yaml(constructor);

        InputStream input = QuestLoader.class
                .getClassLoader()
                .getResourceAsStream(path);

        if (input == null) {
            throw new RuntimeException("Quest file not found: " + path);
        }

        QuestConfig config = yaml.load(input);

        Map<String, Scene> scenes = new HashMap<>();

        for (var entry : config.getScenes().entrySet()) {
            String id = entry.getKey();
            SceneConfig sc = entry.getValue();

            List<Choice> choices = sc.getChoices().stream()
                    .map(c -> new Choice(c.getId(), c.getText(), c.getNext()))
                    .toList();

            scenes.put(id, new Scene(id, sc.getText(), choices));
        }

        return new LoadedQuest(config.getStart(), scenes);
    }
}

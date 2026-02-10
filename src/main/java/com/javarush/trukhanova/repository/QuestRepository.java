package com.javarush.trukhanova.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.exception.QuestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestRepository implements Repository<QuestStep> {
    private static final Logger logger = LogManager.getLogger(QuestRepository.class);
    private final Map<Integer, QuestStep> steps = new HashMap<>();

    // Список файлов, которые мы создали выше
    private final List<String> dataFiles = List.of(
            "prologue.yml",
            "gameplay.yml",
            "final.yml"
    );

    public QuestRepository() {
        load();
    }

    @Override
    public void load() {
        YAMLMapper mapper = new YAMLMapper();
        for (String fileName : dataFiles) {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (is == null) {
                    throw new QuestException("Критическая ошибка: файл " + fileName + " не найден!");
                }
                List<QuestStep> loaded = mapper.readValue(is, new TypeReference<List<QuestStep>>() {});
                for (QuestStep step : loaded) {
                    steps.put(step.getId(), step);
                }
                logger.info("Загружено {} шагов из {}", loaded.size(), fileName);
            } catch (Exception e) {
                throw new QuestException("Ошибка при чтении YAML файла: " + fileName, e);
            }
        }
        logger.info("Итого в игре доступно {} локаций.", steps.size());
    }

    @Override
    public QuestStep getById(int id) {
        QuestStep step = steps.get(id);
        if (step == null) {
            logger.warn("Попытка перехода на несуществующий ID: {}. Сбрасываем на старт.", id);
            return steps.get(1);
        }
        return step;
    }
}
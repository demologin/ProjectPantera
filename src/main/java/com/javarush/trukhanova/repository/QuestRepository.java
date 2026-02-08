package com.javarush.trukhanova.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.trukhanova.entity.QuestStep;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestRepository {
    private static final Logger logger = LogManager.getLogger(QuestRepository.class);
    private final Map<Integer, QuestStep> steps = new HashMap<>();

    public QuestRepository() {
        loadData();
    }

    private void loadData() {
        logger.info("Начало загрузки данных квеста из JSON");
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("game_steps.json")) {
            if (is == null) {
                logger.error("Файл game_steps.json не найден в ресурсах!");
                return;
            }
            List<QuestStep> stepList = mapper.readValue(is, new TypeReference<List<QuestStep>>() {});
            for (QuestStep step : stepList) {
                steps.put(step.getId(), step);
            }
            logger.info("Загрузка завершена успешно. Загружено шагов: {}", steps.size());
        } catch (Exception e) {
            logger.error("Произошла ошибка при парсинге JSON: ", e);
        }
    }

    public QuestStep getStep(int id) {
        return steps.getOrDefault(id, steps.get(1));
    }
}
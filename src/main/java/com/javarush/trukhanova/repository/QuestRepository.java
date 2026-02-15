package com.javarush.trukhanova.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.javarush.trukhanova.entity.QuestStep;
import com.javarush.trukhanova.exception.RepositoryException;
import com.javarush.trukhanova.exception.StepNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestRepository implements Repository<QuestStep> {
    private static final Logger logger = LogManager.getLogger(QuestRepository.class);
    private final Map<Integer, QuestStep> steps = new HashMap<>();

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
        logger.info("Начало загрузки конфигурационных файлов квеста...");
        YAMLMapper mapper = new YAMLMapper();

        for (String fileName : dataFiles) {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (is == null) {
                    logger.fatal("Файл ресурсов не найден: {}", fileName);
                    throw new RepositoryException("Критическая ошибка: файл " + fileName + " не найден!", null);
                }

                List<QuestStep> loaded = mapper.readValue(is, new TypeReference<>() {});
                for (QuestStep step : loaded) {
                    steps.put(step.getId(), step);
                }

                logger.info("Успешно загружено шагов: {} из файла: {}", loaded.size(), fileName);

            } catch (Exception e) {
                logger.error("Ошибка парсинга файла {}: {}", fileName, e.getMessage());
                throw new RepositoryException("Ошибка при чтении или парсинге YAML файла: " + fileName, e);
            }
        }
        logger.info("Загрузка всех данных завершена. Общее количество шагов в базе: {}", steps.size());
    }

    @Override
    public QuestStep getById(int id) {
        QuestStep step = steps.get(id);
        if (step == null) {
            logger.warn("Запрошен несуществующий шаг с ID: {}", id);
            throw new StepNotFoundException("Попытка перехода на несуществующий ID: " + id);
        }
        return step;
    }
}
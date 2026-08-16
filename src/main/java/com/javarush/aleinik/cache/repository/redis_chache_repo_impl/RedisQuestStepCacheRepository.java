package com.javarush.aleinik.cache.repository.redis_chache_repo_impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.aleinik.cache.dto.QuestStepCacheDto;
import com.javarush.aleinik.cache.repository.QuestStepCacheRepository;
import com.javarush.aleinik.exception.QuestStepCacheException;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

public class RedisQuestStepCacheRepository implements QuestStepCacheRepository {

    private static final String QUEST_STEP_KEY_PREFIX = "quest-step:";

    private final StatefulRedisConnection<String, String> connection;
    private final ObjectMapper objectMapper;
    private final Long ttlSeconds;

    public RedisQuestStepCacheRepository(
            StatefulRedisConnection<String, String> connection,
            ObjectMapper objectMapper,
            Long ttlSeconds
    ) {
        this.connection = connection = connection;
        this.objectMapper = objectMapper;
        this.ttlSeconds = ttlSeconds;
    }

    @Override
    public QuestStepCacheDto findStepByQuestId(Long questId, Long stepId) {
        String key = buildKey(questId, stepId);

        try {
            RedisCommands<String, String> commands = connection.sync();

            String json = commands.get(key);

            if (json == null) {
                return null;
            }

            return objectMapper.readValue(json, QuestStepCacheDto.class);

        } catch (JsonProcessingException | RedisException exception) {
            throw new QuestStepCacheException(
                    "Failed to read quest step from Redis",
                    exception);
        }
    }

    @Override
    public void save(QuestStepCacheDto step) {
        String key = buildKey(step.getQuestId(), step.getStepId());

        try {
            String json = objectMapper.writeValueAsString(step);

            RedisCommands<String, String> commands =
                    connection.sync();

            commands.setex(key, ttlSeconds, json);

        } catch (JsonProcessingException | RedisException exception) {
            throw new QuestStepCacheException(
                    "Failed to save quest step to Redis",
                    exception);
        }
    }

    @Override
    public void saveAll(List<QuestStepCacheDto> steps) {
        try {

            RedisCommands<String, String> commands = connection.sync();

            for (QuestStepCacheDto step : steps) {
                String key = buildKey(step.getQuestId(), step.getStepId());
                String json = objectMapper.writeValueAsString(step);

                commands.setex(key, ttlSeconds, json);
            }

        } catch (JsonProcessingException | RedisException exception) {
            throw new QuestStepCacheException(
                    "Failed to save quest steps to Redis",
                    exception);
        }
    }

    @Override
    public void deleteStep(Long questId, Long stepId) {
        try {
            String key = buildKey(questId, stepId);

            RedisCommands<String, String> commands = connection.sync();
            commands.del(key);
        } catch (RedisException exception) {
            throw new QuestStepCacheException(
                    "Failed to delete quest step from Redis",
                    exception);
        }

    }

    private String buildKey(Long questId, Long stepId) {
        return QUEST_STEP_KEY_PREFIX + questId + ":" + stepId;
    }
}

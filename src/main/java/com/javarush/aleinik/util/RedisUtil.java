package com.javarush.aleinik.util;

import com.javarush.aleinik.config.ApplicationProperties;
import com.javarush.aleinik.exception.QuestStepCacheException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisUtil {
    private static final RedisClient REDIS_CLIENT =
            RedisClient.create(
                    ApplicationProperties.getRedisUrl()
            );

    private static StatefulRedisConnection<String, String>
            redisConnection;

    private RedisUtil() {
    }

    public static synchronized StatefulRedisConnection<String, String> getConnection() {
        if (redisConnection != null && redisConnection.isOpen()) {
            return redisConnection;
        }

        try {
            redisConnection = REDIS_CLIENT.connect();
            return redisConnection;
        } catch (RuntimeException exception) {
            throw new QuestStepCacheException(
                    "Failed to connect to Redis",
                    exception
            );
        }
    }

    public static void shutdown() {
        if (redisConnection != null && redisConnection.isOpen()) {
            redisConnection.close();
        }
        REDIS_CLIENT.shutdown();
    }


}

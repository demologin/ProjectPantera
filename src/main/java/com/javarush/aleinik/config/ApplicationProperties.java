package com.javarush.aleinik.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final String REDIS_URL_KEY = "redis.url";
    private static final String REDIS_QUEST_STEP_TTL_KEY =
            "redis.quest-step.ttl-seconds";

    static {
       try(InputStream input = ApplicationProperties.class
                        .getClassLoader()
                        .getResourceAsStream("application.properties")) {

           if(input == null){
               throw new IllegalStateException("application.properties not found");
           }

           PROPERTIES.load(input);

        } catch (IOException e) {
           throw new ExceptionInInitializerError(e);
       }
    }

    private ApplicationProperties() {
    }

    public static String getRedisUrl(){
        return PROPERTIES.getProperty(REDIS_URL_KEY);
    }

    public static long getRedisQuestStepTtlSeconds() {
        return Long.parseLong(
                PROPERTIES.getProperty(REDIS_QUEST_STEP_TTL_KEY)
        );
    }
}

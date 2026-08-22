package com.javarush.aleinik.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.aleinik.cache.mapper.QuestStepCacheMapper;
import com.javarush.aleinik.cache.repository.redis_chache_repo_impl.NoOpQuestStepCacheRepository;
import com.javarush.aleinik.cache.repository.QuestStepCacheRepository;
import com.javarush.aleinik.cache.repository.redis_chache_repo_impl.RedisQuestStepCacheRepository;
import com.javarush.aleinik.cache.service.QuestStepCacheService;
import com.javarush.aleinik.data.initializer.QuestDataInitializer;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.exception.QuestStepCacheException;
import com.javarush.aleinik.repository.QuestRepository;
import com.javarush.aleinik.repository.QuestStepRepository;
import com.javarush.aleinik.repository.hibernate_repo_impl.HibernateQuestRepository;
import com.javarush.aleinik.repository.hibernate_repo_impl.HibernateQuestStepRepository;
import com.javarush.aleinik.service.QuestService;
import com.javarush.aleinik.service.QuestStepService;
import com.javarush.aleinik.util.HibernateUtil;
import com.javarush.aleinik.util.RedisUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

@Slf4j
public class ApplicationConfig {

    private static final ObjectMapper jsonObjectMapper =
            new ObjectMapper();

    private static final ObjectMapper yamlObjectMapper =
            new ObjectMapper(new YAMLFactory());

    private static final QuestStepCacheMapper questStepCacheMapper =
            new QuestStepCacheMapper();


    private static final SessionFactory sessionFactory =
            HibernateUtil.getSessionFactory();

    private static final QuestRepository questRepository =
            new HibernateQuestRepository(sessionFactory);

    private static final QuestStepRepository questStepRepository =
            new HibernateQuestStepRepository(sessionFactory);

    private static final QuestStepCacheRepository questStepCacheRepository = createQuestStepCacheRepository();

    private static final QuestStepCacheService cacheService = new QuestStepCacheService(questStepCacheRepository, questStepCacheMapper);

    @Getter
    private static final QuestService questService = new QuestService(questRepository);

    @Getter
    private static final QuestStepService questStepService = new QuestStepService(questStepRepository, cacheService);


    private static final QuestDataLoader questDataLoader = new QuestDataLoader(yamlObjectMapper);

    private static final QuestDataInitializer questDataInitializer =
            new QuestDataInitializer(
                    questDataLoader,
                    questRepository,
                    questStepRepository);

    private static QuestStepCacheRepository createQuestStepCacheRepository() {
        try {

            QuestStepCacheRepository repository =
                    new RedisQuestStepCacheRepository(RedisUtil.getConnection(),
                            jsonObjectMapper,
                            ApplicationProperties.getRedisQuestStepTtlSeconds());

            log.info("Redis cache repository initialized");

            return repository;

        } catch (QuestStepCacheException exception) {

            log.warn("Redis is unavailable. "
                            + "Application will continue without cache",
                    exception);

            return new NoOpQuestStepCacheRepository();
        }
    }

    public static void initialize() {
        if (questRepository.findAll().isEmpty()) {
            questDataInitializer.initialize("pantera");
            questDataInitializer.initialize("matrix");
        }
    }

    public static void shutdown() {
        RedisUtil.shutdown();
        HibernateUtil.shutdown();
    }

}

package benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.aleinik.cache.mapper.QuestStepCacheMapper;
import com.javarush.aleinik.cache.repository.QuestStepCacheRepository;
import com.javarush.aleinik.cache.repository.redis_chache_repo_impl.RedisQuestStepCacheRepository;
import com.javarush.aleinik.cache.service.QuestStepCacheService;
import com.javarush.aleinik.config.ApplicationConfig;
import com.javarush.aleinik.config.ApplicationProperties;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;
import com.javarush.aleinik.repository.hibernate_repo_impl.HibernateQuestStepRepository;
import com.javarush.aleinik.util.HibernateUtil;
import com.javarush.aleinik.util.RedisUtil;

import java.util.List;

public class QuestStepPerformanceComparison {

    private static final Long QUEST_ID = 1L;

    private static final int NUMBER_OF_STEPS = 10;
    private static final int WARMUP_ROUNDS = 3;
    private static final int MEASUREMENT_ROUNDS = 10;

    public static void main(String[] args) {
        ApplicationConfig.initialize();;

        QuestStepRepository databaseRepository =
                new HibernateQuestStepRepository(
                        HibernateUtil.getSessionFactory()
                );

        QuestStepCacheRepository cacheRepository =
                new RedisQuestStepCacheRepository(
                        RedisUtil.getConnection(),
                        new ObjectMapper(),
                        ApplicationProperties
                                .getRedisQuestStepTtlSeconds()
                );

        QuestStepCacheService cacheService =
                new QuestStepCacheService(
                        cacheRepository,
                        new QuestStepCacheMapper()
                );

        try {
            List<Long> stepIds = prepareRedis(
                    databaseRepository,
                    cacheService
            );

            warmUp(
                    databaseRepository,
                    cacheService,
                    stepIds
            );

            long totalRedisNanos = 0;
            long totalMySqlNanos = 0;

            for (int round = 1;
                 round <= MEASUREMENT_ROUNDS;
                 round++) {

                long redisNanos;
                long mySqlNanos;

                /*
                 * Чередуем порядок, чтобы первый источник
                 * не получал постоянного преимущества.
                 */
                if (round % 2 == 1) {
                    redisNanos = measureRedis(
                            cacheService,
                            stepIds
                    );

                    mySqlNanos = measureMySql(
                            databaseRepository,
                            stepIds
                    );
                } else {
                    mySqlNanos = measureMySql(
                            databaseRepository,
                            stepIds
                    );

                    redisNanos = measureRedis(
                            cacheService,
                            stepIds
                    );
                }

                totalRedisNanos += redisNanos;
                totalMySqlNanos += mySqlNanos;

                System.out.printf(
                        "Round %d: Redis %.3f ms, MySQL %.3f ms%n",
                        round,
                        toMilliseconds(redisNanos),
                        toMilliseconds(mySqlNanos)
                );
            }

            double averageRedisMilliseconds =
                    toMilliseconds(totalRedisNanos)
                            / MEASUREMENT_ROUNDS;

            double averageMySqlMilliseconds =
                    toMilliseconds(totalMySqlNanos)
                            / MEASUREMENT_ROUNDS;

            double ratio =
                    averageMySqlMilliseconds
                            / averageRedisMilliseconds;

            System.out.println();
            System.out.printf(
                    "Average of %d rounds:%n",
                    MEASUREMENT_ROUNDS
            );
            System.out.printf(
                    "Redis: %.3f ms%n",
                    averageRedisMilliseconds
            );
            System.out.printf(
                    "MySQL: %.3f ms%n",
                    averageMySqlMilliseconds
            );
            System.out.printf(
                    "Redis is approximately %.2fx faster%n",
                    ratio
            );

        } finally {
            ApplicationConfig.shutdown();
        }
    }

    private static List<Long> prepareRedis(
            QuestStepRepository databaseRepository,
            QuestStepCacheService cacheService
    ) {
        List<QuestStep> steps =
                databaseRepository
                        .findAllByQuestIdWithChoices(
                                QUEST_ID
                        );

        if (steps.isEmpty()) {
            throw new IllegalStateException(
                    "Quest not found: " + QUEST_ID
            );
        }

        cacheService.cacheAll(steps);

        return steps.stream()
                .limit(NUMBER_OF_STEPS)
                .map(QuestStep::getStepId)
                .toList();
    }

    private static void warmUp(
            QuestStepRepository databaseRepository,
            QuestStepCacheService cacheService,
            List<Long> stepIds
    ) {
        for (int round = 0;
             round < WARMUP_ROUNDS;
             round++) {

            readFromRedis(cacheService, stepIds);
            readFromMySql(databaseRepository, stepIds);
        }
    }

    private static long measureRedis(
            QuestStepCacheService cacheService,
            List<Long> stepIds
    ) {
        long startedAt = System.nanoTime();

        readFromRedis(cacheService, stepIds);

        return System.nanoTime() - startedAt;
    }

    private static long measureMySql(
            QuestStepRepository databaseRepository,
            List<Long> stepIds
    ) {
        long startedAt = System.nanoTime();

        readFromMySql(databaseRepository, stepIds);

        return System.nanoTime() - startedAt;
    }

    private static void readFromRedis(
            QuestStepCacheService cacheService,
            List<Long> stepIds
    ) {
        for (Long stepId : stepIds) {
            QuestStep step =
                    cacheService.getQuestStepById(
                            QUEST_ID,
                            stepId
                    );

            verifyStep(step, stepId);
        }
    }

    private static void readFromMySql(
            QuestStepRepository databaseRepository,
            List<Long> stepIds
    ) {
        for (Long stepId : stepIds) {
            QuestStep step =
                    databaseRepository.findStepByQuestId(
                            QUEST_ID,
                            stepId
                    );

            verifyStep(step, stepId);
        }
    }

    private static void verifyStep(
            QuestStep step,
            Long stepId
    ) {
        if (step == null) {
            throw new IllegalStateException(
                    "Step not found: " + stepId
            );
        }

        if (step.getChoices() == null) {
            throw new IllegalStateException(
                    "Choices are not loaded for step: "
                            + stepId
            );
        }

        step.getChoices().size();
    }

    private static double toMilliseconds(
            long nanoseconds
    ) {
        return nanoseconds / 1_000_000.0;
    }
}
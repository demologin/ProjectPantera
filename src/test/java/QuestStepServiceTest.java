import com.javarush.aleinik.cache.service.QuestStepCacheService;
import com.javarush.aleinik.exception.QuestStepCacheException;
import com.javarush.aleinik.exception.QuestStepNotFoundException;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;
import com.javarush.aleinik.service.QuestStepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestStepServiceTest {

    private static final Long QUEST_ID = 1L;
    private static final Long STEP_ID = 3L;

    @Mock
    private QuestStepRepository questStepRepository;

    @Mock
    private QuestStepCacheService cacheService;

    @InjectMocks
    private QuestStepService questStepService;

    @Test
    void shouldReturnStepFromCache() {
        QuestStep expectedStep = TestUtil.createStep(STEP_ID);

        when(
                cacheService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                )
        ).thenReturn(expectedStep);

        QuestStep actual =
                questStepService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                );

        assertSame(expectedStep, actual);

        verify(cacheService).getQuestStepById(
                QUEST_ID,
                STEP_ID
        );

        verifyNoInteractions(questStepRepository);
    }

    @Test
    void shouldLoadAllStepsAndWarmCacheOnCacheMiss() {
        QuestStep expectedStep = TestUtil.createStep(STEP_ID);
        List<QuestStep> steps = List.of(expectedStep);

        when(
                cacheService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                )
        ).thenReturn(null);

        when(
                questStepRepository
                        .findAllByQuestIdWithChoices(
                                QUEST_ID
                        )
        ).thenReturn(steps);

        QuestStep actual =
                questStepService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                );

        assertSame(expectedStep, actual);

        verify(
                questStepRepository
        ).findAllByQuestIdWithChoices(QUEST_ID);

        verify(cacheService).cacheAll(steps);
    }

    @Test
    void shouldFallBackToMySqlWhenRedisFails() {
        QuestStep expectedStep = TestUtil.createStep(STEP_ID);
        List<QuestStep> steps = List.of(expectedStep);

        when(
                cacheService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                )
        ).thenThrow(
                new QuestStepCacheException(
                        "Redis unavailable",
                        new RuntimeException()
                )
        );

        when(
                questStepRepository
                        .findAllByQuestIdWithChoices(
                                QUEST_ID
                        )
        ).thenReturn(steps);

        doThrow(
                new QuestStepCacheException(
                        "Redis unavailable",
                        new RuntimeException()
                )
        ).when(cacheService).cacheAll(steps);

        QuestStep actual =
                questStepService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                );

        assertSame(expectedStep, actual);

        verify(
                questStepRepository
        ).findAllByQuestIdWithChoices(QUEST_ID);
    }

    @Test
    void shouldThrowExceptionWhenStepDoesNotExist() {
        when(
                cacheService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                )
        ).thenReturn(null);

        when(
                questStepRepository
                        .findAllByQuestIdWithChoices(
                                QUEST_ID
                        )
        ).thenReturn(List.of());

        assertThrows(
                QuestStepNotFoundException.class,
                () -> questStepService.getQuestStepById(
                        QUEST_ID,
                        STEP_ID
                )
        );
    }

}


import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.data.initializer.QuestDataInitializer;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.exception.QuestMappingException;
import com.javarush.aleinik.model.Choice;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestDataInitializerTest {

    @Mock
    QuestDataLoader questDataLoader;

    @Mock
    QuestRepository questRepository;

    @InjectMocks
    QuestDataInitializer initializer;

    @Test
    void shouldProcessQuestDefinitionAndCreateQuest() {
        QuestDefinition definition = TestUtil.createQuestDefinition();

        when(questDataLoader.load("pantera")).thenReturn(definition);

        initializer.initialize("pantera");

        ArgumentCaptor<Quest> questCaptor = ArgumentCaptor.forClass(Quest.class);

        verify(questRepository).save(questCaptor.capture());


        Quest quest = questCaptor.getValue();
        assertEquals(definition.getTitle(), quest.getTitle());
        assertEquals(definition.getDescription(), quest.getDescription());
        assertEquals(definition.getFirstStepId(), quest.getFirstStepId());
        assertNotNull(quest.getSteps());
        assertEquals(definition.getSteps().size(), quest.getSteps().size());

        for (QuestStep step : quest.getSteps()) {
            assertSame(
                    quest,
                    step.getQuest(),
                    "QuestStep must reference its Quest"
            );

            assertNotNull(
                    step.getChoices(),
                    "Choices collection must not be null"
            );

            for (Choice choice : step.getChoices()) {
                assertSame(
                        step,
                        choice.getQuestStep(),
                        "Choice must reference its QuestStep"
                );
            }
        }
    }

    @Test
    void shouldNotSaveQuestWhenLoadingFails() {
        when(questDataLoader.load("broken"))
                .thenThrow(
                        new QuestMappingException(
                                "Mapping failed",
                                new RuntimeException()));

        assertThrows(
                QuestMappingException.class,
                () -> initializer.initialize("broken")
        );

        verify(questRepository, never()).save(any());
    }


}

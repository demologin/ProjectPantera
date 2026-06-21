
import com.javarush.aleinik.data.initializer.QuestDataInitializer;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.exception.QuestMappingException;
import com.javarush.aleinik.repository.QuestRepository;
import com.javarush.aleinik.repository.QuestStepRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestDataInitializerTest {

    @Mock
    QuestDataLoader questDataLoader;

    @Mock
    QuestRepository questRepository;

    @Mock
    QuestStepRepository questStepRepository;

    @InjectMocks
    QuestDataInitializer initializer;

    @Test
    void shouldProcessQuestDefinitionAndCreateQuest(){

        when(questDataLoader.load("pantera"))
                .thenReturn(TestUtil.createQuestDefinition());
        when(questRepository.save(any())).thenReturn(TestUtil.createSavedQuest());

        initializer.initialize("pantera");

        verify(questRepository).save(any());
        verify(questStepRepository, times(3)).save(any());

    }

    @Test
    void shouldNotSaveQuestWhenLoadingFails(){
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
        verify(questStepRepository, never()).save(any());
    }


}

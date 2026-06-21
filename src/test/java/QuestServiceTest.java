import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.repository.QuestRepository;
import com.javarush.aleinik.service.QuestService;
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
public class QuestServiceTest {

    Quest EXPECTED_QUEST = TestUtil.createSavedQuest();

    @Mock
    QuestRepository questRepository;

    @InjectMocks
    QuestService questService;

    @Test
    void shouldReturnAllQuests(){

        when(questRepository.findAll()).thenReturn(List.of(EXPECTED_QUEST));

        List<Quest> actualQuests = questService.getAllQuests();

        assertEquals(1, actualQuests.size());
        assertEquals(EXPECTED_QUEST, actualQuests.get(0));
        verify(questRepository).findAll();

    }

    @Test
    void shouldReturnFirstStepId() {
        when(questRepository.findById(any()))
                .thenReturn(EXPECTED_QUEST);

        Long actual =
                questService.getFirstStepId(EXPECTED_QUEST.getId());

        assertEquals(
                EXPECTED_QUEST.getFirstStepId(),
                actual
        );

        verify(questRepository)
                .findById(EXPECTED_QUEST.getId());
    }

}

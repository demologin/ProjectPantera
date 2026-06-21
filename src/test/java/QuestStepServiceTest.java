import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;
import com.javarush.aleinik.service.QuestStepService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestUtil;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestStepServiceTest {


    QuestStep EXPECTED_STEP = TestUtil.winStep;


    @Mock
    QuestStepRepository questStepRepository;

    @InjectMocks
    QuestStepService questStepService;

    @Test
    void shouldGetQuestStepById(){
        when(questStepRepository
                .findStepByQuestId(EXPECTED_STEP.getQuestId(), EXPECTED_STEP.getId()))
                .thenReturn(EXPECTED_STEP);

        val actual = questStepService.getQuestStepById(EXPECTED_STEP.getQuestId(), EXPECTED_STEP.getId());

        assertEquals(EXPECTED_STEP, actual);
        verify(questStepRepository).findStepByQuestId(EXPECTED_STEP.getQuestId(), EXPECTED_STEP.getId());

    }
}

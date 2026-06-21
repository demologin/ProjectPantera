import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.data.loader.QuestDataLoader;
import com.javarush.aleinik.exception.InvalidResourceNameException;
import com.javarush.aleinik.exception.QuestMappingException;
import com.javarush.aleinik.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuestDataLoaderTest {
    private final QuestDataLoader loader = new QuestDataLoader();

    @Test
    void shouldLoadPanteraQuestFromYaml() {

        QuestDefinition quest = loader.load("pantera");

        assertNotNull(quest);
        assertEquals(1L, quest.getId());
        assertEquals("Как приручить пантеру", quest.getTitle());
        assertEquals(1L, quest.getFirstStepId());

        assertNotNull(quest.getSteps());
        assertFalse(quest.getSteps().isEmpty());

    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void shouldThrowExceptionWhenFileNameIsBlank(String fileName){
        assertThrows(
                InvalidResourceNameException.class,
                () -> loader.load(fileName)
        );
    }

    @Test
    void shouldThrowExceptionWhenFileNameIsNull(){
        assertThrows(
                InvalidResourceNameException.class,
                () -> loader.load(null)
        );
    }

    @Test
    void shouldThrowExceptionWhenFileInvalidFileFormat(){
        assertThrows(
                QuestMappingException.class,
                () -> loader.load("broken")
        );
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist(){
        assertThrows( ResourceNotFoundException.class,
                () -> loader.load("unknown")
        );
    }

}

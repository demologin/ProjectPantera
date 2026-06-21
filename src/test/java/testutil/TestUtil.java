package testutil;

import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.model.Choice;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.model.enums.QuestStepResult;

import java.util.List;

public class TestUtil {
    public static Choice choice1 = Choice.builder()
            .id(1L)
            .text("Attack")
            .nextStepId(2L)
            .build();

    public static Choice choice2 = Choice.builder()
            .id(2L)
            .text("Feed")
            .nextStepId(3L)
            .build();

    public static QuestStep firstStep = QuestStep.builder()
            .id(1L)
            .text("Panther")
            .result(QuestStepResult.CONTINUE)
            .choices(List.of(choice1, choice2))
            .build();

    public static QuestStep loseStep = QuestStep.builder()
            .id(2L)
            .text("Lose")
            .result(QuestStepResult.LOSE)
            .choices(List.of())
            .build();

    public static QuestStep winStep = QuestStep.builder()
            .id(3L)
            .text("Win")
            .result(QuestStepResult.WIN)
            .choices(List.of())
            .build();


    private TestUtil(){}

    public static QuestDefinition createQuestDefinition() {

        return QuestDefinition.builder()
                .id(1L)
                .title("Pantera")
                .description("Test quest")
                .firstStepId(1L)
                .steps(List.of(firstStep, loseStep, winStep))
                .build();
    }

    public static Quest createSavedQuest(){
        return Quest
                .builder()
                .id(1L)
                .title("Title")
                .description("description")
                .firstStepId(1L)
                .build();
    }


}

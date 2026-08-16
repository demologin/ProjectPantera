package testutil;

import com.javarush.aleinik.data.definition.QuestDefinition;
import com.javarush.aleinik.model.Choice;
import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.model.enums.QuestStepResult;

import java.util.ArrayList;
import java.util.List;

public final class TestUtil {

    private TestUtil() {
    }

    public static QuestDefinition createQuestDefinition() {
        Choice attackChoice = Choice.builder()
                .choiceId(1L)
                .text("Attack")
                .nextStepId(2L)
                .build();

        Choice feedChoice = Choice.builder()
                .choiceId(2L)
                .text("Feed")
                .nextStepId(3L)
                .build();

        QuestStep firstStep = QuestStep.builder()
                .stepId(1L)
                .text("Panther")
                .result(QuestStepResult.CONTINUE)
                .choices(
                        new ArrayList<>(
                                List.of(
                                        attackChoice,
                                        feedChoice
                                )
                        )
                )
                .build();

        QuestStep loseStep = QuestStep.builder()
                .stepId(2L)
                .text("Lose")
                .result(QuestStepResult.LOSE)
                .choices(new ArrayList<>())
                .build();

        QuestStep winStep = QuestStep.builder()
                .stepId(3L)
                .text("Win")
                .result(QuestStepResult.WIN)
                .choices(new ArrayList<>())
                .build();

        return QuestDefinition.builder()
                .id(1L)
                .title("Pantera")
                .description("Test quest")
                .firstStepId(1L)
                .steps(
                        List.of(
                                firstStep,
                                loseStep,
                                winStep
                        )
                )
                .build();
    }

    public static Quest createSavedQuest() {
        return Quest.builder()
                .id(1L)
                .title("Title")
                .description("Description")
                .firstStepId(1L)
                .build();
    }

    public static QuestStep createStep(Long stepId) {
        return QuestStep.builder()
                .stepId(stepId)
                .text("Win")
                .result(QuestStepResult.WIN)
                .choices(new ArrayList<>())
                .build();
    }
}


import entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Repository;
import services.RepositoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionTest {
    Repository repository;
    RepositoryService service;

    @BeforeEach
    public void init() {
        String[] question1 = {"1", "question1", "2"};
        String[] question2 = {"2", "question2", "2"};
        List<String[]> records = Arrays.asList(question1, question2);
        repository = new Repository(records);
        service = new RepositoryService(repository);
    }

    @Test
    public void getAnswersForQuestionTest() {
        Question question = service.getQuestionById("1");
        assertTrue(question.getAnswersId().contains("101") && question.getAnswersId().contains("102"));
    }

    @Test
    public void equalsTest() {
        Question question = service.getQuestionById("1");
        Question question2 = new Question();
        question2.setId("1");
        question2.setQuestion("question1");
        question2.setQuantityAnswers(2);
        List<String> answersId = new ArrayList<>();
        answersId.add("101");
        answersId.add("102");
        question2.setAnswersId(answersId);
        assertEquals(question, question2);
    }


}

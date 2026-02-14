import entity.Answer;
import entity.Question;
import entity.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Repository;
import services.RepositoryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnswerTest {
    Repository repository;
    RepositoryService service;

    @BeforeEach
    public void init() {
        String[] question1 = {"1", "question1", "2"};
        String[] question2 = {"2", "question2", "2"};
        String[] answer1 = {"101", "answer1", "2"};
        String[] answer2 = {"102", "answer2", "win", "answer by win"};
        String[] answer3 = {"201", "answer1", "lose", "answer by lose"};
        List<String[]> records = Arrays.asList(question1, question2, answer1, answer2, answer3);
        repository = new Repository(records);
        service = new RepositoryService(repository);
    }

    @Test
    public void equalsTest() {
        Answer answer = service.getAnswerById("101");
        Answer answer2 = new Answer();
        answer2.setId("101");
        answer2.setAnswer("answer1");
        answer2.setResult(Result.NEXT_QUESTION);
        answer2.setIdQuestion("2");
        assertEquals(answer2, answer);
    }

    @Test
    public void answerGetTextResultTest() {
        Answer answer = service.getAnswerById("102");
        String resultText = answer.getResultText();
        assertEquals("answer by win", resultText);
    }

    @Test
    public void getAnswerResultLoseTest() {
        Answer answer = service.getAnswerById("201");
        assertEquals(Result.LOSE, answer.getResult());
    }

    @Test
    public void getAnswerResultWinTest() {
        Answer answer = service.getAnswerById("102");
        assertEquals(Result.WIN, answer.getResult());
    }

    @Test
    public void getAnswerResultNextQuestionTest() {
        Answer answer = service.getAnswerById("101");
        assertEquals(Result.NEXT_QUESTION, answer.getResult());
    }

    @Test
    public void getQuestionFromAnswerTest() {
        Question question = service.getQuestionById("2");
        Answer answer = service.getAnswerById("101");
        Question question1 = service.getQuestionById(answer.getIdQuestion());
        assertEquals(question, question1);
    }

}

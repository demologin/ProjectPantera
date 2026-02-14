import entity.Answer;
import entity.Question;
import entity.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.Repository;
import services.RepositoryService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepositoryServiceTest {
    Repository repository;

    @BeforeEach
    public void init() {
        String[] question1 = {"1", "question1", "2"};
        String[] answer1 = {"101", "answer1", "2"};
        String[] answer2 = {"102", "answer2", "win", "answer by win"};
        List<String[]> records = Arrays.asList(question1, answer1, answer2);
        repository = new Repository(records);
    }

    @Test
    public void getQuestionByIdTest() {
        Question question = new Question();
        question.setQuestion("question1");
        question.setId("1");
        question.setQuantityAnswers(2);
        List<String> answersId = new ArrayList<>();
        answersId.add("101");
        answersId.add("102");
        question.setAnswersId(answersId);
        RepositoryService service = new RepositoryService(repository);
        assertEquals(question, service.getQuestionById(question.getId()));
    }

    @Test
    public void getAnswerByIdTest() {
        Answer answer = new Answer();
        answer.setId("101");
        answer.setAnswer("answer1");
        answer.setIdQuestion("2");
        answer.setResult(Result.NEXT_QUESTION);
        RepositoryService service = new RepositoryService(repository);
        assertEquals(answer, service.getAnswerById(answer.getId()));
    }

    @Test
    public void getListQuestionsTest() {
        RepositoryService service = new RepositoryService(repository);
        Question question = new Question();
        question.setQuestion("question1");
        question.setId("1");
        List<String> answersId = new ArrayList<>();
        answersId.add("101");
        answersId.add("102");
        question.setAnswersId(answersId);
        question.setQuantityAnswers(2);
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        assertEquals(questions, service.getQuestions());
    }

    @Test
    public void getListAnswersTest() {
        RepositoryService service = new RepositoryService(repository);
        Answer answer1 = new Answer();
        answer1.setId("101");
        answer1.setAnswer("answer1");
        answer1.setIdQuestion("2");
        answer1.setResult(Result.NEXT_QUESTION);
        Answer answer2 = new Answer();
        answer2.setId("102");
        answer2.setAnswer("answer2");
        answer2.setResult(Result.WIN);
        answer2.setResultText("answer by win");
        List<Answer> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        assertEquals(answers, service.getAnswers());
    }

    @Test
    public void illegalQuantityEntriesInArrayTest() {
        String[] record1 = {"1", "question1"};
        String[] record2 = {"1", "question1", "3"};
        List<String[]> records = Arrays.asList(record1, record2);
        Repository repository1 = new Repository(records);
        assertThrows(IllegalArgumentException.class, () -> new RepositoryService(repository1));
    }

    @Test
    public void illegalArgumentQuantityAnswersInQuestionTest() {
        String[] record1 = {"1", "question1", "2"};
        String[] record2 = {"2", "question2", "a"};
        List<String[]> records = Arrays.asList(record1, record2);
        Repository repository1 = new Repository(records);
        assertThrows(NumberFormatException.class, () -> new RepositoryService(repository1));
    }

}

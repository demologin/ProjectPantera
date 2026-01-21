package com.javarush.zyibin.state;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InterviewStateTest {

    private InterviewState interviewState;
    private Question firstQuestion;
    private Question secondQuestion;

    @BeforeEach
    void setUp() {
        Set<Topic> topics = Set.of(Topic.JAVA_CORE);
        firstQuestion = new Question(
                "Что такое JVM?",
                List.of("Java Virtual Machine", "JavaScript VM"),
                0
        );

        secondQuestion = new Question(
                "Что такое JDK?",
                List.of("Java Development Kit", "Java Debug Kit"),
                0
        );

        List<Question> questions = List.of(firstQuestion, secondQuestion);
        interviewState = new InterviewState(topics, questions);
    }

    @Test
    void shouldReturnFirstQuestion_whenTestJustStarted() {

        Question currentQuestion = interviewState.getCurrentQuestion();

        assertEquals(0, interviewState.getCurrentIndex());
        assertEquals(firstQuestion, currentQuestion);
    }

    @Test
    void shouldMoveToNextQuestion_whenMoveToNextQuestionCalled() {

        interviewState.moveToNextQuestion();

        assertEquals(1, interviewState.getCurrentIndex());
        assertSame(secondQuestion, interviewState.getCurrentQuestion());
    }

    @Test
    void shouldIncreaseScore_whenIncrementScoreCalled() {

        interviewState.incrementScore();
        interviewState.incrementScore();

        assertEquals(2, interviewState.getScore());
    }

    @Test
    void shouldNotBeFinished_whenQuestionsRemain() {

        boolean finished = interviewState.isFinished();

        assertFalse(finished);
    }

    @Test
    void shouldBeFinished_whenAllQuestionsAnswered() {

        interviewState.moveToNextQuestion();
        interviewState.moveToNextQuestion();

        assertTrue(interviewState.isFinished());
    }

    @Test
    void shouldReturnCorrectTotalQuestionsCount() {
        assertEquals(2, interviewState.getTotalQuestions());
    }
}

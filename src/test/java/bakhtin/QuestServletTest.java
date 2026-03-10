package bakhtin;

import bakhtin.Quest.Question;
import bakhtin.Quest.Question.Answer;
import bakhtin.exсeptions.IllegalActionException;
import bakhtin.exсeptions.NoAnswerGivenException;
import bakhtin.exсeptions.NoQuestionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QuestServletTest {

    @Test
    void getNextQuestion_validAnswer() {
        Question nextQuestion = Question.builder().question("testQuestion").build();
        Answer answer = Answer.builder().answer("testAnswer").nextQuestion(nextQuestion).build();
        Question currentQuestion = Question.builder().question("currentTestQuestion").answer(answer)
                .build();
        QuestServlet questServlet = new QuestServlet();
        Long id = answer.getId();
        Question expected = questServlet.getNextQuestion(currentQuestion, id);
        Assertions.assertEquals(expected, nextQuestion);
    }

    @Test
    void getNextQuestion_validNoAnswerGivenException() {
        Question currentQuestion = Question.builder().question("currentTestQuestion").build();
        QuestServlet questServlet = new QuestServlet();
        Long id = Long.MIN_VALUE;
        Assertions.assertThrows(NoAnswerGivenException.class, () -> {
            Question expected = questServlet.getNextQuestion(currentQuestion, id);
        });
    }

    @Test
    void getNextQuestion_validNoQuestionException() {
        Answer answer = Answer.builder().answer("testAnswer").build();
        Question currentQuestion = Question.builder().question("currentTestQuestion").answer(answer)
                .build();
        QuestServlet questServlet = new QuestServlet();
        Long id = answer.getId();
        Assertions.assertThrows(NoQuestionException.class, () -> {
            questServlet.getNextQuestion(currentQuestion, id);
        });
    }

    @Test
    void getAction_validAnswer() {
        QuestServlet questServlet = new QuestServlet();
        Question nextQuestion = Question.builder().question("testQuestion").build();
        var actual = questServlet.getAction("restart");
        Assertions.assertEquals(Actions.RESTART, actual);
    }

    @Test
    void getAction_illegalActionException() {
        QuestServlet questServlet = new QuestServlet();

        Assertions.assertThrows(IllegalActionException.class, () -> {
            questServlet.getAction("***");
        });
    }
}
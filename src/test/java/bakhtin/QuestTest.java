package bakhtin;

import static org.junit.jupiter.api.Assertions.*;

import bakhtin.Quest.Question;
import org.junit.jupiter.api.Test;

class QuestTest {

    @Test
    void testBuilderName() {
        Quest quest = Quest.builder().name("Test").build();
        assertEquals("Test", quest.getName());
    }

    @Test
    void testBuilderQuestionWithAnswer() {
        Question.Answer answer = Question.Answer.builder().answer("TestAnswer").build();
        Question question = Question.builder().question("TestQuestion").answer(answer).build();
        assertTrue(question.getAnswers().containsValue(answer));
    }

    @Test
    void testQuestTerminalQuestion() {
        Question question = Question.builder().question("TestQuestion").win(true).build();
        assertTrue(question.isFinish());
        assertTrue(question.isWin());
    }

    @Test
    void testQuestNextQuestion() {
        Question question = Question.builder().question("TestQuestion").build();
        Question.Answer answer = Question.Answer.builder().answer("TestAnswer").nextQuestion(question).build();
        assertEquals(answer.getNextQuestion(), question);
    }
}
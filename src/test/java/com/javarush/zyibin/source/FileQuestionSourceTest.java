package com.javarush.zyibin.source;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileQuestionSourceTest {

    private final FileQuestionSource source = new FileQuestionSource();

    @Test
    void shouldLoadQuestionsFromFileForExistingTopic() {

        List<Question> questions = source.loadQuestions(Topic.JAVA_CORE);

        assertNotNull(questions);
        assertFalse(questions.isEmpty());

        Question question = questions.get(0);
        assertNotNull(question.getQuestionText());
        assertNotNull(question.getAnswers());
    }

}

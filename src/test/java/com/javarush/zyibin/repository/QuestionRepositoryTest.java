package com.javarush.zyibin.repository;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;
import com.javarush.zyibin.source.QuestionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionRepositoryTest {

    private QuestionSource questionSource;
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        questionSource = mock(QuestionSource.class);
        questionRepository = new QuestionRepository(questionSource);
    }

    @Test
    void shouldLoadQuestionsForGivenTopic() {
        Topic topic = Topic.JAVA_CORE;
        List<Question> expectedQuestions = List.of(
                mock(Question.class),
                mock(Question.class)
        );

        when(questionSource.loadQuestions(topic)).thenReturn(expectedQuestions);

        List<Question> actualQuestions = questionRepository.getQuestions(topic);

        assertEquals(expectedQuestions, actualQuestions);
        verify(questionSource, times(1)).loadQuestions(topic);
    }
}

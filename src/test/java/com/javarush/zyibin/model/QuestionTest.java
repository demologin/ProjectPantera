package com.javarush.zyibin.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuestionTest {

    @Test
    void shouldCreateQuestionUsingConstructor() {
        String text = "Что такое JVM?";
        List<String> answer = List.of("VM", "JDK", "JRE");
        int correctIndex = 0;

        Question question = new Question(text, answer, correctIndex);

        assertEquals(text, question.getQuestionText());
        assertEquals(answer, question.getAnswers());
        assertEquals(correctIndex, question.getCorrectAnswerIndex());
    }

    @SneakyThrows
    @Test
    void shouldDeserializeQuestionFromJson() {

        String json = """
            {
              "questionText": "Что такое JVM?",
              "answers": ["VM", "JDK", "JRE"],
              "correctAnswerIndex": 0
            }
            """;

        ObjectMapper mapper= new ObjectMapper();
        Question question = mapper.readValue(json, Question.class);

        assertEquals("Что такое JVM?", question.getQuestionText());
        assertEquals(List.of("VM", "JDK", "JRE"), question.getAnswers());
        assertEquals(0, question.getCorrectAnswerIndex());
    }

}

package com.javarush.zyibin.source;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.validation.QuestionValidator;

import java.io.InputStream;
import java.util.List;

public class FileQuestionSource implements QuestionSource{

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String QUESTIONS_FILE = "/questions.json";


    @Override
    public List<Question> loadQuestions() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(QUESTIONS_FILE.substring(1));

        if (inputStream == null) {
            throw new IllegalStateException("File questions.json not found in resources");
        }

        try {
            List<Question> questions =  objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Question>>() {
                    }
            );
            QuestionValidator.validate(questions);
            return questions;
        } catch (Exception e) {
            throw new IllegalStateException("Error reading questions.json", e);
        }
    }
}

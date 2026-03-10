package com.javarush.khmelov.repository;

import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Answer;

public class AnswerRepository extends BaseRepository<Answer> {


    public AnswerRepository(SessionCreator sessionCreator) {
        super(sessionCreator, Answer.class);
    }
}

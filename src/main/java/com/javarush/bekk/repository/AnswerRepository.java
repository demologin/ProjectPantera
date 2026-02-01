package com.javarush.bekk.repository;

import com.javarush.bekk.entity.Answer;

import java.util.stream.Stream;

public class AnswerRepository extends BaseRepository<Answer> {

    @Override
    public Stream<Answer> find(Answer pattern) {
        return map.values()
                .stream()
                .filter(answer -> nullOrEquals(pattern.getId(), answer.getId()))
                .filter(answer -> nullOrEquals(pattern.getQuestionId(), answer.getQuestionId()))
                .filter(answer -> nullOrEquals(pattern.getText(), answer.getText()))
                .filter(answer -> nullOrEquals(pattern.getQuestionId(), answer.getQuestionId()));
    }
}

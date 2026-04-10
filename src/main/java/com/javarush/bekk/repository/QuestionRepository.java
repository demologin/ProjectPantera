package com.javarush.bekk.repository;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class QuestionRepository extends BaseRepository<Question> {

    private final Map<Long, Question> map = new HashMap<>();

    public static final AtomicLong id = new AtomicLong(System.currentTimeMillis());

}

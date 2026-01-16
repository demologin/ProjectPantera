package com.javarush.zyibin.source;

import com.javarush.zyibin.model.Question;
import com.javarush.zyibin.model.Topic;

import java.util.List;

public interface QuestionSource {

    List<Question> loadQuestions(Topic topic);
}

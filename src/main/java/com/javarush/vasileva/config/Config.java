package com.javarush.vasileva.config;

import com.javarush.vasileva.entity.Answer;
import com.javarush.vasileva.entity.Quest;
import com.javarush.vasileva.entity.Question;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.service.QuestionService;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class Config {
    private final QuestService questService = Winter.find(QuestService.class);
    private final QuestionService questionService = Winter.find(QuestionService.class);
    private final AnswerService answerService = Winter.find(AnswerService.class);
    private final QuestMapper questMapper = Winter.find(QuestMapper.class);

    public static final String QUEST_FILE_NAME_1 = "/Users/katiavasileva/IdeaProjects/quest/src/main/resources/quest-1.json";
    public static final String QUEST_FILE_NAME_2 = "/Users/katiavasileva/IdeaProjects/quest/src/main/resources/quest-2.json";
    public static final String[] QUEST_FILES = {QUEST_FILE_NAME_1, QUEST_FILE_NAME_2};

    public void fillRepository() {
        try {
            for (String fileName : QUEST_FILES) {
                Quest quest = questMapper.readFromJson(fileName);
                questService.create(quest);
                for (Question question : quest.getQuestions()) {
                    questionService.create(question);
                    List<Answer> answers = question.getAnswers();
                    if (answers == null) {
                        continue;
                    }
                    for (Answer answer : answers) {
                        answer.setQuestionId(question.getGeneratedId());
                        answerService.create(answer);
                    }
                }
                long startQuestionId = quest.getQuestions().get(0).getGeneratedId();
                quest.setStartQuestionId(startQuestionId);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

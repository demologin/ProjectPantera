package com.javarush.vasileva.config;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.mapper.QuestMapper;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.service.QuestionService;
import com.javarush.vasileva.service.UserService;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.JSON_SAVE_ERROR;

@AllArgsConstructor
public class Config {
    private final UserService userService = Winter.find(UserService.class);
    private final QuestService questService = Winter.find(QuestService.class);
    private final QuestionService questionService = Winter.find(QuestionService.class);
    private final AnswerService answerService = Winter.find(AnswerService.class);
    private final QuestMapper questMapper = Winter.find(QuestMapper.class);

    public static final String[] QUEST_FILES = {QUEST_FILE_NAME_1, QUEST_FILE_NAME_2, QUEST_FILE_NAME_3};

    public void fillRepository() {
        try {
            for (String fileName : QUEST_FILES) {
                Quest quest = questMapper.readFromJson(fileName);
                questService.create(quest);
                setQuestParameters(quest);
            }
            userService.create(buildUser("Carl", "admin@gmail.com", "admin", Role.ADMIN));
            userService.create(buildUser("Alisa", "alisa@gmail.com", "qwerty", Role.USER));
            userService.create(buildUser("Bob", "bob@gmail.com", "12345", Role.USER));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private User buildUser(String login, String email, String password, Role role) {
        return User.builder()
                .login(login)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public void setQuestParameters(Quest quest) {
        List<Question> questions = quest.getQuestions();
        if (questions == null || questions.isEmpty()) {
            throw new AppException(JSON_SAVE_ERROR);
        }

        for (Question question : questions) {

            question.setQuest(quest);
            List<Answer> answers = question.getAnswers();
            if (answers == null) {
                questionService.create(question);
                continue;
            }
            for (Answer answer : answers) {
                answer.setQuestion(question);
                answerService.create(answer);
            }
            questionService.create(question);
        }
        long startQuestionId = quest.getQuestions().get(0).getGeneratedId();
        quest.setStartQuestionId(startQuestionId);
    }
}

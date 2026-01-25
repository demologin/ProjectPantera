package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public class PlayGame implements Command {
    private final QuestService questService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public PlayGame(QuestService questService, QuestionService questionService, AnswerService answerService) {
        this.questService = questService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String questIdStr = req.getParameter("id");
        String questionIdStr = req.getParameter("questionId");

        Quest quest = questService.getValidatedQuest(questIdStr)
                .orElseThrow(() -> new IllegalArgumentException("Quest is not found: id=" + questIdStr));
        req.setAttribute("quest", quest);

        if (questionIdStr == null || questionIdStr.isEmpty()) {
            return getView();
        }

        Optional<Question> currentQuestion = questionService.findCurrentQuestion(questionIdStr, quest);
        if (currentQuestion.isPresent()) {
            req.setAttribute("question", currentQuestion.get());
            List<Answer> answers = currentQuestion.get().getAnswers();
            if (answers == null || answers.isEmpty()) {
                req.setAttribute("noAnswers", true);
            } else {
                req.setAttribute("answers", answers);
            }
        } else {
            req.setAttribute("gameOver", true);
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        String questIdStr = req.getParameter("questId");
        String answerIdStr = req.getParameter("selectedAnswerId");

        if (questIdStr == null || answerIdStr == null) {
            return getView() + "?id=" + questIdStr; // Return to the same page
        }

        long questId = questService.parseQuestIdStrToLong(questIdStr);
        long answerId = answerService.parseAnswerIdStrToLong(answerIdStr);

        Optional<Answer> answer = answerService.get(answerId);
        if (answer.isEmpty()) {
            return getView() + "?id=" + questId;
        }
        long nextQuestionId = questionService.findNextQuestionId(questId, answer.get());
        return getView() + "?id=" + questId + "&questionId=" + nextQuestionId;
    }
}

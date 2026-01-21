package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;

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
        if (questIdStr != null && !questIdStr.isEmpty()) {
            long questId = Long.parseLong(questIdStr);
            Quest quest = questService.get(questId).orElseThrow();
            req.setAttribute("quest", quest);

            Long currentQuestionId;
            if (questionIdStr == null || questionIdStr.isEmpty()) {
                currentQuestionId = quest.getStartQuestionId();
            } else {
                currentQuestionId = Long.parseLong(questionIdStr);
            }

            System.out.println("current" + currentQuestionId);

            Optional<Question> currentQuestion = questionService.get(currentQuestionId);
            if (currentQuestion.isPresent()) {
                req.setAttribute("question", currentQuestion.get());
                req.setAttribute("answers", currentQuestion.get().getAnswers());
            } else {
                req.setAttribute("gameOver", true);
            }
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        String questIdStr = req.getParameter("questId");
        String answerIdStr = req.getParameter("selectedAnswerId");

        if (questIdStr == null || answerIdStr == null) {
            return getView() + "?id=" + questIdStr; // Возвращаем на тот же экран
        }

        long questId = Long.parseLong(questIdStr);
        long answerId = Long.parseLong(answerIdStr);

        Optional<Answer> answerOpt = answerService.get(answerId);
        if (answerOpt.isEmpty()) {
            return getView() + "?id=" + questId;
        }

        Answer answer = answerOpt.get();

        String nextQuestionIdStr = answer.getNextQuestionId();
        if (nextQuestionIdStr == null || nextQuestionIdStr.isEmpty()) {
            return getView() + "?id=" + questId + "&gameOver=true";
        }

        Optional<Question> nextQuestionOpt = questionService.getByIdAndQuestId(nextQuestionIdStr, questId);
        long nextQuestionId = nextQuestionOpt.get().getGeneratedId();

        return getView() + "?id=" + questId + "&questionId=" + nextQuestionId;
    }
}

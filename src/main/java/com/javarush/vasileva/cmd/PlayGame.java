package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestService;
import com.javarush.vasileva.service.QuestionService;
import com.javarush.vasileva.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

import static com.javarush.vasileva.util.Key.*;

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
        String questIdStr = req.getParameter(QUEST_ID);
        String questionIdStr = req.getParameter(QUESTION_ID);

        Quest quest = questService.findById(questIdStr)
                .orElseThrow(() -> new AppException("Quest is not found: id=" + questIdStr));
        req.setAttribute(QUEST, quest);

        if (questionIdStr == null || questionIdStr.isEmpty()) {
            return getView();
        }

        Optional<Question> currentQuestion = questionService.findCurrentQuestion(questionIdStr, quest);
        if (currentQuestion.isPresent()) {
            req.setAttribute(QUESTION, currentQuestion.get());
            List<Answer> answers = currentQuestion.get().getAnswers();
            if (answers == null || answers.isEmpty()) {
                req.setAttribute(NO_ANSWERS, true);
            } else {
                req.setAttribute(ANSWERS, answers);
            }
        } else {
            req.setAttribute(GAME_OVER, true);
        }
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) throws AppException {
            String questIdStr = req.getParameter(QUEST_ID);
            String answerIdStr = req.getParameter(SELECTED_ANSWER_ID);

            if (questIdStr == null || answerIdStr == null) {
                return getView() + "?" + QUEST_ID + "=" + questIdStr; // Return to the same page
            }

            long questId = RequestHelpers.parseStringToLong(questIdStr);
            long answerId = answerService.parseAnswerIdStrToLong(answerIdStr);

            Optional<Answer> answer = answerService.get(answerId);
            if (answer.isEmpty()) {
                return getView() + "?" + QUEST_ID + "=" + questId;
            }
            long nextQuestionId = questionService.findNextQuestionId(questId, answer.get());
            return getView() + "?" + QUEST_ID + "=" + questId + "&"+ QUESTION_ID + "=" + nextQuestionId;
    }
}

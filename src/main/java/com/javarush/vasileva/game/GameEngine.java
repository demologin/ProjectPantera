package com.javarush.vasileva.game;

import com.javarush.vasileva.entity.*;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.AnswerService;
import com.javarush.vasileva.service.QuestionService;
import com.javarush.vasileva.service.UserStatsService;
import com.javarush.vasileva.util.Value;

import static com.javarush.vasileva.util.Value.*;

public class GameEngine {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserStatsService userStatsService;

    public GameEngine(QuestionService questionService, AnswerService answerService, UserStatsService userStatsService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userStatsService = userStatsService;
    }

    public GameState startGame(User user, Quest quest) {
        Question startQuestion = questionService.findById(quest.getStartQuestionId())
                .orElseThrow(() -> new AppException(QUESTION_NOT_FOUND));
        boolean isFinalQuestion = questionService.isFinalQuestion(startQuestion);

        return new GameState(quest, startQuestion, user, isFinalQuestion);
    }

    public GameState advanceGame(GameState currentState, Long answerId) {
        Answer answer = answerService.findById(answerId).orElseThrow(() -> new AppException(ANSWER_NOT_FOUND));
        String nextQuestionLabel = answer.getNextQuestionLabel();
        Question nextQuestion = questionService.getByQuestionLabelAndQuestId(nextQuestionLabel, currentState.getCurrentQuest().getId())
                .orElseThrow(() -> new AppException(Value.QUESTION_NOT_FOUND + nextQuestionLabel));
        boolean isFinalQuestion = questionService.isFinalQuestion(nextQuestion);

        if (isFinalQuestion) {
            UserStats stats = userStatsService.getUserStats(currentState.getUser().getId())
                    .orElseThrow(() -> new AppException(STATS_NOT_FOUND));
            userStatsService.updateUserStats(nextQuestion, stats);
        }

        return new GameState(currentState.getCurrentQuest(), nextQuestion, currentState.getUser(), isFinalQuestion);
    }
}

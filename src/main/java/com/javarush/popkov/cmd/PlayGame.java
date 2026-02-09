package com.javarush.popkov.cmd;

import com.javarush.popkov.entity.Game;
import com.javarush.popkov.entity.Question;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.service.GameService;
import com.javarush.popkov.service.QuestionService;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.Key;
import com.javarush.popkov.util.RequestHelpers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@SuppressWarnings("unused")
@Slf4j
public class PlayGame implements Command {

    private final GameService gameService;
    private final QuestionService questionService;

    public PlayGame(GameService gameService, QuestionService questionService) {
        this.gameService = gameService;
        this.questionService = questionService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        Long questId = Long.parseLong(request.getParameter(Key.QUEST_ID));
        Optional<User> user = RequestHelpers.getUser(request.getSession());
        if (user.isPresent()) {
            Long userId = user.get().getId();
            Optional<Game> game = gameService.getGame(questId, userId);
            if (game.isPresent()) {
                showOneQuestion(request, game.get());
                return getView();
            } else {
                String message = "Нет незавершенной игры";
                log.warn(message);
                RequestHelpers.createError(request, message);
                return Go.HOME;
            }
        } else {
            String message = "Сначала нужно войти в аккаунт";
            log.warn(message);
            RequestHelpers.createError(request, message);
            return Go.LOGIN;
        }
    }

    @Override
    public String doPost(HttpServletRequest request) {
        Long gameId = RequestHelpers.getId(request);
        Long answerId = RequestHelpers.getId(request, Key.ANSWER);
        Optional<Game> game = gameService.processOneStep(gameId, answerId);
        if (game.isPresent()) {
            if (answerId == 0) {
                String message = "Нужно выбрать какой-то ответ";
                log.warn(message);
                RequestHelpers.createError(request, message);
            }
            Game currentGame = game.get();
            return "%s?questId=%d&id=%d".formatted(Go.PLAY_GAME, game.get().getQuestId(), game.get().getId());
        } else {
            String message = "Нет такой игры";
            log.warn(message);
            RequestHelpers.createError(request, message);
            return Go.HOME;
        }
    }

    private void showOneQuestion(HttpServletRequest request, Game game) {
        request.setAttribute(Key.GAME, game);
        Optional<Question> question = questionService.get(game.getCurrentQuestionId());
        request.setAttribute(Key.QUESTION, question.orElseThrow());
    }

}

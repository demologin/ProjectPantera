package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Game;
import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.model.Question;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.*;

import java.util.Map;
import java.util.Optional;

public class GameService {
    private final GameRepository gameRepository;
    private final QuestService questService;
    private final QuestionService questionService;
    private final UserService userService;

    public GameService(GameRepository gameRepository,
                       QuestService questService,
                       QuestionService questionService,
                       UserService userService) {
        this.gameRepository = gameRepository;
        this.questService = questService;
        this.questionService = questionService;
        this.userService = userService;
    }

    public Optional<Game> get(Long id){
        return gameRepository.get(id);
    }

    public Optional<Game> getGame(Long questId, Long userId) {
        if (gameRepository.getAll().containsKey(questId)){
            Game game = gameRepository.getAll().get(questId);
            if (game.getUserId().equals(userId)){
                return Optional.of(game);
            }
        }
        User user = userService.get(userId).get();
        Quest quest = questService.get(questId).get();
        Long startQuestionId = quest.getStartQuestionId();
        Question firstQuestion = questionService.get(startQuestionId).get();
        Game newGame = Game.builder()
                .questId(questId)
                .questName(quest.getName())
                .currentQuestionId(startQuestionId)
                .gameState(firstQuestion.getGameState())
                .userId(userId)
                .userName(user.getLogin())
                .build();
        userService.get(userId).get().getGames().add(newGame);
        gameRepository.create(newGame);
        return Optional.of(newGame);
    }

    public Optional<Game> find(String questName, String userName){
        return gameRepository.findBy(questName, userName);
    }

    public void post(Game game){
        game.setId(0L);
        gameRepository.create(game);
    }

    public void delete(Game game){
        gameRepository.delete(game);
    }

    public void update(Game game){
        gameRepository.update(game);
    }

    public Map<Long, Game> getAll(){
        return gameRepository.getAll();
    }
}

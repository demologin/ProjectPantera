package com.javarush.goncharov.service;

import com.javarush.goncharov.model.*;
import com.javarush.goncharov.repository.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameService {
    final GameRepository gameRepository;
    final QuestService questService;
    final QuestionService questionService;
    final AnswerRepository answerRepository;
    final UserService userService;

    public GameService(GameRepository gameRepository,
                       QuestService questService,
                       QuestionService questionService,
                       UserService userService,
                       AnswerRepository answerRepository) {
        this.gameRepository = gameRepository;
        this.questService = questService;
        this.questionService = questionService;
        this.userService = userService;
        this.answerRepository = answerRepository;
    }

    public Optional<Game> get(Long id){
        return gameRepository.get(id);
    }

    public Optional<Game> getGame(Long questId, Long userId) {
        if (gameRepository.getAll().containsKey(questId)){
            Optional<Game> game = gameRepository.findByQuestId(questId).max(Comparator.comparingLong(Game::getId));
            if (game.isPresent() &&
                    game.get().getUserId().equals(userId)){
                return game;
            }
        }
        return getNewGame(questId, userId);
    }

    private Optional<Game> getNewGame(Long questId, Long userId) {
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

    public Optional<Game> processOneStep(Long gameId, Long answerId) {
        Long nextQuestionId = 0L;
        Game game = gameRepository.get(gameId).get();
        if (game.getGameState() == GameState.PLAY) {
            if (answerRepository.get(answerId).isPresent()){
                Answer answer = answerRepository.get(answerId).get();
                nextQuestionId = answer.getNextQuestionId();
            } else {
                nextQuestionId = game.getCurrentQuestionId();
            }
            game.setCurrentQuestionId(nextQuestionId);
            Question question = questionService.get(nextQuestionId).get();
            game.setGameState(question.getGameState());
            gameRepository.update(game);
        } else {
            game = getNewGame(game.getQuestId(), game.getUserId()).get();
        }
        return Optional.ofNullable(game);
    }
}

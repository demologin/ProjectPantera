package com.javarush.goncharov.service;

import com.javarush.goncharov.model.Game;
import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.repository.*;

import java.util.Map;
import java.util.Optional;

public class GameService {
    private final Storage storage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(storage));
    private final QuestionService questionService = new QuestionService(new QuestionRepository(storage));
    private final AnswerRepository answerRepository = new AnswerRepository(storage);
    private final Repository<Game> gameRepository;

    public GameService(Repository<Game> gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<Game> get(Long id){
        return gameRepository.get(id);
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

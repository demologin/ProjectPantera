package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.*;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.repository.FileStatisticRepository;
import com.javarush.matsarskaya.repository.FileUserRepository;
import com.javarush.matsarskaya.repository.StatisticRepository;
import com.javarush.matsarskaya.repository.UserRepository;
import com.javarush.matsarskaya.service.StatisticService;
import com.javarush.matsarskaya.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class HttpResolver {
    private static final UserFileStorage storage = new UserFileStorage();
    private static final UserRepository repository = new FileUserRepository(storage);
    private static final UserService userService = new UserService(repository);
    private static final StatisticRepository statisticRepository = new FileStatisticRepository();
    private static final StatisticService statisticService = new StatisticService(statisticRepository);

    private final Map<String, Command> commandMap = Map.of(
            "/home-page", new HomePage(),
            "/quest-dragon", new QuestDragon(statisticService),
            "/login-page", new LoginPage(userService),
            "/register-page", new RegisterPage(userService),
            "/logout", new LogoutPage(userService),
            "/statistic-page", new StatisticPage(statisticService)
    );

    public Command resolve(String pathInfo) {
        return commandMap.getOrDefault(pathInfo, new HomePage());
    }
}

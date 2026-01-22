package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.*;
import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.repository.FileUserRepository;
import com.javarush.matsarskaya.repository.UserRepository;
import com.javarush.matsarskaya.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class HttpResolver {
    private static final UserFileStorage storage = new UserFileStorage();
    private static final UserRepository repository = new FileUserRepository(storage);
    private static final UserService userService = new UserService(repository);

    private final Map<String, Command> commandMap = Map.of(
            "/home-page", new HomePage(),
            "/quest-dragon", new QuestDragon(),
            "/login-page", new LoginPage(userService),
            "/register-page", new RegisterPage(userService),
            "/logout", new LogoutPage(userService)
    );

    public Command resolve(String pathInfo) {
        return commandMap.getOrDefault(pathInfo, new HomePage());
    }
}

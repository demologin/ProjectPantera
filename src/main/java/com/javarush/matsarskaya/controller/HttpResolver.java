package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.*;

import java.util.HashMap;
import java.util.Map;

public class HttpResolver {
    private final Map<String, Command> commandMap = Map.of(
            "/home-page", new HomePage(),
            "/quest-dragon", new QuestDragon(),
            "/login-page", new LoginPage(),
            "/register-page", new RegisterPage(),
            "/logout", new LogoutPage()
    );

    public Command resolve(String pathInfo) {
        return commandMap.getOrDefault(pathInfo, new HomePage());
    }
}

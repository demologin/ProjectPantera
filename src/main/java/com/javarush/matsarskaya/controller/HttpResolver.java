package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import com.javarush.matsarskaya.cmd.HomePage;
import com.javarush.matsarskaya.cmd.QuestDragon;

import java.util.HashMap;
import java.util.Map;

public class HttpResolver {
    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put("/", new HomePage());
        commandMap.put("/home-page", new HomePage());
        commandMap.put("/quest-dragon", new QuestDragon());
    }

    public Command resolve(String pathInfo) {
        return commandMap.getOrDefault(pathInfo, new HomePage());
    }
}

package com.javarush.buslovskii.command;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    
    private static CommandFactory instance;
    private Map<String, Command> commands;
    
    private CommandFactory() {
        commands = new HashMap<>();
        commands.put("start", new StartCommand());
        commands.put("game", new GameCommand());
        commands.put("restart", new RestartCommand());
        commands.put("selectquest", new SelectQuestCommand());
        commands.put("selectQuest", new SelectQuestCommand());
    }
    
    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }
    
    public Command getCommand(String commandName) {
        if (commandName == null || commandName.isEmpty()) {
            return new StartCommand();
        }
        
        Command command = commands.get(commandName.toLowerCase());
        if (command == null) {
            command = new UnknownCommand();
        }
        
        return command;
    }
}
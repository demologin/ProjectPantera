package com.javarush.popkov.controller;

import com.javarush.popkov.cmd.Command;
import com.javarush.popkov.cmd.StartPage;
import com.javarush.popkov.config.Winter;
import jakarta.servlet.http.HttpServletRequest;

public class HttpResolver {

    public Command resolve(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.equals("/") ? "/start-page" : requestURI;

        String[] parts = requestURI.split("/");
        String lastSegment = null;
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!parts[i].isEmpty()) {
                lastSegment = parts[i];
                break;
            }
        }

        if (lastSegment == null) {
            lastSegment = "start-page";
        }

        String simpleName = convertToCamelCase(lastSegment);
        String fullName = Command.class.getPackageName() + "." + simpleName;

        try {
            Class<?> clazz = Class.forName(fullName);
            return (Command) Winter.find(clazz);
        } catch (ClassNotFoundException e) {
            return (Command) Winter.find(StartPage.class);
        }
    }

    private String convertToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (c == '-' || c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }
}

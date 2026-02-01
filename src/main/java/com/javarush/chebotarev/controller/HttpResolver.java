package com.javarush.chebotarev.controller;

import com.javarush.chebotarev.cmd.Command;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import jakarta.servlet.http.HttpServletRequest;

public class HttpResolver {

    public Command resolve(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.equals("/")
                ? Go.MAIN_MENU
                : requestURI;
        String kebabName = requestURI.split("[?#/]")[1];
        String simpleName = convertKebabStyleToCamelCase(kebabName);
        String fullName = Command.class.getPackageName() + "." + simpleName;
        Class<?> aClass;
        try {
            aClass = Class.forName(fullName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return (Command) ObjectRepository.find(aClass);
    }

    private String convertKebabStyleToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        for (char c : input.toCharArray()) {
            if (c == '-') {
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

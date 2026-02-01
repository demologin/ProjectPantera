package com.javarush.chebotarev.cmd;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command {

    public String doGet(HttpServletRequest req, HttpServlet servlet) {
        return getView();
    }

    public String doPost(HttpServletRequest req) {
        return getView();
    }

    protected String getView() {
        String simpleName = this.getClass().getSimpleName();
        return convertCamelCaseToKebabStyle(simpleName);
    }

    private static String convertCamelCaseToKebabStyle(String string) {
        String snakeName = string.chars()
                .mapToObj(s -> String.valueOf((char) s))
                .flatMap(s -> s.matches("[A-Z]")
                        ? Stream.of("-", s)
                        : Stream.of(s))
                .collect(Collectors.joining())
                .toLowerCase();
        return snakeName.startsWith("-")
                ? snakeName.substring(1)
                : snakeName;
    }
}

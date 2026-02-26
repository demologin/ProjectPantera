package com.javarush.ushanov.cmd;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Команда для отображения стартовой страницы-приветствия.
 * Просто показывает JSP с предысторией квеста и формой ввода имени.
 */
public class StartPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // Просто возвращаем путь к JSP — контроллер сам сделает forward
        return "/WEB-INF/start-page.jsp";
    }
}

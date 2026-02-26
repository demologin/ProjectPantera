package com.javarush.ushanov.cmd;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Интерфейс команды — основа паттерна Command.
 *
 * Паттерн Command — каждое действие (показать страницу, обработать выбор)
 * оформляется как отдельный класс, реализующий этот интерфейс.
 *
 * Метод execute() выполняет действие и возвращает путь к JSP-странице,
 * которую нужно показать пользователю (или путь для redirect).
 */
public interface Command {

    /**
     * Выполнить команду.
     *
     * @param request  HTTP-запрос (содержит параметры, сессию и т.д.)
     * @param response HTTP-ответ
     * @return путь к JSP-странице для отображения, или "redirect:/path" для перенаправления
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}

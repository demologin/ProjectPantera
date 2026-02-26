package com.javarush.ushanov.cmd;

import com.javarush.ushanov.entity.GameSession;
import com.javarush.ushanov.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Команда для начала новой игры.
 *
 * Обрабатывает два случая:
 * 1. Первый запуск: игрок ввёл имя на стартовой странице — создаём новую сессию.
 * 2. Перезапуск: игрок нажал "Играть снова" — сбрасываем прогресс, сохраняем статистику.
 */
public class NewGameCommand implements Command {

    private final SessionService sessionService;

    public NewGameCommand(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String playerName = request.getParameter("playerName");
        GameSession existingSession = sessionService.getSession(request.getSession());

        if (playerName != null && !playerName.trim().isEmpty()) {
            // Пришло имя — это первый запуск или смена имени
            // Создаём новую сессию (или пересоздаём с новым именем)
            sessionService.createSession(request.getSession(), playerName.trim());
        } else if (existingSession != null) {
            // Имя не пришло, но сессия есть — это "Играть снова"
            sessionService.restartGame(request.getSession());
        } else {
            // Нет ни имени, ни сессии — возвращаем на старт с ошибкой
            request.setAttribute("error", "Please, enter your name!");
            return "/WEB-INF/start-page.jsp";
        }

        return "redirect:/quest";
    }
}

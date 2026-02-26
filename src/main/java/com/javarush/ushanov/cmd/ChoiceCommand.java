package com.javarush.ushanov.cmd;

import com.javarush.ushanov.entity.GameSession;
import com.javarush.ushanov.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Команда для обработки выбора игрока.
 *
 * POST /choice — игрок нажал на кнопку с вариантом ответа.
 *
 * Логика:
 * 1. Читаем параметр "option" из POST-запроса (текст кнопки)
 * 2. Через SessionService обновляем текущий шаг в сессии
 * 3. Делаем redirect на GET /quest — паттерн PRG (Post-Redirect-Get)
 *
 * Паттерн PRG важен: если после POST-запроса не делать redirect,
 * и пользователь обновит страницу (F5) — браузер отправит POST повторно,
 * и выбор будет сделан ещё раз. Redirect это предотвращает.
 */
public class ChoiceCommand implements Command {

    private final SessionService sessionService;

    public ChoiceCommand(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        GameSession gameSession = sessionService.getSession(request.getSession());

        if (gameSession == null) {
            return "redirect:/";
        }

        String chosenOption = request.getParameter("option");

        if (chosenOption != null && !chosenOption.isEmpty()) {
            sessionService.processChoice(request.getSession(), chosenOption);
        }

        // PRG паттерн: после обработки POST — redirect на GET
        return "redirect:/quest";
    }
}

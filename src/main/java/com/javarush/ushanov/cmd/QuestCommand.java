package com.javarush.ushanov.cmd;

import com.javarush.ushanov.entity.GameSession;
import com.javarush.ushanov.entity.QuestStep;
import com.javarush.ushanov.service.QuestService;
import com.javarush.ushanov.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Команда для отображения текущего шага квеста.
 *
 * GET /quest — показывает текущий шаг игроку.
 *
 * Логика:
 * 1. Получаем игровую сессию из HttpSession
 * 2. Если сессии нет — перенаправляем на стартовую страницу
 * 3. Загружаем текущий шаг квеста
 * 4. Кладём данные в request.setAttribute() — JSP их прочитает
 * 5. Возвращаем путь к JSP
 */
public class QuestCommand implements Command {

    private final QuestService questService;
    private final SessionService sessionService;

    public QuestCommand(QuestService questService, SessionService sessionService) {
        this.questService = questService;
        this.sessionService = sessionService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        GameSession gameSession = sessionService.getSession(request.getSession());

        // Если сессии нет — игрок не начинал игру, отправляем на старт
        if (gameSession == null) {
            return "redirect:/";
        }

        // Загружаем текущий шаг
        QuestStep currentStep = questService.getStep(gameSession.getCurrentStepId());

        // Кладём данные в request — JSP получит их через ${step} и ${gameSession}
        request.setAttribute("step", currentStep);
        request.setAttribute("gameSession", gameSession);

        return "/WEB-INF/quest-page.jsp";
    }
}
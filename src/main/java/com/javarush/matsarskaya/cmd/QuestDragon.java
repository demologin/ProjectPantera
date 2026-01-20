package com.javarush.matsarskaya.cmd;

import jakarta.servlet.http.HttpServletRequest;

public class QuestDragon implements Command{
    @Override
    public String getView() {
        return "/WEB-INF/quest-dragon.jsp";
    }

    @Override
    public String doGet(HttpServletRequest request) {
        // Для GET-запросов просто возвращаем представление
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        // Обработка POST-запроса с логикой квеста
        String stageParam = request.getParameter("stage");
        String choice = request.getParameter("choice");
        String playerNameInput = request.getParameter("playerNameInput");
        String quest = request.getParameter("quest");

        // Получаем сессию
        var session = request.getSession();

        // Обработка перехода с главной страницы на квест
        if(quest != null && quest.equals("the way of the dragon rider")) {
            // Инициализируем сессию для нового квеста
            session.setAttribute("stage", 0);
            session.setAttribute("trust", 50);
            return getView();
        }
        else if (stageParam != null && Integer.parseInt(stageParam) == 0) {
            session.setAttribute("stage", 1);
        }
        else if (stageParam != null && Integer.parseInt(stageParam) == 1
                 && playerNameInput != null && !playerNameInput.isEmpty()) {

            session.setAttribute("playerName", playerNameInput);
            session.setAttribute("stage", 2);

        } else if(stageParam != null && choice != null) {
            // Обработка других этапов
            int currentStage = Integer.parseInt(stageParam);

            // Обновляем доверие в зависимости от выбора
            if(currentStage >= 2 && currentStage <= 9) {
                Integer trust = (Integer) session.getAttribute("trust");
                if(trust == null) trust = 50;

                int trustChange = Integer.parseInt(choice);
                trust += trustChange;

                // Ограничиваем доверие диапазоном 0-100
                trust = Math.max(0, Math.min(100, trust));

                session.setAttribute("trust", trust);
            }

            // Переходим к следующему этапу
            session.setAttribute("stage", currentStage + 1);
        } else {
            // Если параметры некорректны, возвращаем на главную
            return "/WEB-INF/home-page.jsp";
        }
        // Возвращаем представление квеста
        return getView();
    }
}

package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.StatisticService;
import jakarta.servlet.http.HttpServletRequest;

public class QuestDragon implements Command{
    private final StatisticService statisticService;

    public QuestDragon(StatisticService statisticService) {
        this.statisticService = statisticService;
    }
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
            session.setAttribute("questFinished", false);

            String username = (String) session.getAttribute("username");
            statisticService.registerAttempt(username);
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

            String username = (String) session.getAttribute("username");
            Boolean finished = (Boolean) session.getAttribute("questFinished");
            if (finished == null) {
                finished = false;
            }

            // Обновляем доверие в зависимости от выбора
            if(currentStage >= 2 && currentStage <= 9) {
                Integer trust = (Integer) session.getAttribute("trust");
                if(trust == null) trust = 50;

                int trustChange = Integer.parseInt(choice);
                trust += trustChange;
                // Ограничиваем доверие диапазоном 0-100
                trust = Math.max(0, Math.min(100, trust));

                session.setAttribute("trust", trust);

                // Проверяем условия поражения в зависимости от этапа
                boolean isLossCondition = false;
                if (currentStage >= 3 && currentStage <= 6 && trust < 50) { // Поражение если доверие < 50% на этапах 4-7
                    isLossCondition = true;
                } else if (currentStage >= 7 && currentStage <= 10 && trust < 70) { // Поражение если доверие < 70% на этапах 8-11
                    isLossCondition = true;
                }

                if (!finished && isLossCondition) {
                    statisticService.registerLoss(username);
                    session.setAttribute("questFinished", true);
                    session.setAttribute("stage", 11); // конец квеста
                    return getView();
                }
            }

            // Переходим к следующему этапу
            int nextStage = currentStage + 1;
            session.setAttribute("stage", nextStage);

            Integer trust = (Integer) session.getAttribute("trust");

            if (!finished && nextStage == 11) {
                session.setAttribute("questFinished", true);
                if (trust != null && trust >= 70) {
                    statisticService.registerWin(username);
                } else {
                    // Регистрируем поражение, если игрок дошел до 11-го этапа, но уровень доверия < 70
                    statisticService.registerLoss(username);
                }
            }

        } else {
            // Если параметры некорректны, возвращаем на главную
            return "/WEB-INF/home-page.jsp";
        }
        // Возвращаем представление квеста
        return getView();
    }
}
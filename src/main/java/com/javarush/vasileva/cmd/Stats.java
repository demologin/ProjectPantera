package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserStatsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class Stats implements Command {
    private final UserStatsService statsService;

    public Stats(UserStatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new AppException("Необходимо авторизоваться");
        }

        UserStats stats = statsService.getStatsByUser(user);
        req.setAttribute("stats", stats);
        return getView();
    }
}

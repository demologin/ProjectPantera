package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserStatsService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Value;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.javarush.vasileva.util.Value.AUTH_ERROR;

@SuppressWarnings("unused")
public class Stats implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Stats.class.getName());

    private final UserStatsService statsService;

    public Stats(UserStatsService statsService) {
        this.statsService = statsService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        LOGGER.info("Received GET request to view user statistics");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            LOGGER.error("User not authenticated. Throwing AppException: {}", AUTH_ERROR);
            throw new AppException(AUTH_ERROR);
        }

        LOGGER.debug("Authenticated user: id={}, login={}", user.getId(), user.getLogin());

        UserStats stats = statsService.getUserStats(user.getId()).orElseThrow(() -> new AppException(Value.STATS_NOT_FOUND));
        req.setAttribute(Key.STATS, stats);
        LOGGER.info("Statistics loaded successfully for user ID: {}", user.getId());

        return getView();
    }
}

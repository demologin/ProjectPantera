package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.service.UserStatsService;
import com.javarush.vasileva.util.Key;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Key.ERROR;
import static com.javarush.vasileva.util.Link.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class Login implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class.getName());

    private final UserService userService;
    private final UserStatsService userStatsService;

    public Login(UserService userService, UserStatsService userStatsService) {
        this.userService = userService;
        this.userStatsService = userStatsService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        LOGGER.info("Received POST request for user login");

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        LOGGER.debug("Login attempt with email: {}, password: {}", email, password);

        if (email.isEmpty() || password.isEmpty()) {
            LOGGER.warn("Empty email or password provided");
            request.getSession().setAttribute(Key.ERROR, EMPTY_DATA_ERROR);
            return getView();
        }

        Optional<User> optionalUser = userService.login(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LOGGER.info("User authenticated successfully: {}", user.getEmail());

            HttpSession session = request.getSession();
            UserStats stats = userStatsService.getUserStats(user.getId())
                    .orElse(userStatsService.createUserStats(user.getId()));

            session.setAttribute(USER, optionalUser.get());
            session.setAttribute(Key.STATS, stats);

            LOGGER.debug("User stats loaded/created and stored in session for user ID: {}", user.getId());
        } else {
            LOGGER.error(INVALID_DATA_ERROR);
            request.getSession().setAttribute(ERROR, INVALID_DATA_ERROR);
            return getView();
        }
        return HOME;
    }
}

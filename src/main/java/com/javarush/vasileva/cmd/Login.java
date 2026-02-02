package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.entity.UserStats;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.service.UserStatsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Link.HOME;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class Login implements Command {
    private final UserService userService;
    private final UserStatsService userStatsService;

    public Login(UserService userService, UserStatsService userStatsService) {
        this.userService = userService;
        this.userStatsService = userStatsService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        if (email.isEmpty() || password.isEmpty()) {
            request.getSession().setAttribute(ERROR, EMPTY_DATA_ERROR);
        }
        Optional<User> optionalUser = userService.login(email, password);
        if (optionalUser.isPresent()) {
            HttpSession session = request.getSession();
            UserStats stats = userStatsService.getUserStats(optionalUser.get().getId())
                    .orElse(userStatsService.createUserStats(optionalUser.get().getId()));
            session.setAttribute(USER, optionalUser.get());
            session.setAttribute("stats", stats);
        } else {
            request.getSession().setAttribute(ERROR, INVALID_DATA_ERROR);
            return getView();
        }
        return HOME;
    }
}

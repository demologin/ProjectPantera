package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class UserList implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserList.class.getName());

    private final UserService userService;

    public UserList(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        LOGGER.info("Received GET request to view user list");

        Helpers.checkAdminAuthorization(request, USER_LIST_AUTH_ERROR);
        LOGGER.debug("Admin authorization successful");

        Collection<User> users = userService.getAll();
        LOGGER.info("Retrieved {} users from database", users.size());

        request.setAttribute("users", users);
        request.getSession().setAttribute("ADMIN_ROLE", Role.ADMIN);
        LOGGER.debug("Users and admin role set in request attributes");

        return getView();
    }

    @Override
    public String doDelete(HttpServletRequest req) {
        LOGGER.info("Received DELETE request to remove user");

        String userIdStr = req.getParameter(USER_ID);
        LOGGER.debug("Attempting to delete user with ID: {}", userIdStr);

        User user = userService.getValidatedUser(userIdStr)
                .orElseThrow(() -> new AppException(USER_NOT_FOUND + userIdStr));
        LOGGER.info("User found for deletion: id={}, login={}", user.getId(), user.getLogin());

        req.setAttribute(USER, user);
        userService.delete(user);
        LOGGER.info("User with ID {} successfully deleted", user.getId());

        return getView();
    }
}
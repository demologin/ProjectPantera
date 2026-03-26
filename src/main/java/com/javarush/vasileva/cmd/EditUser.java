package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Link;
import com.javarush.vasileva.util.Helpers;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@SuppressWarnings("unused")
public class EditUser implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditUser.class.getName());

    private final UserService userService;

    public EditUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest req) {
        String stringId = req.getParameter("id");
        LOGGER.info("Received GET request to edit user with id: {}", stringId);

        Optional<User> optionalUser = userService.getValidatedUser(stringId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            req.setAttribute("user", user);
            LOGGER.debug("User found and set as request attribute: {}", user.getLogin());
        } else {
            LOGGER.warn("User with id {} not found", stringId);
        }

        return getView();
    }

    @Override
    public String doPost(HttpServletRequest req) {
        LOGGER.info("Received POST request to process user data");

        User user = User.builder()
                .login(req.getParameter(Key.LOGIN))
                .email(req.getParameter(Key.EMAIL))
                .password(req.getParameter(Key.PASSWORD))
                .role(Role.valueOf(req.getParameter(Key.ROLE)))
                .build();

        LOGGER.debug("Constructed user object: login={}, email={}, role={}",
                user.getLogin(), user.getEmail(), user.getRole());

        if (req.getParameter(Key.CREATE) != null) {
            LOGGER.info("Creating new user with login: {}", user.getLogin());
            userService.create(user);
        } else if (req.getParameter(Key.UPDATE) != null) {
            user.setId(Helpers.parseStringToLong(req.getParameter("id")));
            LOGGER.info("Updating user with id: {}, login: {}", user.getId(), user.getLogin());
            userService.update(user);
        }
        LOGGER.info("Redirecting to user list page");
        return Link.USER_LIST;
    }
}
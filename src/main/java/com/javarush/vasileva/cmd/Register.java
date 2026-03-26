package com.javarush.vasileva.cmd;

import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Link;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class Register implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Register.class.getName());

    private final UserService userService;

    public Register(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        LOGGER.info("Received POST request for user registration");

        String login = request.getParameter(Key.LOGIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        LOGGER.debug("Registration attempt with login: {}, email: {}", login, email);

        if (login == null || login.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            LOGGER.warn(EMPTY_DATA_ERROR + ". Missing: login={}, email={}, password={}",
                    login == null || login.isEmpty(), email == null || email.isEmpty(), password == null || password.isEmpty());
            throw new AppException(EMPTY_DATA_ERROR);
        }

        LOGGER.info("Registering new user with login: {} and email: {}", login, email);
        userService.register(login, email, password);

        LOGGER.info("User successfully registered. Redirecting to login page");
        return Link.LOGIN;
    }
}

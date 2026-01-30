package com.javarush.vasileva.cmd;

import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Key;
import com.javarush.vasileva.util.Link;
import jakarta.servlet.http.HttpServletRequest;

import static com.javarush.vasileva.util.Key.*;
import static com.javarush.vasileva.util.Value.*;

@SuppressWarnings("unused")
public class Register implements Command {
    private final UserService userService;

    public Register(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter(Key.LOGIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (login == null || login.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new AppException(EMPTY_DATA_ERROR);
        }

        userService.register(login, email, password);
        return Link.LOGIN;
    }
}

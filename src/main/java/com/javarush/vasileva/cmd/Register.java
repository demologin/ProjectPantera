package com.javarush.vasileva.cmd;

import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import com.javarush.vasileva.util.Link;
import jakarta.servlet.http.HttpServletRequest;

public class Register implements Command {
    private final UserService userService;

    public Register(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (login == null || login.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new AppException("Необходимо ввести логин, email и пароль.");
        }

        userService.register(login, email, password);
        return Link.LOGIN;
    }
}

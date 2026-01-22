package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.repository.FileUserRepository;
import com.javarush.matsarskaya.repository.UserRepository;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginPage implements Command {
    private final UserService userService;

    // Конструктор, принимающий UserService
    public LoginPage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doGet(HttpServletRequest request) {
        return getView();
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userService.loginUser(username, password).isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "/home-page";
        } else {
            request.setAttribute("error", "Неверное имя пользователя или пароль");
            return getView();
        }
    }

    @Override
    public String getView() {
        return "/WEB-INF/login-page.jsp";
    }
}

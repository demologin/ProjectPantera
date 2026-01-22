package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.entity.UserFileStorage;
import com.javarush.matsarskaya.repository.FileUserRepository;
import com.javarush.matsarskaya.repository.UserRepository;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RegisterPage implements Command{
    private final UserService userService;

    // Конструктор, принимающий UserService
    public RegisterPage(UserService userService) {
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

        if (userService.registerUser(username, password)) {
            // Автоматический вход после регистрации
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "/home-page";
        } else {
            request.setAttribute("error", "Пользователь уже существует");
            return getView();
        }
    }

    @Override
    public String getView() {
        return "/WEB-INF/register-page.jsp";
    }
}

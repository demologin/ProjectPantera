package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RegisterPage implements Command{
    private final UserService userService = new UserService();

    @Override
    public String doGet(HttpServletRequest request) {
        return "/WEB-INF/register-page.jsp";
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
            return "/WEB-INF/register-page.jsp";
        }
    }

    @Override
    public String getView() {
        return "/WEB-INF/register-page.jsp";
    }
}

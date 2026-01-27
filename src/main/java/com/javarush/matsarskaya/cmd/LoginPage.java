package com.javarush.matsarskaya.cmd;

import com.javarush.matsarskaya.exception.InvalidCredentialsException;
import com.javarush.matsarskaya.exception.UserNotFoundException;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginPage implements Command {
    private final UserService userService;

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

        try {
            userService.loginUser(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "/home-page";
        } catch (UserNotFoundException e) {
            request.setAttribute("error", "Пользователь не найден");
        } catch (InvalidCredentialsException e) {
            request.setAttribute("error", "Неверное имя пользователя или пароль");
        }

        return getView();
    }

    @Override
    public String getView() {
        return "/WEB-INF/login-page.jsp";
    }
}

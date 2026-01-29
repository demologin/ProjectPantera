package com.javarush.vasileva.cmd;

import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.javarush.vasileva.util.Link.HOME;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email.isEmpty() || password.isEmpty()) {
            throw new AppException("Необходимо ввести email и пароль");
        }

        User user = userService.login(email, password).orElseThrow(() -> new AppException("Неверный email или пароль"));
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return HOME;
    }
}

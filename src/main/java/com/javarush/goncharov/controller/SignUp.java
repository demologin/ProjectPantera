package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Role;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.repository.UserRepository;
import com.javarush.goncharov.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/signup")
public class SignUp extends HttpServlet {

    private final Storage userStorage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = User.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.USER)
                .email(req.getParameter("email"))
                .build();
        userService.post(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        req.setAttribute("user", user);
        resp.sendRedirect("/");
    }
}

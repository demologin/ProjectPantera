package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.UserRepository;
import com.javarush.goncharov.repository.UserStorage;
import com.javarush.goncharov.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;


@WebServlet("/signup")
public class SignUp extends HttpServlet {

    private final UserStorage userStorage = UserStorage.getInstance();
//    private final UserService userService = UserService.getInstance(UserRepository.getInstance(userStorage));
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = User.builder()
                .login(login)
                .password(password)
                .build();
        userService.post(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        resp.sendRedirect("/");
    }
}

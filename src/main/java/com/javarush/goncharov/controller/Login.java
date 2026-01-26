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
import java.util.Optional;

@WebServlet("/login")
public class Login extends HttpServlet {

    private final UserStorage userStorage = UserStorage.getInstance();
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> user = userService.find(login, password);
        if (user.isPresent()){
            System.out.println(user.get().toString());
            HttpSession session = req.getSession();
            session.setAttribute("user", user.get());
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login");
        }
    }
}

package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.repository.UserRepository;
import com.javarush.goncharov.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/list-users")
public class ListUsers extends HttpServlet {

    private final Storage userStorage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> users = userService.getAll().values();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/list-user.jsp").forward(req, resp);
    }
}

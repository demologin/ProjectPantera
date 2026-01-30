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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class Profile extends HttpServlet {

    private final Storage userStorage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (req.getParameter("action").equals("edit")) {
            resp.sendRedirect("/edit-user" + "?id=" + user.getId());
        } else if (req.getParameter("action").equals("delete")) {
            userService.delete(user);
            resp.sendRedirect("/logout");
        } else {
            resp.sendRedirect("/logout");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }
}

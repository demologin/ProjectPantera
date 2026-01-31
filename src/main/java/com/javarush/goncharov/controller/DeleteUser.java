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
import java.util.Optional;

@WebServlet("/delete-user")
public class DeleteUser extends HttpServlet {

    private final Storage userStorage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idUser = Long.parseLong(req.getParameter("id"));
        Optional<User> userFind = userService.get(idUser);
        if (req.getParameter("action").equals("delete")) {
            userFind.ifPresent(userService::delete);
        }
        resp.sendRedirect("/list-users");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idUser = Long.parseLong(req.getParameter("id"));
        userService.get(idUser).ifPresent(user -> req.setAttribute("user", user));
        req.getRequestDispatcher("/WEB-INF/delete-user.jsp").forward(req, resp);
    }
}

package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/create-quest")
public class CreateGame extends DefaultServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/create-quest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userSession = (User) session.getAttribute("user");
        Optional<User> userFind = userService.get(userSession.getId());
        String name = req.getParameter("name");
        String text = req.getParameter("text");
        questService.create(name, text, userFind.get().getId(), userFind.get().getLogin());
        resp.sendRedirect("/list-quests");
    }
}

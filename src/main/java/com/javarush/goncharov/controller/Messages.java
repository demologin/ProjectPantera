package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.model.Topic;
import com.javarush.goncharov.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@WebServlet("/messages")
public class Messages extends DefaultServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Message> messages = messageService.getAll().values();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        req.setAttribute("messages", messages);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/messages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("GoArchive")) {
            resp.sendRedirect("/completed-messages");
            return;
        }
        Long idMessage = Long.parseLong(req.getParameter("id"));
        Optional<Message> message = messageService.get(idMessage);
        String rememberMe = req.getParameter("rememberMe");
        if (rememberMe != null && (rememberMe.equals("on") || rememberMe.equals("off"))) {
            message.ifPresent(value -> value.setCompleted(true));
        }
        message.ifPresent(value -> value.setTopic(Topic.valueOf(req.getParameter("topic"))));
        messageService.update(message.get());
        resp.sendRedirect("/messages");
    }
}

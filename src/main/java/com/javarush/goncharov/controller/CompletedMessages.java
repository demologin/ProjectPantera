package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.MessageRepository;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.service.MessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@WebServlet("/completed-messages")
public class CompletedMessages extends HttpServlet {
    private final Storage messageStorage = Storage.getInstance();
    private final MessageService messageService = new MessageService(new MessageRepository(messageStorage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Message> messages = messageService.getAll().values();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");
        req.setAttribute("messages", messages);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/WEB-INF/completed-messages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idMessage = Long.parseLong(req.getParameter("id"));
        Optional<Message> message = messageService.get(idMessage);
        if (req.getParameter("action").equals("delete")) {
            messageService.delete(message.get());
        }
        resp.sendRedirect("/completed-messages");
    }
}

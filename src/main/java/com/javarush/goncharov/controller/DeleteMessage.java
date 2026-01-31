package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.repository.MessageRepository;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.service.MessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/delete-message")
public class DeleteMessage extends HttpServlet {
    private final Storage messageStorage = Storage.getInstance();
    private final MessageService messageService = new MessageService(new MessageRepository(messageStorage));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idMessage = Long.parseLong(req.getParameter("id"));
        Optional<Message> userFind = messageService.get(idMessage);
        if (req.getParameter("action").equals("delete")) {
            userFind.ifPresent(messageService::delete);
        }
        resp.sendRedirect("/messages");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idMessage = Long.parseLong(req.getParameter("id"));
        messageService.get(idMessage).ifPresent(message -> req.setAttribute("message", message));
        req.getRequestDispatcher("/WEB-INF/delete-message.jsp").forward(req, resp);
    }
}

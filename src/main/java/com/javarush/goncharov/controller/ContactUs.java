package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.repository.MessageRepository;
import com.javarush.goncharov.repository.MessageStorage;
import com.javarush.goncharov.service.MessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/contact")
public class ContactUs extends HttpServlet {

    private final MessageStorage messageStorage = MessageStorage.getInstance();
    private final MessageService messageService = new MessageService(new MessageRepository(messageStorage));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String text = req.getParameter("message");
        Message message = Message.builder()
                .name(name)
                .email(email)
                .message(text)
                .build();
        messageService.post(message);
        resp.sendRedirect("/contact");
    }
}

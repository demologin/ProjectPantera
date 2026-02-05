package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Message;
import com.javarush.goncharov.model.Topic;
import com.javarush.goncharov.repository.MessageRepository;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.service.MessageService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/contact")
public class ContactUs extends DefaultServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Тут я думаю слишком поздно делаю проверку на пустые значения в заполняемых полях
        //Валидация формы должна производиться на фронте, на заполненность полей и запросы с пустыми
        //Полями сюда долетать не должны, но пока так
        //Не разбирался пока как сделать проверки на стороне формы
        if (req.getParameter("name").isEmpty() ||
                req.getParameter("email").isEmpty() ||
                req.getParameter("message").isEmpty()){
            session.setAttribute("alertType", "danger");
            session.setAttribute("alertMessage",
                    "Не заполнены все обязательные поля, попробуйте снова!");
            resp.sendRedirect("/contact");
            return;
        }
        String name = req.getParameter("name");
        Message message = Message.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .message(req.getParameter("message"))
                .topic(Topic.valueOf(req.getParameter("topic")))
                .Completed(false)
                .build();
        messageService.post(message);
        session.setAttribute("alertType", "success");
        session.setAttribute("alertMessage",
                "Спасибо, " + name + "! Ваше сообщение успешно отправлено.");
        resp.sendRedirect("/contact");
    }
}

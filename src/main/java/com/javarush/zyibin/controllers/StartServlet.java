package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.Question;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/start")
public class StartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/start.jsp");
        dispatcher.forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        List<Question> questions = createQuestions();

        session.setAttribute("questions",questions);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("score", 0);

        resp.sendRedirect(req.getContextPath() + "/question");
    }

    private List<Question> createQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Что такое JVM?",
                List.of("Среда выполнения Java-приложений",
                        "Компилятор Java",
                        "Фреймворк для веб-приложений"
                ),
                0)
        );

        questions.add(new Question("Какой метод вызывается при старте сервлета?",
                List.of("doGet()",
                        "init()",
                        "service()"),

                1)
                );
        return questions;
    }
}

package com.javarush.khmelov.something;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/questRepository")
public class AllQuests extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("questRepository", Create.questRepository);
        req.getRequestDispatcher("/WEB-INF/allQuests.jsp").forward(req, resp);

    }
}

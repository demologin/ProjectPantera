package com.javarush.khmelov.something;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/quest")
public class NewQuest extends HttpServlet {
    static int num;
    static String name;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
name = req.getParameter("name");
num =Integer.parseInt( req.getParameter("num"));
req.setAttribute("num",num);

        req.getRequestDispatcher("/WEB-INF/create.jsp").forward(req,resp);


    }
}

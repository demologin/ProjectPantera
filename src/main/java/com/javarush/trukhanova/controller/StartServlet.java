package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("playerName");

        Player player = new Player(name, 0);

        HttpSession session = request.getSession();
        session.setAttribute("player", player);

        response.sendRedirect("logic?id=1");
    }
}
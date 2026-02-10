package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "InitServlet", value = "/init")
public class InitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String playerName = request.getParameter("playerName");

        HttpSession session = request.getSession();

        Player player = new Player(playerName);
        session.setAttribute("player", player);

        response.sendRedirect("logic?id=1");
    }
}
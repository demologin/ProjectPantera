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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException { // Убрали ServletException, так как он тут не нужен

        String playerName = request.getParameter("playerName");
        String selectedAvatar = request.getParameter("avatar");

        if (playerName == null || playerName.isBlank()) {
            playerName = "Неизвестный герой";
        }

        if (selectedAvatar == null || selectedAvatar.isEmpty()) {
            selectedAvatar = "static/images/avatars/1.png";
        }

        Player player = new Player(playerName, selectedAvatar);

        HttpSession session = request.getSession();
        session.setAttribute("player", player);

        response.sendRedirect("logic?id=1");
    }
}
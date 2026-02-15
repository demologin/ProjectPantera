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
            throws IOException {

        String playerName = request.getParameter("playerName");
        String selectedAvatar = request.getParameter("avatar");

        if (playerName == null || playerName.isBlank()) {
            playerName = "Неизвестный герой";
        }
        if (selectedAvatar == null || selectedAvatar.isEmpty()) {
            selectedAvatar = "static/images/avatars/1.png";
        }

        HttpSession session = request.getSession();

        Player player = (Player) session.getAttribute("player");

        if (player == null) {
            player = new Player(playerName, selectedAvatar);
            session.setAttribute("player", player);
        } else {
            player.setName(playerName);
            player.setAvatarPath(selectedAvatar);
        }

        response.sendRedirect(request.getContextPath() + "/logic?id=1");
    }
}
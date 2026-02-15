package com.javarush.trukhanova.controller;

import com.javarush.trukhanova.entity.Player;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StartServlet", value = "/start")
public class StartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {

        String name = request.getParameter("playerName");
        String avatar = request.getParameter("avatar");

        if (name == null || name.isBlank()) name = "Исследователь";
        if (avatar == null) avatar = "static/images/avatars/1.png";

        Player player = new Player(name, avatar);

        HttpSession session = request.getSession();
        session.setAttribute("player", player);

        response.sendRedirect("logic?id=1");
    }
}
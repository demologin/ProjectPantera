package com.javarush.goncharov.controller;


import com.javarush.goncharov.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class Profile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") == null) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/logout");
        }
    }
}

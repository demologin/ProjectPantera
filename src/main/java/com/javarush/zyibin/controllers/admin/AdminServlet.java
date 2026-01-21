package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");

        req.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(req, resp);
    }
}

package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.web.admin.AbstractAdminController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin-users")
public class AdminUsersController extends AbstractAdminController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        if (checkAdmin(req, resp)) {
            return;
        }

        req.setAttribute("users", adminService.getAllUsers());
        req.setAttribute("view", "/WEB-INF/jsp/admin-users.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}


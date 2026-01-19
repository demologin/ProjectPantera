package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.web.admin.AbstractAdminController;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

@WebServlet("/admin")
public class AdminController extends AbstractAdminController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        if (checkAdmin(req, resp)) {
            return;
        }

        req.setAttribute("view", "/WEB-INF/jsp/admin.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}


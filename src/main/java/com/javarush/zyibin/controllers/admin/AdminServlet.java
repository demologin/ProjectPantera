package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.controllers.BaseServlet;
import com.javarush.zyibin.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends BaseServlet {

    @Override
    protected void initializeSpecificServices() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("GET /admin");

        User user = getCurrentUser(req);
        log.debug("Admin panel accessed by user {}", user.getUsername());

        req.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(req, resp);
    }
}

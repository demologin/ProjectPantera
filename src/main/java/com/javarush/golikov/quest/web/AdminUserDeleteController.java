package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.web.admin.AbstractAdminController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin-user-delete")
public class AdminUserDeleteController extends AbstractAdminController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (checkAdmin(req, resp)) {
            return;
        }

        adminService.deleteUser(req.getParameter("login"));
        resp.sendRedirect(req.getContextPath() + "/admin-users");
    }
}

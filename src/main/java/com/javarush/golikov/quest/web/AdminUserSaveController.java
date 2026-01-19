package com.javarush.golikov.quest.web;

import com.javarush.golikov.quest.auth.Role;
import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.web.admin.AbstractAdminController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin-user-save")
public class AdminUserSaveController extends AbstractAdminController {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        if (checkAdmin(req, resp)) {
            return;
        }

        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        Role role = Role.valueOf(req.getParameter("role"));

        adminService.saveUser(new User(login, pass, role));
        resp.sendRedirect(req.getContextPath() + "/admin-users");
    }
}


package com.javarush.golikov.quest.web.admin;

import com.javarush.golikov.quest.auth.Role;
import com.javarush.golikov.quest.model.User;
import com.javarush.golikov.quest.service.AdminService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class AbstractAdminController extends HttpServlet {

    protected AdminService adminService;

    @Override
    public void init() {
        adminService = (AdminService) getServletContext().getAttribute("adminService");
    }

    protected boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = (User) req.getSession().getAttribute("user");

        if (user == null || user.role() != Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/");
            return true;
        }
        return false;
    }
}
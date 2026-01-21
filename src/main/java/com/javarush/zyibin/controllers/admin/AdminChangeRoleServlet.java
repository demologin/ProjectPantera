package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.service.AdminUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/users/role")
public class AdminChangeRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User admin = (User) session.getAttribute("currentUser");

        long userId = Long.parseLong(req.getParameter("userId"));
        Role newRole = Role.valueOf(req.getParameter("role"));

        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        AdminUserService adminService = new AdminUserService(userRepository);
        adminService.changeUserRole(admin.getId(), userId, newRole);
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}

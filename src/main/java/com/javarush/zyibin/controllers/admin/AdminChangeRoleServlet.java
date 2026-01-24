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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/admin/users/role")
public class AdminChangeRoleServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AdminChangeRoleServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("POST /admin/users/role");

        HttpSession session = req.getSession(false);
        User admin = (User) session.getAttribute("currentUser");

        long userId = Long.parseLong(req.getParameter("userId"));
        Role newRole = Role.valueOf(req.getParameter("role"));
        log.info("Admin {} requested role change for userId={} to role={}",
                admin.getUsername(),
                userId,
                newRole);

        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        AdminUserService adminService = new AdminUserService(userRepository);
        adminService.changeUserRole(admin.getId(), userId, newRole);
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}

package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin/users/block")
public class AdminBlockUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User admin = (User) session.getAttribute("currentUser");

        if (admin == null || admin.getRole() != Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        long userId = Long.parseLong(req.getParameter("userId"));
        UserRepository repo = (UserRepository)  getServletContext().getAttribute("userRepository");
        repo.findById(userId).ifPresent(user -> {
            if (user.getId() != admin.getId()) {
                user.setBlocked(!user.isBlocked());
            }
        });
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}

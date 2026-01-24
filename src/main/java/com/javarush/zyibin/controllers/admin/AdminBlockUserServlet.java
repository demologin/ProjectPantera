package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/admin/users/block")
public class AdminBlockUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AdminBlockUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("POST /admin/users/block");

        HttpSession session = req.getSession(false);
        User admin = (User) session.getAttribute("currentUser");

        long userId = Long.parseLong(req.getParameter("userId"));
        UserRepository repo = (UserRepository) getServletContext().getAttribute("userRepository");
        repo.findById(userId).ifPresent(user -> {
            if (user.getId() != admin.getId()) {
                user.setBlocked(!user.isBlocked());
                log.info("Admin {} changed block status for user {} to {}",
                        admin.getUsername(),
                        user.getUsername(),
                        user.isBlocked() ? "blocked" : "unblocked");
            }
        });
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}

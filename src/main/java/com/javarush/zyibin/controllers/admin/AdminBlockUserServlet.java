package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.controllers.BaseServlet;
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
public class AdminBlockUserServlet extends BaseServlet {

    @Override
    protected void initializeSpecificServices() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("POST /admin/users/block");

        User admin = getCurrentUser(req);
        long userId = Long.parseLong(req.getParameter("userId"));

        userRepository.findById(userId).ifPresent(user -> {
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

package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET /login");
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        log.debug("Login attempt for username={}", username);
        String password = req.getParameter("password");

        UserRepository userRepository = (UserRepository) req.getServletContext().getAttribute("userRepository");
        Optional<User> userOptional = userRepository.findByUserName(username);

        if (userOptional.isEmpty()) {
            log.warn("Login failed: user {} not found", username);
            req.setAttribute("error", "Invalid login or password");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }
        User user = userOptional.get();
        String passwordHash = PasswordUtil.hashPassword(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            log.warn("Login failed: invalid password for user {}", username);
            req.setAttribute("error", "Invalid login or password");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        if (user.isBlocked()) {
            log.warn("Login attempt for blocked user {}", username);
            req.setAttribute("error", "Пользователь заблокирован администратором");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }


        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);
        log.info("User {} successfully logged in", username);
        resp.sendRedirect(req.getContextPath() + "/home");
    }

}

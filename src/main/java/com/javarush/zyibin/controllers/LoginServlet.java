package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserRepository userRepository = (UserRepository)  req.getServletContext().getAttribute("userRepository");
        Optional<User> userOptional = userRepository.findByUserName(username);

        if(userOptional.isEmpty()) {
            req.setAttribute("error", "Invalid login or password");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }
        User user = userOptional.get();
        String passwordHash = hashPassword(password);
        if (!user.getPasswordHash().equals(passwordHash)) {
            req.setAttribute("error", "Invalid login or password");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
    private String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Password hashing error", e);
        }
    }
}

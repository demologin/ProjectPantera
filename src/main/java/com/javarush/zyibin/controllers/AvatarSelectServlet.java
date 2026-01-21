package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.service.AvatarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile/avatar")
public class AvatarSelectServlet extends HttpServlet {

    private final AvatarService avatarService = new AvatarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        List<String> avatars = avatarService.getAvailableAvatars();

        req.setAttribute("avatars", avatars);
        req.getRequestDispatcher("/WEB-INF/jsp/avatar-select.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");

        String selectedAvatar = req.getParameter("avatarPath");

        List<String> availableAvatars = avatarService.getAvailableAvatars();
        if (availableAvatars.contains(selectedAvatar)) {
            user.setAvatarPath(selectedAvatar);
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}

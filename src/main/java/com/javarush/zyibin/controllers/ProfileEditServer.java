package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/profile/edit")
public class ProfileEditServer extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ProfileEditServer.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET /profile/edit");
        HttpSession session = req.getSession(false);

        req.getRequestDispatcher("/WEB-INF/jsp/profile-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("POST /profile/edit");

        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");
        String nickname = req.getParameter("nickname");
        String about = req.getParameter("about");

        user.setNickname(nickname);
        user.setAbout(about);
        log.info("User {} updated profile data", user.getUsername());

        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}

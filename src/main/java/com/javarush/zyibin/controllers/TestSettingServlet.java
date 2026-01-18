package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.Topic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test/settings")
public class TestSettingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("topics", Topic.values());
        req.getRequestDispatcher("/WEB-INF/jsp/test-settings.jsp").forward(req,resp);
    }
}

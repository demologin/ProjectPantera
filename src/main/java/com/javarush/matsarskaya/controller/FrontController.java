package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/home-page", "/quest-dragon", "/login-page", "/register-page", "/logout"})
public class FrontController extends HttpServlet {
    private final HttpResolver httpResolver = new HttpResolver();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получение пути запроса (например, "/home-page" или "/quest-dragon")
        String pathInfo = req.getServletPath();

        // Проверяем, нужно ли проверять авторизацию
        if (pathInfo.equals("/quest-dragon")) {
            // Проверяем авторизацию для квеста
            if (!UserService.isAuthenticated(req)) {
                resp.sendRedirect(req.getContextPath() + "/home-page");
                return;
            }
        }
        // Использование HttpResolver для получения соответствующей команды
        Command command = httpResolver.resolve(pathInfo);
        // Вызов метода doGet команды, который возвращает путь к JSP-странице
        String viewPath = command.doGet(req);
        // Перенаправление запроса на соответствующую JSP-страницу
        req.getRequestDispatcher(viewPath).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getServletPath();

        // Проверяем, нужно ли проверять авторизацию
        if (pathInfo.equals("/quest-dragon")) {
            if (!UserService.isAuthenticated(req)) {
                resp.sendRedirect(req.getContextPath() + "/home-page");
                return;
            }
        }

        Command command = httpResolver.resolve(pathInfo);
        String viewPath = command.doPost(req);
        req.getRequestDispatcher(viewPath).forward(req, resp);
    }
}



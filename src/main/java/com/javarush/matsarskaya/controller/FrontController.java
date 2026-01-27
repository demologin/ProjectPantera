package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"/home-page", "/quest-dragon", "/login-page", "/register-page", "/logout", "/statistic-page"})
public class FrontController extends HttpServlet {
    private final HttpResolver httpResolver = new HttpResolver();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Обрабатывает HTTP-запрос.
     * @param req HTTP запрос
     * @param resp HTTP ответ
     * @throws ServletException ошибка сервлета
     * @throws IOException ошибка ввода-вывода
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getServletPath();

        // Проверяем авторизацию для защищённых страниц
        if (isProtectedPath(pathInfo) && !UserService.isAuthenticated(req)) {
            resp.sendRedirect(req.getContextPath() + "/home-page");
            return;
        }

        // Получаем команду и выполняем её
        Command command = httpResolver.resolve(pathInfo);
        String viewPath = "GET".equals(req.getMethod()) ? command.doGet(req) : command.doPost(req);

        // Перенаправляем на JSP-страницу
        req.getRequestDispatcher(viewPath).forward(req, resp);
    }

    /**
     * Проверяет, является ли путь защищённым (требует авторизации).
     * @param pathInfo путь запроса
     * @return true если путь защищённый
     */
    private boolean isProtectedPath(String pathInfo) {
        return "/quest-dragon".equals(pathInfo) || "/statistic-page".equals(pathInfo);
    }
}



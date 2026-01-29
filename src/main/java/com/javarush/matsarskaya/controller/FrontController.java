package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import com.javarush.matsarskaya.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


@WebServlet({"/home-page", "/quest-dragon", "/login-page", "/register-page", "/logout", "/statistic-page"})
public class FrontController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
    private final HttpResolver httpResolver;

    public FrontController() {
        this(new HttpResolver());
    }

    public FrontController(HttpResolver httpResolver) {
        this.httpResolver = httpResolver;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getServletPath();
        String method = req.getMethod();

        logger.info("Получен {} запрос на путь: {}", method, pathInfo);

        // Проверяем авторизацию для защищённых страниц
        if (isProtectedPath(pathInfo) && !UserService.isAuthenticated(req)) {
            logger.warn("Попытка доступа к защищённой странице без авторизации: {}", pathInfo);
            resp.sendRedirect(req.getContextPath() + "/home-page");
            return;
        }

        try {
            Command command = httpResolver.resolve(pathInfo);
            String viewPath = "GET".equals(req.getMethod()) ? command.doGet(req) : command.doPost(req);
            logger.debug("Запрос обработан успешно, перенаправление на: {}", viewPath);
            req.getRequestDispatcher(viewPath).forward(req, resp);
        } catch (Exception e) {
            logger.error("Ошибка при обработке запроса {}: {}", pathInfo, e.getMessage(), e);
            throw e;
        }
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



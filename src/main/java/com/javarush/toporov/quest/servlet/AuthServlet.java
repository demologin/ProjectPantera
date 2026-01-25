package com.javarush.toporov.quest.servlet;

import com.javarush.toporov.quest.util.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            if (UserRepository.register(login, pass)) {
                request.getSession().setAttribute("user", login);
                response.sendRedirect("index.jsp");
            } else {
                sendError(request, response, "Пользователь уже существует!");
            }
        } else {
            if (UserRepository.check(login, pass)) {
                request.getSession().setAttribute("user", login);
                // Установим начальное кол-во игр, если новый сеанс
                if (request.getSession().getAttribute("gamesCount") == null) {
                    request.getSession().setAttribute("gamesCount", 0);
                }
                response.sendRedirect("index.jsp");
            } else {
                sendError(request, response, "Неверный логин или пароль!");
            }
        }
    }

    private void sendError(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        req.setAttribute("error", msg);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}

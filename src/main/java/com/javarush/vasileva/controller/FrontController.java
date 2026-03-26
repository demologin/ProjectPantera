package com.javarush.vasileva.controller;

import com.javarush.vasileva.cmd.Command;
import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Role;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.javarush.vasileva.util.Link.*;

@WebServlet({INDEX, HOME, LOGIN, REGISTER, LOGOUT, PROFILE, USER_LIST, EDIT_USER, PLAY_GAME, EDIT_QUEST, STATS, ERROR})
public class FrontController extends HttpServlet {

    private final HttpResolver httpResolver = Winter.find(HttpResolver.class);

    @Override
    public void init(ServletConfig config) {
        config.getServletContext().setAttribute("roles", Role.values());

        Config gameConfig = Winter.find(Config.class);
        gameConfig.fillRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String view = command.doGet(req);
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    private static String getJsp(String view) {
        return "WEB-INF/" + view + ".jsp";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String methodOverride = req.getParameter("_method");
        if ("DELETE".equalsIgnoreCase(methodOverride)) {
            doDelete(req, resp);
            return;
        }
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doDelete(req);
        resp.sendRedirect(redirect);
    }
}

package com.javarush.bekk.controller;

import com.javarush.bekk.cmd.Command;
import com.javarush.bekk.config.Config;
import com.javarush.bekk.config.Winter;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({"", "/test-page"})
public class FrontController extends HttpServlet {

    private final HttpResolver httpResolver = Winter.find(HttpResolver.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String view = command.doGet(req);
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp).forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) {
        Config config1 = Winter.find(Config.class);
        config1.fillRepository();

        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute(Config.class.getName(), config1);
    }

    private static String getJsp(String view) {
        return "/WEB-INF/" + view + ".jsp";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }

    /*@Override
    public void init(ServletConfig config) {
        config.getServletContext().setAttribute("roles", Role.values());
    }*/
}

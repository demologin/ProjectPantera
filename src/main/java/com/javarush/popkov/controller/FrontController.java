package com.javarush.popkov.controller;

import com.javarush.popkov.cmd.Command;
import com.javarush.popkov.config.Config;
import com.javarush.popkov.config.Winter;
import com.javarush.popkov.entity.Gender;
import com.javarush.popkov.entity.Role;
import com.javarush.popkov.util.Go;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1 << 20)
@WebServlet({
        Go.INDEX, Go.HOME,
        Go.SIGNUP, Go.LOGIN, Go.LOGOUT,
        Go.LIST_USER, Go.PROFILE, Go.EDIT_USER,
        Go.STATISTICS,
        Go.CREATE, Go.QUEST, Go.PLAY_GAME
})
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
    public void init(ServletConfig servletConfig) {
        Config config = Winter.find(Config.class);
        config.fillEmptyRepository();

        ServletContext servletContext = servletConfig.getServletContext();
        servletContext.setAttribute("roles", Role.values());
        servletContext.setAttribute("genders", Gender.values());
    }

    private static String getJsp(String view) {
        return "/WEB-INF/" + view + ".jsp";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}

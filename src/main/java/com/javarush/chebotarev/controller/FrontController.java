package com.javarush.chebotarev.controller;

import com.javarush.chebotarev.cmd.Command;
import com.javarush.chebotarev.component.Go;
import com.javarush.chebotarev.component.ObjectRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet({
        Go.INDEX,
        Go.MAIN_MENU,
        Go.NEW_QUEST,
        Go.START_QUEST,
        Go.CONTINUE_QUEST,
        Go.NEXT_STAGE,
        Go.PREVIOUS_STAGE,
        Go.EDITOR
})
public class FrontController extends HttpServlet {

    private final HttpResolver httpResolver = ObjectRepository.find(HttpResolver.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = httpResolver.resolve(req);
        String view = command.doGet(req, this);
        String jsp = getJsp(view);
        req.getRequestDispatcher(jsp)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Command command = httpResolver.resolve(req);
        String redirect = command.doPost(req);
        resp.sendRedirect(redirect);
    }

    private static String getJsp(String view) {
        return "/WEB-INF" + view + ".jsp";
    }
}

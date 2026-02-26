package com.javarush.ushanov.controller;

import com.javarush.ushanov.cmd.*;
import com.javarush.ushanov.repository.QuestRepository;
import com.javarush.ushanov.service.QuestService;
import com.javarush.ushanov.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * FrontController — единственный сервлет в приложении.
 *
 * Паттерн Front Controller: все HTTP-запросы приходят сюда,
 * а этот класс решает, какую команду (Command) вызвать.
 *
 * Важно: мы используем urlPatterns с конкретными путями вместо "/*",
 * чтобы Tomcat сам обслуживал статику (CSS, картинки) напрямую,
 * не пропуская их через наш сервлет.
 */
@WebServlet(urlPatterns = {"/", "/quest", "/new-game", "/choice"})
public class FrontController extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(FrontController.class);

    private final Map<String, Command> getCommands = new HashMap<>();
    private final Map<String, Command> postCommands = new HashMap<>();

    @Override
    public void init() {
        log.info("Initializing FrontController - creating dependencies and registering commands");

        QuestRepository questRepository = new QuestRepository();
        QuestService questService = new QuestService(questRepository);
        SessionService sessionService = new SessionService(questService);

        // GET-команды: показывают страницы
        getCommands.put("/", new StartPageCommand());
        getCommands.put("/quest", new QuestCommand(questService, sessionService));

        // POST-команды: обрабатывают действия пользователя
        postCommands.put("/new-game", new NewGameCommand(sessionService));
        postCommands.put("/choice", new ChoiceCommand(sessionService));

        log.info("FrontController ready. GET commands: {}, POST commands: {}",
                getCommands.size(), postCommands.size());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, getCommands, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, postCommands, "POST");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Command> commands, String method)
            throws ServletException, IOException {

        String uri = request.getRequestURI().substring(request.getContextPath().length());
        log.debug("{} {}", method, uri);

        Command command = commands.get(uri);

        if (command == null) {
            log.warn("Command not found for {} {}, returning 404", method, uri);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String view = command.execute(request, response);
        log.debug("Command {} returned view: {}", command.getClass().getSimpleName(), view);

        if (view.startsWith("redirect:")) {
            String redirectPath = request.getContextPath() + view.substring("redirect:".length());
            log.debug("Redirect -> {}", redirectPath);
            response.sendRedirect(redirectPath);
        } else {
            log.debug("Forward -> {}", view);
            request.getRequestDispatcher(view).forward(request, response);
        }
    }
}

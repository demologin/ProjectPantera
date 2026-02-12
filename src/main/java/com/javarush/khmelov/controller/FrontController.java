package com.javarush.khmelov.controller;

import com.javarush.khmelov.config.StoryFiles;
import com.javarush.khmelov.config.StoryLoader;
import com.javarush.khmelov.entity.Story;
import com.javarush.khmelov.repository.InMemoryStoryRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

@WebServlet({"", "/home"})
public class FrontController extends HttpServlet {

    private final Map<String, Controller> routes = new HashMap<>();

    @Override
    public void init() throws ServletException {
        InMemoryStoryRepository repo = new InMemoryStoryRepository();
        StoryLoader loader = new StoryLoader();

        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(StoryFiles.ALIEN_CHALLENGE_RESOURCE)) {

            if (in == null) {
                throw new ServletException("Story resource not found on classpath: "
                        + StoryFiles.ALIEN_CHALLENGE_RESOURCE
                        + " (expected: src/main/resources/" + StoryFiles.ALIEN_CHALLENGE_RESOURCE + ")");
            }

            Story story = loader.load(StoryFiles.ALIEN_CHALLENGE_CODE, in);
            repo.put(story);

        } catch (Exception e) {
            throw new ServletException("Failed to bootstrap stories", e);
        }

        StoryController storyController = new StoryController(repo);
        routes.put("/", storyController);
        routes.put("/home", storyController);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        dispatch(req, resp);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String path = req.getServletPath();
            Controller controller = routes.getOrDefault(path, routes.get("/"));
            String view = controller.handle(req, resp);
            req.getRequestDispatcher(view).forward(req, resp);
        } catch (Exception ex) {
            req.setAttribute("errorMessage", ex.getMessage());
            try {
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
            } catch (Exception ignored) {}
        }
    }
}

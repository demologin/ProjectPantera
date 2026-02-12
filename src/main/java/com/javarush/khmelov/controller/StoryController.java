package com.javarush.khmelov.controller;

import com.javarush.khmelov.entity.EndingType;
import com.javarush.khmelov.entity.GameSession;
import com.javarush.khmelov.entity.Story;
import com.javarush.khmelov.entity.StoryNode;
import com.javarush.khmelov.repository.StoryRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class StoryController implements Controller {

    private final StoryRepository storyRepository;
    private static final String SESSION_STATS_KEY = "GAME_STATS";
    private static final String SESSION_LAST_ENDED_NODE_KEY = "LAST_ENDED_NODE";

    public StoryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    public String handle(HttpServletRequest req, HttpServletResponse resp) {

        final String storyCode;
        String paramStory = req.getParameter("story");
        storyCode = paramStory == null || paramStory.isBlank() ? "alien_challenge" : paramStory;

        final String nodeKey;
        String paramNode = req.getParameter("node");
        nodeKey = paramNode == null || paramNode.isBlank() ? "START" : paramNode;

        // Session Stats
        HttpSession session = req.getSession(true);
        GameSession stats = (GameSession) session.getAttribute(SESSION_STATS_KEY);
        if (stats == null) {
            stats = new GameSession();
            session.setAttribute(SESSION_STATS_KEY, stats);
        }
        if ("START".equalsIgnoreCase(nodeKey)) {
            session.removeAttribute(SESSION_LAST_ENDED_NODE_KEY);
        }

        Story story = storyRepository.findByCode(storyCode)
            .orElseThrow(() -> new IllegalArgumentException("Story not found: " + storyCode));

        StoryNode node = story.getNode(nodeKey);

        // When final end - count
        if (node.isEnding()) {
            String lastEndedNode = (String) session.getAttribute(SESSION_LAST_ENDED_NODE_KEY);

            if (lastEndedNode == null || !lastEndedNode.equals(node.getKey())) {
                if (node.getEndingType() == EndingType.WIN) {
                    stats.recordWin();
                } else if (node.getEndingType() == EndingType.LOSE) {
                    stats.recordLoss();
                }
                session.setAttribute(SESSION_LAST_ENDED_NODE_KEY, node.getKey());
            }
        }

        req.setAttribute("story", story);
        req.setAttribute("node", node);

        req.setAttribute("stats", stats);

        return "/WEB-INF/story.jsp";
    }
}

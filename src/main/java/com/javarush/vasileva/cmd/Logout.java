package com.javarush.vasileva.cmd;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.javarush.vasileva.util.Link.LOGIN;

@SuppressWarnings("unused")
public class Logout implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logout.class.getName());

    @Override
    public String doGet(HttpServletRequest request) {
        LOGGER.info("Received GET request for user logout");

        HttpSession session = request.getSession(false);
        if (session != null) {
            LOGGER.debug("User session found. Session ID: {}", session.getId());
            session.invalidate();
            LOGGER.info("User session invalidated successfully");
        }

        LOGGER.info("Redirecting user to login page");
        return LOGIN;
    }
}

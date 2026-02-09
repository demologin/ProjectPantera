package com.javarush.popkov.filter;

import com.javarush.popkov.entity.Role;
import com.javarush.popkov.entity.User;
import com.javarush.popkov.util.Go;
import com.javarush.popkov.util.RequestHelpers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@WebFilter({
        Go.INDEX, Go.HOME, Go.SIGNUP, Go.LOGIN,
        Go.LOGOUT, Go.LIST_USER, Go.PROFILE, Go.EDIT_USER, Go.PLAY_GAME,
        Go.CREATE, Go.QUEST})
public class AuthorizationFilter extends HttpFilter {

    private final Map<Role, List<String>> permissions = Map.of(
            Role.GUEST,
            List.of(Go.HOME, Go.SIGNUP, Go.LOGIN),

            Role.USER,
            List.of(Go.HOME, Go.SIGNUP, Go.LOGIN,
                    Go.LOGOUT, Go.LIST_USER, Go.PROFILE, Go.EDIT_USER, Go.PLAY_GAME),

            Role.ADMIN,
            List.of(Go.HOME, Go.SIGNUP, Go.LOGIN,
                    Go.LOGOUT, Go.LIST_USER, Go.PROFILE, Go.EDIT_USER, Go.PLAY_GAME,
                    Go.CREATE, Go.QUEST)
    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = requestURI.startsWith(contextPath)
                ? requestURI.substring(contextPath.length())
                : requestURI;
        path = path.split("[?#]")[0];
        if (isStaticResource(path)) {
            chain.doFilter(req, res);
            return;
        }
        String cmdUri = path.isBlank() || "/".equals(path) ? "/home" : path;
        HttpSession session = req.getSession();
        Role role = RequestHelpers.getUser(session)
                .map(User::getRole)
                .orElse(Role.GUEST);
        if (permissions.get(role).contains(cmdUri)) {
            chain.doFilter(req, res);
        } else {
            String message = "Access denied";
            log.warn(message);
            RequestHelpers.createError(req, message);
            res.sendRedirect(req.getContextPath() + Go.LOGIN);
        }
    }

    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/")
                || path.startsWith("/images/")
                || path.startsWith("/user-images/")
                || path.startsWith("/quest-images/")
                || path.equals("/favicon.ico");
    }
}

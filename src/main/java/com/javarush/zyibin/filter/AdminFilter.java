package com.javarush.zyibin.filter;

import com.javarush.zyibin.model.Role;
import com.javarush.zyibin.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        User user = (User) session.getAttribute("currentUser");

        if (user == null || user.getRole() != Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

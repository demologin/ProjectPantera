package com.javarush.vasileva.filter;

import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.exception.ExceptionHelper;
import com.javarush.vasileva.util.Link;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (AppException e) {
            handleAppException(req, res, e);
        }
    }

    private void handleAppException(HttpServletRequest req, HttpServletResponse res, AppException appException)
            throws ServletException, IOException {
        ExceptionHelper.createError(req, appException.getMessage());
        req.getRequestDispatcher(Link.ERROR).forward(req, res);
    }
}

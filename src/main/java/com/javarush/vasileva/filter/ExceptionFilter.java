package com.javarush.vasileva.filter;

import com.javarush.vasileva.exception.AppException;
import com.javarush.vasileva.exception.ExceptionHelper;
import com.javarush.vasileva.util.Link;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } catch (AppException e) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            ExceptionHelper.createError(request, e.getMessage());
            request.getRequestDispatcher(Link.ERROR).forward(request, response);
        }
    }
}

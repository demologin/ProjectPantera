package com.javarush.buslovskii.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class StaticResourceFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/css/") ||
            path.startsWith("/js/") ||
            path.startsWith("/images/") ||
            path.startsWith("/fonts/") ||
            path.endsWith(".css") ||
            path.endsWith(".js") ||
            path.endsWith(".jpg") ||
            path.endsWith(".png") ||
            path.endsWith(".gif") ||
            path.endsWith(".ico")) {
            chain.doFilter(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
}
package com.javarush.buslovskii.controller;

import com.javarush.buslovskii.command.Command;
import com.javarush.buslovskii.command.CommandFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class FrontControllerServlet extends HttpServlet {
    
    private CommandFactory commandFactory;
    
    @Override
    public void init() throws ServletException {
        commandFactory = CommandFactory.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = getCommandPath(request);
        Command command = commandFactory.getCommand(path);
        
        try {
            String view = command.execute(request, response);
            
            if (view.startsWith("redirect:")) {
                String redirectPath = view.substring("redirect:".length());
                response.sendRedirect(request.getContextPath() + redirectPath);
            } else {
                request.getRequestDispatcher(view).forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error executing command", e);
        }
    }

    private String getCommandPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();

        String path = uri.substring(contextPath.length());

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        if (path.isEmpty() || path.equals("favicon.ico")) {
            return "start";
        }

        if (path.contains("?")) {
            path = path.substring(0, path.indexOf("?"));
        }
        if (path.contains("#")) {
            path = path.substring(0, path.indexOf("#"));
        }

        if (path.contains(".")) {
            path = path.substring(0, path.indexOf("."));
        }

        return path;
    }
}
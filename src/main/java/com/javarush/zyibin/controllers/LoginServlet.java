package com.javarush.zyibin.controllers;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import com.javarush.zyibin.service.AuthenticationService;
import com.javarush.zyibin.handler.RequestHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    
    private AuthenticationService authService;
    private RequestHandler requestHandler;
    
    @Override
    public void init() {
        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        this.authService = new AuthenticationService(userRepository);
        this.requestHandler = new RequestHandler();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        requestHandler.handleRequest(req, resp, () -> {
            User user = authService.authenticate(username, password);
            
            HttpSession session = req.getSession(true);
            session.setAttribute("currentUser", user);
            
            log.info("User {} successfully logged in", username);
            resp.sendRedirect(req.getContextPath() + "/home");
        }, "/WEB-INF/jsp/login.jsp");
    }
}

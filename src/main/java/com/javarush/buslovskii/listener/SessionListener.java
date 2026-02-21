package com.javarush.buslovskii.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    
    private static int activeSessions = 0;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
        System.out.println("Session created. Active sessions: " + activeSessions);
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions--;
        System.out.println("Session destroyed. Active sessions: " + activeSessions);
    }
    
    public static int getActiveSessions() {
        return activeSessions;
    }
}
package com.javarush.vasileva.cmd;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.javarush.vasileva.util.Link.LOGIN;

@SuppressWarnings("unused")
public class Logout implements Command {

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return LOGIN;
    }
}

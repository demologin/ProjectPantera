package com.javarush.vasileva.cmd;

import com.javarush.vasileva.util.Link;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Logout implements Command {

    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Link.LOGIN;
    }
}

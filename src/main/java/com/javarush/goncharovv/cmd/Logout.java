package com.javarush.goncharovv.cmd;

import com.javarush.goncharovv.util.Go;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class Logout implements Command {
    @Override
    public String doGet(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return Go.LOGIN;
    }
}

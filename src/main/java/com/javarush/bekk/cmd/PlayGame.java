package com.javarush.bekk.cmd;

import jakarta.servlet.http.HttpServletRequest;

public class PlayGame implements Command {

    @Override
    public String doPost(HttpServletRequest request) {
        request.getSession().setAttribute("playGame", this);

        return "";
    }
}

package com.javarush.vasileva.cmd;

import jakarta.servlet.http.HttpServletRequest;

import static com.javarush.vasileva.util.Link.LOGIN;

public class Login implements Command {

    @Override
    public String doGet(HttpServletRequest request) {
        return LOGIN;
    }


}

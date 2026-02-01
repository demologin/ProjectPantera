package com.javarush.bekk.cmd;

import com.javarush.bekk.service.UserService;
import com.javarush.bekk.util.Key;
import jakarta.servlet.http.HttpServletRequest;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String doPost(HttpServletRequest request) {
        String login = request.getParameter(Key.LOGIN);
        String password = request.getParameter(Key.PASSWORD);


        return "";

    }
}

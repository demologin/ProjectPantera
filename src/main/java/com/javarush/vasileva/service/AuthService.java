package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.javarush.vasileva.util.Key.USER;

public class AuthService {

    public void checkAdminAuthorization(HttpServletRequest req, String message) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute(USER);
        if (user == null || user.getRole() == Role.GUEST || user.getRole() == Role.USER) {
            throw new AppException(message);
        }
    }
}

package com.javarush.vasileva.util;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import com.javarush.vasileva.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.javarush.vasileva.util.Key.USER;

public class Helpers {

    private Helpers() {
    }

    public static void checkAdminAuthorization(HttpServletRequest req, String message) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute(USER);
        if (user == null || user.getRole() == Role.GUEST || user.getRole() == Role.USER) {
            throw new AppException(message);
        }
    }

    public static Long parseStringToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quest id");
        }
    }
}

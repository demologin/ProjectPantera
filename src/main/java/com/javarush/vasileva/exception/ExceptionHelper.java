package com.javarush.vasileva.exception;

import com.javarush.vasileva.util.Key;
import jakarta.servlet.http.HttpServletRequest;

public class ExceptionHelper {
    private ExceptionHelper() {
    }

    public static void createError(HttpServletRequest req, String errorMessage) {
        req.getSession().setAttribute(Key.ERROR, errorMessage);
    }
}

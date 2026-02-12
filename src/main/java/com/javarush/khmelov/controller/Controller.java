package com.javarush.khmelov.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Controller {
    String handle(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}

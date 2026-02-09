package com.javarush.popkov.controller;

import com.javarush.popkov.config.Winter;
import com.javarush.popkov.service.ImageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/quest-images/*")
public class QuestImageController extends HttpServlet {

    private final ImageService imageService = Winter.find(ImageService.class);

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();
        String target = req.getContextPath() + "/quest-images/";
        String nameImage = requestURI.replace(target, "");
        Path path = imageService.getQuestImagePath(nameImage);
        Files.copy(path, resp.getOutputStream());
    }
}

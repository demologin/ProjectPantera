package com.javarush.goncharov.controller;

import com.javarush.goncharov.service.ImageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/images/*")
public class ImageController extends HttpServlet {


    private final ImageService imageService = new ImageService();

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI(); // /images/qa, /images/analytic, /images/developer, /images/architector это прописано в jsp в src до каждой картинки
        String target = req.getContextPath() + "/images/"; // /images/
        String nameImage = requestURI.replace(target, ""); // qa
        Path path = imageService.getImagePath(nameImage); // "C:\Users\Puh\IdeaProjects\ProjectPantera_3Module\target\project-Pantera-1.0-SNAPSHOT\WEB-INF\images\qa.jpg"
        Files.copy(path, resp.getOutputStream());
    }
}

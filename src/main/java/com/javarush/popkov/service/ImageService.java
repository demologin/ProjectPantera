package com.javarush.popkov.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class ImageService {

    private static final String USER_IMAGES_FOLDER = "images";
    private static final String QUEST_IMAGES_FOLDER = "quest-images";
    private static final String FALLBACK_FOLDER = "img";
    private static final String PART_NAME = "image";
    private static final String NO_IMAGE_PNG = "no-image.png";
    private static final List<String> EXTENSIONS = List.of(
            ".jpg", ".jpeg", ".png", ".bmp", ".gif", ".webp"
    );

    public final Path WEB_INF = Paths.get(URI.create(
                    Objects.requireNonNull(
                            ImageService.class.getResource("/")
                    ).toString()))
            .getParent();

    private final Path userImagesFolder = WEB_INF.resolve(USER_IMAGES_FOLDER);
    private final Path questImagesFolder = WEB_INF.resolve(QUEST_IMAGES_FOLDER);
    private final Path fallbackFolder = WEB_INF.resolve(FALLBACK_FOLDER);

    @SneakyThrows
    public ImageService() {
        Files.createDirectories(userImagesFolder);
        Files.createDirectories(questImagesFolder);
    }

    @SneakyThrows
    public Path getUserImagePath(String filename) {
        return resolveImagePath(userImagesFolder, filename);
    }

    @SneakyThrows
    public Path getQuestImagePath(String filename) {
        return resolveImagePath(questImagesFolder, filename);
    }

    @SneakyThrows
    private Path resolveImagePath(Path baseFolder, String filename) {
        return EXTENSIONS.stream()
                .flatMap(ext -> List.of(
                        baseFolder.resolve(filename + ext),
                        fallbackFolder.resolve(filename + ext)
                ).stream())
                .filter(Files::exists)
                .findAny()
                .orElseGet(() -> {
                    Path fallback = fallbackFolder.resolve(NO_IMAGE_PNG);
                    return Files.exists(fallback)
                            ? fallback
                            : baseFolder.resolve(NO_IMAGE_PNG);
                });
    }

    public boolean uploadUserImage(HttpServletRequest req, String imageId) throws IOException, ServletException {
        return uploadImage(userImagesFolder, req, imageId);
    }

    public boolean uploadQuestImage(HttpServletRequest req, String imageId) throws IOException, ServletException {
        return uploadImage(questImagesFolder, req, imageId);
    }

    private boolean uploadImage(Path targetFolder, HttpServletRequest req, String imageId) throws IOException, ServletException {
        Part data = req.getPart(PART_NAME);
        if (Objects.nonNull(data) && data.getInputStream().available() > 0) {
            String filename = data.getSubmittedFileName();
            String ext = filename.substring(filename.lastIndexOf("."));
            deleteOldFiles(targetFolder, imageId);
            filename = imageId + ext;
            uploadImageInternal(targetFolder, filename, data.getInputStream());
            return true;
        }
        return false;
    }

    private void deleteOldFiles(Path imagesFolder, String filename) {
        EXTENSIONS.stream()
                .map(ext -> imagesFolder.resolve(filename + ext))
                .filter(Files::exists)
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @SneakyThrows
    private void uploadImageInternal(Path imagesFolder, String name, InputStream data) {
        try (data) {
            if (data.available() > 0) {
                Files.copy(data, imagesFolder.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

}
package com.javarush.goncharov.service;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageService {

    static final String IMAGES_FOLDER = "images";
    static final String PART_NAME = "image";
    static final String NO_IMAGE_PNG = "no-image.png";
    static final List<String> EXTENSIONS = List.of(
            ".jpg", ".jpeg", ".png", ".bmp", ".gif", ".webp"
    );

    private final Path imagesFolder;

    @SneakyThrows
    public ImageService() {
        URL url = ImageService.class.getResource("/"); //"file:/C:/Users/Puh/IdeaProjects/ProjectPantera_3Module/target/project-Pantera-1.0-SNAPSHOT/WEB-INF/classes/"
        Path startPath = Paths.get(Objects.requireNonNull(url).toURI());
        String webInf = "WEB-INF";
        Path webPath = startPath.getParent().endsWith(webInf)
                ? startPath.getParent()        //run in tomcat (webapp/WEB-INF)
                : startPath.resolve(webInf);   //embedded tomcat (resources/WEB-INF)
        imagesFolder = webPath.resolve(IMAGES_FOLDER);
        Files.createDirectories(imagesFolder);
    }

    @SneakyThrows
    public Path getImagePath(String filename) {
        return EXTENSIONS.stream()
                .map(ext -> imagesFolder.resolve(filename + ext))// resolve возвращает Path, преобразуем stream<String> >> stream<Path>
                .filter(Files::exists)// фильтруем результат
                .findAny() // находим любое совпадение
                .orElse(imagesFolder.resolve(NO_IMAGE_PNG)); // возврщаем найденное значение или если ничего неашлось то вернем no-image.png
    }
}

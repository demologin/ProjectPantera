package com.javarush.aleinik.util;

import com.javarush.aleinik.exception.InvalidResourceNameException;
import com.javarush.aleinik.exception.ResourceNotFoundException;

import java.net.URL;

public final class ResourcePathResolver {
    private static final ClassLoader CLASS_LOADER =
            ResourcePathResolver.class.getClassLoader();
    private static final String QUEST_DIRECTORY = "data/quests/%s.yml";

    private ResourcePathResolver() {
    }

    public static URL getQuestUrl(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new InvalidResourceNameException("Resource name cannot be null or blank");
        }

        String resource = QUEST_DIRECTORY.formatted(fileName);
        URL questUrl = CLASS_LOADER.getResource(resource);

        if (questUrl == null) {
            throw new ResourceNotFoundException("File with name %s could not be found".formatted(fileName));
        }
        return questUrl;
    }
}

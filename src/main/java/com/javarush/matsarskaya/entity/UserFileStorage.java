package com.javarush.matsarskaya.entity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UserFileStorage {
    private static final String USERS_FILE_PATH = "users.txt";
    private final Map<String, String> users = new HashMap<>();
    private boolean loaded = false;

    public UserFileStorage() {
        loadUsersIfNeeded();
    }

    public void saveUser(String username, String password) {
        loadUsersIfNeeded();
        users.put(username, password);
        saveUsersToFile();
    }

    public String getPasswordByUsername(String username) {
        loadUsersIfNeeded();
        return users.get(username);
    }

    public boolean userExists(String username) {
        loadUsersIfNeeded();
        return users.containsKey(username);
    }

    public Map<String, String> getAllUsers() {
        loadUsersIfNeeded();
        return new HashMap<>(users);
    }

    private void loadUsersIfNeeded() {
        if (!loaded) {
            loadUsersFromFile();
            loaded = true;
        }
    }

    private void saveUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE_PATH))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        if (!Files.exists(Paths.get(USERS_FILE_PATH))) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении из файла: " + e.getMessage());
        }
    }
}

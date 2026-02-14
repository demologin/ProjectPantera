package com.javarush.goncharov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public abstract class BaseTest {
    public static final String ROOT = "http://localhost:8088";
    protected static ObjectMapper mapper;
    protected static HttpClient httpClient;

    @SneakyThrows
    @BeforeAll
    static void init() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mapper = new ObjectMapper();
        httpClient = HttpClient.newBuilder()
                .cookieHandler(cookieManager)
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.of(2, ChronoUnit.SECONDS))
                .build();
        HttpRequest indexProbe = HttpRequest.newBuilder()
                .uri(URI.create(ROOT))
                .GET()
                .build();
        try {
            httpClient.send(indexProbe, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }
    }
    protected void createSession() throws IOException, InterruptedException {
        String loginForm = "login=Admin&password=123";
        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/login"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(loginForm))
                .build();

        httpClient.send(loginRequest, HttpResponse.BodyHandlers.ofString());
    }
}

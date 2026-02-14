package com.javarush.goncharov.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class PlayGameTest  extends BaseTest {
    @Test
    @DisplayName("When open play game page then body contains close tag")
    void whenOpenPlayGamePageThenBodyContainsCloseTag() throws IOException, InterruptedException {
//        createSession();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/play-game"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
//        Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        Assertions.assertTrue(response.body().contains("</body>"));
    }
}
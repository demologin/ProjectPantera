package com.javarush.goncharov.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class TeamControllerTest extends BaseTest{
    @Test
    @DisplayName("When open contactUs page then body conrains close tag")
    void whenOpenContactUsPageThenBodyConrainsCloseTag() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/our-team"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        Assertions.assertTrue(response.body().contains("</body>"));
    }
}
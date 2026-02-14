package com.javarush.goncharov.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class ListQuestsTest extends BaseTest {

    @Test
    @DisplayName("When open index page then body contains close tag")
    void whenOpenIndexPageThenBodyContainsCloseTag() throws IOException, InterruptedException {
        HttpRequest indexReq = HttpRequest.newBuilder()
                .uri(URI.create(ROOT))
                .GET()
                .build();
        HttpResponse<String> indexResponse = httpClient.send(
                indexReq,
                HttpResponse.BodyHandlers.ofString()
        );
        Assertions.assertEquals(HttpURLConnection.HTTP_OK, indexResponse.statusCode());
        Assertions.assertTrue(indexResponse.body().contains("</body>"));
    }
}
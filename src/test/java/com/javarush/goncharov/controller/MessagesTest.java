package com.javarush.goncharov.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class MessagesTest extends BaseTest {
    @Test
    @Tag("http-client")
    @DisplayName("When open message page then body contains close tag")
    void whenOpenMessagePageThenBodyContainsCloseTag() throws IOException, InterruptedException {
        HttpRequest indexReq = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/messages"))
                .GET()
                .build();
        HttpResponse<String> indexResponse = httpClient.send(
                indexReq,
                HttpResponse.BodyHandlers.ofString()
        );
        assertEquals(HttpURLConnection.HTTP_OK, indexResponse.statusCode());
        assertTrue(indexResponse.body().contains("</body>"));
    }
}
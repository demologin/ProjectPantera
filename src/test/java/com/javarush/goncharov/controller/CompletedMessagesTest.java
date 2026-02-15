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

class CompletedMessagesTest extends BaseTest{
    @Test
    @Tag("http-client")
    @DisplayName("When open completed message page then body contains close tag")
    void whenOpenCompletedMessagePageThenBodyContainsCloseTag() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/completed-messages"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        assertEquals(HttpURLConnection.HTTP_OK, response.statusCode());
        assertTrue(response.body().contains("</body>"));
    }

}
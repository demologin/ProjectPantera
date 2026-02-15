package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.service.QuestService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class DeleteQuestTest extends BaseTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    Quest quest;
    @Mock
    QuestService questService;
    @InjectMocks
    DeleteQuest servlet;

    static final Long TEST_QUEST_ID = 123L;
    static final String JSP_PATH = "/WEB-INF/delete-quest.jsp";

    @Test
    @DisplayName("Should set quest attribute and forward to JSP when quest exists")
    void shouldSetQuestAttributeAndForwardToJspWhenQuestExists() throws ServletException, IOException {
        quest.setId(TEST_QUEST_ID);
        quest.setName("Test Quest");

        when(request.getParameter("id")).thenReturn(String.valueOf(TEST_QUEST_ID));
        when(questService.get(TEST_QUEST_ID)).thenReturn(Optional.of(quest));
        when(request.getRequestDispatcher(JSP_PATH)).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("quest", quest);
        verify(request).getRequestDispatcher(JSP_PATH);
        verify(requestDispatcher).forward(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    @Tag("http-client")
    @DisplayName("When open delete quest page then body contains close tag")
    void whenOpenDeleteQuestPageThenBodyContainsSeTagIT() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/delete-quest" + "?id=" + TEST_QUEST_ID))
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
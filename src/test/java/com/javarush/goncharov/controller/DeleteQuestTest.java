package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.Quest;
import com.javarush.goncharov.service.QuestService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteQuestTest extends BaseTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private QuestService questService;

    @InjectMocks
    private DeleteQuest servlet;

    private static final Long TEST_QUEST_ID = 123L;
    private static final String JSP_PATH = "/WEB-INF/delete-quest.jsp";

    @Test
    @DisplayName("Should set quest attribute and forward to JSP when quest exists")
    void shouldSetQuestAttributeAndForwardToJspWhenQuestExists() throws ServletException, IOException, InterruptedException {
        Quest expectedQuest = new Quest();
        expectedQuest.setId(TEST_QUEST_ID);
        expectedQuest.setName("Test Quest");

        when(request.getParameter("id")).thenReturn(String.valueOf(TEST_QUEST_ID));
        when(questService.get(TEST_QUEST_ID)).thenReturn(Optional.of(expectedQuest));
        when(request.getRequestDispatcher(JSP_PATH)).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("quest", expectedQuest);
        verify(request).getRequestDispatcher(JSP_PATH);
        verify(requestDispatcher).forward(request, response);
        verifyNoMoreInteractions(response);
    }

    @Test
    @DisplayName("When open delete quest users page then body contains se tag")
    void whenOpenDeleteQuestPageThenBodyContainsSeTag() throws IOException, InterruptedException {
        createSession();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/delete-quest" + "?id=" + TEST_QUEST_ID))
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
package com.javarush.goncharov.controller;

import com.javarush.goncharov.model.User;
import com.javarush.goncharov.service.UserService;
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
class DeleteUserTest extends BaseTest{
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    UserService userService;
    @Mock
    User user;
    @InjectMocks
    DeleteUser servlet;

    final Long TEST_USER_ID = 123L;
    final String JSP_PATH = "/WEB-INF/delete-user.jsp";

    @Test
    @DisplayName("Should set user attribute and forward to JSP when user exists")
    void shouldSetUserAttributeAndForwardToJspWhenUserExists() throws ServletException, IOException {
        user.setId(TEST_USER_ID);
        user.setLogin("TestUser");

        when(request.getParameter("id")).thenReturn(String.valueOf(TEST_USER_ID));
        when(userService.get(TEST_USER_ID)).thenReturn(Optional.of(user));
        when(request.getRequestDispatcher(JSP_PATH)).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("user", user);
        verify(request).getRequestDispatcher(JSP_PATH);
        verify(requestDispatcher).forward(request, response);
        verifyNoInteractions(response);
    }

    @Test
    @Tag("http-client")
    @DisplayName("When open delete user page then body contains close tag")
    void whenOpenDeleteUserPageThenBodyContainsCloseTag() throws IOException, InterruptedException {
        createSession();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "/delete-user" + "?id=" + TEST_USER_ID))
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
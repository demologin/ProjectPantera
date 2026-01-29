package com.javarush.matsarskaya.controller;

import com.javarush.matsarskaya.cmd.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты для FrontController")
class FrontControllerTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Command command;

    @Mock
    private HttpResolver httpResolver;

    private FrontController frontController;

    @BeforeEach
    void setUp() {
        frontController = new FrontController(httpResolver);
    }

    @Test
    @DisplayName("GET запрос обрабатывается корректно")
    void testDoGet() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/home-page");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(httpResolver.resolve("/home-page")).thenReturn(command);
        when(command.doGet(request)).thenReturn("/WEB-INF/home-page.jsp");

        frontController.doGet(request, response);

        verify(httpResolver).resolve("/home-page");
        verify(command).doGet(request);
        verify(request).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    @DisplayName("POST запрос обрабатывается корректно")
    void testDoPost() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/login-page");
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(httpResolver.resolve("/login-page")).thenReturn(command);
        when(command.doPost(request)).thenReturn("/WEB-INF/login-page.jsp");

        frontController.doPost(request, response);

        verify(httpResolver).resolve("/login-page");
        verify(command).doPost(request);
        verify(request).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    @DisplayName("Перенаправление на домашнюю страницу при попытке доступа к защищённой странице без авторизации")
    void testProtectedPathWithoutAuthentication() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/quest-dragon");
        when(request.getMethod()).thenReturn("GET");
        when(request.getContextPath()).thenReturn("");

        frontController.doGet(request, response);

        verify(response).sendRedirect("/home-page");
    }

    @Test
    @DisplayName("Доступ к защищённой странице с авторизацией")
    void testProtectedPathWithAuthentication() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/quest-dragon");
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("username")).thenReturn("testuser");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(httpResolver.resolve("/quest-dragon")).thenReturn(command);
        when(command.doGet(request)).thenReturn("/WEB-INF/quest-dragon.jsp");

        frontController.doGet(request, response);

        verify(httpResolver).resolve("/quest-dragon");
        verify(command).doGet(request);
        verify(request).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }
}

package com.javarush.alimov.quest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;

import static org.mockito.Mockito.*;

class GameServletTest {

    private GameServlet servlet;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new GameServlet();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void doGet_ForwardsToGameJsp() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/game.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_StartToUfoChallenge() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(null);
        when(request.getParameter("answer")).thenReturn("any");
        when(request.getRequestDispatcher("/WEB-INF/game.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.UFO_CHALLENGE);
        verify(session).setAttribute("message", null);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_UfoChallenge_Accept() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.UFO_CHALLENGE);
        when(request.getParameter("answer")).thenReturn("accept");
        when(request.getRequestDispatcher("/WEB-INF/game.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.BRIDGE_CHOICE);
        verify(session).setAttribute("message", null);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_UfoChallenge_Reject_Lose() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.UFO_CHALLENGE);
        when(request.getParameter("answer")).thenReturn("reject");
        when(request.getRequestDispatcher("/WEB-INF/result.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.LOSE);
        verify(session).setAttribute("message", "Ты отклонил вызов. Поражение.");
        verify(session).setAttribute("gamesPlayed", 1);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_BridgeChoice_Go() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.BRIDGE_CHOICE);
        when(request.getParameter("answer")).thenReturn("go");
        when(request.getRequestDispatcher("/WEB-INF/game.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.IDENTITY_CHOICE);
        verify(session).setAttribute("message", null);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_BridgeChoice_Stay_Lose() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.BRIDGE_CHOICE);
        when(request.getParameter("answer")).thenReturn("stay");
        when(request.getRequestDispatcher("/WEB-INF/result.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.LOSE);
        verify(session).setAttribute("message", "Ты не пошёл на переговоры. Поражение.");
        verify(session).setAttribute("gamesPlayed", 1);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_IdentityChoice_Truth_Win() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.IDENTITY_CHOICE);
        when(session.getAttribute("gamesPlayed")).thenReturn(2);
        when(request.getParameter("answer")).thenReturn("truth");
        when(request.getRequestDispatcher("/WEB-INF/result.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.WIN);
        verify(session).setAttribute("message", "Тебя вернули домой. Победа!");
        verify(session).setAttribute("gamesPlayed", 3);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_IdentityChoice_Lie_Lose() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.IDENTITY_CHOICE);
        when(session.getAttribute("gamesPlayed")).thenReturn(null);
        when(request.getParameter("answer")).thenReturn("lie");
        when(request.getRequestDispatcher("/WEB-INF/result.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.LOSE);
        verify(session).setAttribute("message", "Твою ложь разоблачили. Поражение.");
        verify(session).setAttribute("gamesPlayed", 1);
        verify(dispatcher).forward(request, response);
    }
    @Test
    void doPost_LoseScenario_IncrementsGamesPlayed() throws ServletException, IOException {
        when(session.getAttribute("state")).thenReturn(GameState.IDENTITY_CHOICE);
        when(session.getAttribute("gamesPlayed")).thenReturn(5);
        when(request.getParameter("answer")).thenReturn("lie");
        when(request.getRequestDispatcher("/WEB-INF/result.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(session).setAttribute("state", GameState.LOSE);
        verify(session).setAttribute("message", "Твою ложь разоблачили. Поражение.");
        verify(session).setAttribute("gamesPlayed", 6);
        verify(dispatcher).forward(request, response);
    }

}


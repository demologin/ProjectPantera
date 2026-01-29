package test.java;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import martynov.GameState;
import martynov.QuestServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class QuestServletTest {

    @InjectMocks
    private QuestServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ServletContext servletContext;

    private GameState state;

    @BeforeEach
    void setUp() {
        state = new GameState();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(state);
        when(request.getRequestDispatcher("/quest.jsp")).thenReturn(dispatcher);
    }

    @Test
    void testProcessAnswerCorrect() throws ServletException, IOException {
        state.setStep(1);
        state.setPlayerName("Stalker");

        when(request.getParameter("action")).thenReturn("answer");
        when(request.getParameter("choice")).thenReturn("trust");

        servlet.doGet(request, response);

        assertEquals(2, state.getStep(), "После выбора 'trust' на 1 шаге, текущий шаг должен быть 2");

        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void testProcessAnswerWrong() throws ServletException, IOException {
        state.setStep(1);

        when(request.getParameter("action")).thenReturn("answer");
        when(request.getParameter("choice")).thenReturn("run");

        servlet.doGet(request, response);

        assertEquals(-1, state.getStep(), "После неправильного выбора шаг должен стать -1");
    }
}

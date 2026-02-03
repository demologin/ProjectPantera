package pantera.textquest;

import com.javarush.wladimir.GameServlet;
import com.javarush.wladimir.dao.InMemoryUserDao;
import com.javarush.wladimir.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private RequestDispatcher dispatcher;

    private GameServlet servlet;
    private InMemoryUserDao dao;

    @BeforeEach
    void setUp() throws ServletException {
        servlet = new GameServlet();
        dao = new InMemoryUserDao();

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userDao")).thenReturn(dao);
        when(request.getRequestDispatcher("/game.jsp")).thenReturn(dispatcher);

        servlet.init(servletConfig);
    }


    @Test
    void doGet_withoutLogin_redirectsToLogin() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/text-quest");
        when(session.getAttribute("currentUser")).thenReturn(null);

        servlet.service(request, response);

        verify(response).sendRedirect("/text-quest/login");
    }

    @Test
    void doGet_withLogin_forwardsToGameJsp() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("start");

        when(request.getMethod()).thenReturn("GET");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(u);

        servlet.service(request, response);

        verify(dispatcher).forward(request, response);
    }


    @Test
    void doPost_startAction() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("start");
        dao.add(u);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/text-quest");
        when(session.getAttribute("currentUser")).thenReturn(u);
        when(request.getParameter("action")).thenReturn("start");

        servlet.service(request, response);

        assertEquals("zone", u.getProgress());
        verify(response).sendRedirect("/text-quest/game");
    }

    @Test
    void doPost_exploreRuinsAction() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("zone");
        dao.add(u);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(u);
        when(request.getParameter("action")).thenReturn("explore_ruins");

        servlet.service(request, response);

        assertEquals("ruins", u.getProgress());
    }

    @Test
    void doPost_enterAnomalyAction() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("zone");
        dao.add(u);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(u);
        when(request.getParameter("action")).thenReturn("enter_anomaly");

        servlet.service(request, response);

        assertEquals("anomaly", u.getProgress());
    }

    @Test
    void doPost_searchArtifactsAction() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("ruins");
        dao.add(u);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(u);
        when(request.getParameter("action")).thenReturn("search_artifacts");

        servlet.service(request, response);

        assertEquals("artifact_found", u.getProgress());
    }

    @Test
    void doPost_resetAction() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("treasure");
        dao.add(u);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(u);
        when(request.getParameter("action")).thenReturn("reset");

        servlet.service(request, response);

        assertEquals("start", u.getProgress());
    }

    @Test
    void doPost_withoutLogin_redirectsToLogin() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/text-quest");
        when(session.getAttribute("currentUser")).thenReturn(null);
        when(request.getParameter("action")).thenReturn("start");

        servlet.service(request, response);

        verify(response).sendRedirect("/text-quest/login");
    }

    @Test
    void doPost_invalidAction_doesNothing() throws ServletException, IOException {
        User u = new User();
        u.setUsername("player");
        u.setProgress("zone");
        dao.add(u);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(u);
        when(request.getParameter("action")).thenReturn("invalid_action");

        servlet.service(request, response);

        assertEquals("zone", u.getProgress());
    }
}

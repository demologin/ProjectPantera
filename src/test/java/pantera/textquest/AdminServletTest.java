package pantera.textquest;

import com.javarush.wladimir.AdminServlet;
import com.javarush.wladimir.dao.InMemoryUserDao;
import com.javarush.wladimir.model.Role;
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
public class AdminServletTest {

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

    private AdminServlet servlet;
    private InMemoryUserDao dao;

    @BeforeEach
    void setUp() throws ServletException {
        servlet = new AdminServlet();
        dao = new InMemoryUserDao();

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userDao")).thenReturn(dao);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/text-quest");
        when(request.getRequestDispatcher("/admin.jsp")).thenReturn(dispatcher);

        servlet.init(servletConfig);
    }


    @Test
    void doGet_adminAccess_allowed() throws ServletException, IOException {
        User admin = dao.findByUsername("admin").get();
        when(request.getMethod()).thenReturn("GET");
        when(session.getAttribute("currentUser")).thenReturn(admin);

        servlet.service(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doGet_nonAdmin_denied() throws ServletException, IOException {
        User user = new User();
        user.setUsername("user");
        user.setRole(Role.USER);

        when(request.getMethod()).thenReturn("GET");
        when(session.getAttribute("currentUser")).thenReturn(user);

        servlet.service(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doGet_notLoggedIn_denied() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(session.getAttribute("currentUser")).thenReturn(null);

        servlet.service(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }


    @Test
    void doPost_adminDeletesUser() throws ServletException, IOException {
        User admin = dao.findByUsername("admin").get();
        User toDelete = new User();
        toDelete.setUsername("todelete");
        dao.add(toDelete);

        when(request.getMethod()).thenReturn("POST");
        when(session.getAttribute("currentUser")).thenReturn(admin);
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("username")).thenReturn("todelete");

        servlet.service(request, response);

        assertFalse(dao.findByUsername("todelete").isPresent());
        verify(response).sendRedirect("/text-quest/admin");
    }

    @Test
    void doPost_nonAdmin_denied() throws ServletException, IOException {
        User user = new User();
        user.setUsername("user");
        user.setRole(Role.USER);

        when(request.getMethod()).thenReturn("POST");
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(request.getParameter("action")).thenReturn("delete");

        servlet.service(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doPost_invalidAction_ignored() throws ServletException, IOException {
        User admin = dao.findByUsername("admin").get();
        User target = new User();
        target.setUsername("target");
        dao.add(target);

        when(request.getMethod()).thenReturn("POST");
        when(session.getAttribute("currentUser")).thenReturn(admin);
        when(request.getParameter("action")).thenReturn("invalid");

        servlet.service(request, response);

        assertTrue(dao.findByUsername("target").isPresent());
    }
}

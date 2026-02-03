package pantera.textquest;

import com.javarush.wladimir.RegisterServlet;
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
public class RegisterServletTest {

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

    private RegisterServlet servlet;
    private InMemoryUserDao dao;

    @BeforeEach
    void setUp() throws ServletException {
        servlet = new RegisterServlet();
        dao = new InMemoryUserDao();

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userDao")).thenReturn(dao);
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(dispatcher);

        servlet.init(servletConfig);
    }


    @Test
    void doGet_forwardsToRegisterJsp() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");

        servlet.service(request, response);

        verify(dispatcher).forward(request, response);
    }


    @Test
    void doPost_successfulRegistration() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/text-quest");
        when(request.getParameter("username")).thenReturn("newuser");
        when(request.getParameter("password")).thenReturn("password123");

        servlet.service(request, response);

        assertTrue(dao.findByUsername("newuser").isPresent());

        User user = dao.findByUsername("newuser").get();
        assertEquals("newuser", user.getUsername());
        assertEquals("password123", user.getPassword());

        verify(session).setAttribute(eq("currentUser"), any(User.class));
        verify(response).sendRedirect("/text-quest/game");
    }


    @Test
    void doPost_duplicateUsername_fails() throws ServletException, IOException {
        User existing = new User();
        existing.setUsername("existing");
        existing.setPassword("pass");
        dao.add(existing);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn("existing");
        when(request.getParameter("password")).thenReturn("newpass");

        servlet.service(request, response);

        verify(request).setAttribute(eq("error"), contains("already exists"));
        verify(dispatcher).forward(request, response);
    }


    @Test
    void doPost_emptyUsername_fails() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("password");

        servlet.service(request, response);

        verify(request).setAttribute(eq("error"), contains("Invalid"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_nullUsername_fails() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("password");

        servlet.service(request, response);

        verify(request).setAttribute(eq("error"), contains("Invalid"));
        verify(dispatcher).forward(request, response);
    }
}

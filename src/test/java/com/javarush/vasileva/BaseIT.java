package com.javarush.vasileva;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class BaseIT {
    protected final HttpServletRequest req;
    protected final HttpServletResponse resp;
    protected final HttpSession session;
    protected final Config config;
    protected final ServletConfig servletConfig;
    protected final ServletContext servletContext;

    protected User testAdmin;
    protected User testUser;
    protected User testGuest;

    public BaseIT() {
        config = Winter.find(Config.class);
        config.fillRepository();

        servletConfig = Mockito.mock(ServletConfig.class);
        servletContext = Mockito.mock(ServletContext.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);

        req = Mockito.mock(HttpServletRequest.class);
        resp = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);

        testAdmin = User.builder()
                .id(1L)
                .login("testAdmin")
                .email("admin@test.com")
                .password("testAdmin")
                .role(Role.ADMIN)
                .build();

        testUser = User.builder()
                .id(2L)
                .login("testUser")
                .email("user@test.com")
                .password("testUser")
                .role(Role.USER)
                .build();

        testGuest = User.builder()
                .id(3L)
                .login("testGuest")
                .email("guest@test.com")
                .password("testGuest")
                .role(Role.GUEST)
                .build();
    }


}

package com.javarush.goncharov.controller;


import com.javarush.goncharov.model.Role;
import com.javarush.goncharov.model.User;
import com.javarush.goncharov.repository.Storage;
import com.javarush.goncharov.repository.UserRepository;
import com.javarush.goncharov.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/edit-user")
public class EditUser extends HttpServlet {

    private final Storage userStorage = Storage.getInstance();
    private final UserService userService = new UserService(new UserRepository(userStorage));

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute("roles", Role.values());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idUser = Long.parseLong(req.getParameter("id"));
        userService.get(idUser).ifPresent(user -> req.setAttribute("user", user));
        req.getRequestDispatcher("/WEB-INF/edit-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = "";
        Long idUser = Long.parseLong(req.getParameter("id"));
        Optional<User> userFind = userService.get(idUser);
        if (userFind.isPresent() && req.getParameter("role") == null){
            role = userFind.get().getRole().toString();
        } else {
            role = req.getParameter("role");
        }
        User user = User.builder()
                .id(idUser)
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .role(Role.valueOf(role))
                .email(req.getParameter("email"))
                .build();
        userService.update(user);
        resp.sendRedirect("/profile");
    }
}

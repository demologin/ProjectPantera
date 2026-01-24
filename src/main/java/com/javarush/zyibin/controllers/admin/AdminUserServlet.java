package com.javarush.zyibin.controllers.admin;

import com.javarush.zyibin.model.User;
import com.javarush.zyibin.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AdminUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET /admin/users");

        HttpSession session = req.getSession(false);

        User admin = (User) session.getAttribute("currentUser");

        UserRepository userRepository = (UserRepository) getServletContext().getAttribute("userRepository");

        List<User> users = userRepository.findAll();

        log.info("Admin {} loaded users list, count={}", admin.getUsername(), users.size());

        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/users.jsp").forward(req, resp);
    }
}

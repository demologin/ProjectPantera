package bakhtin;

import bakhtin.Quest.Question.Answer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/bakhtin", "/bakhtin/", "/bakhtin/home", ""})
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Перенаправляем на quest сервлет
        resp.sendRedirect(req.getContextPath() + "/hello");
    }
}

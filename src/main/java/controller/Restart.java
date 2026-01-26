package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "restart", value = "/restart")
public class Restart extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int count = Integer.parseInt(session.getAttribute("count").toString());
        session.setAttribute("count", count + 1);

        String name = req.getParameter("name");
        req.setAttribute("name", name);
        req.getServletContext().getRequestDispatcher("/indexRestart.jsp").forward(req, resp);
    }
}

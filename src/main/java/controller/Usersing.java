package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "usersing", value = "/usersing")
public class Usersing extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();
        String chekValue = null;


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("someChek")) {
                    chekValue = cookie.getValue();
                }
            }
        }

        if (chekValue.equals("remember")) {

            req.setAttribute("chekValue", "remember");
            req.setAttribute("password", "1234");
            req.setAttribute("name", "admin");

        }else
        {

            req.setAttribute("password", "");
            req.setAttribute("name", "");
        }

        req.getServletContext().getRequestDispatcher("/usersing.jsp").forward(req, resp);


    }
}
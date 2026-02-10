package com.javarush.khmelov.something;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/play")
public class Play extends HttpServlet {
    int qid;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=== PLAY POST ===");
        System.out.println("answerId = " + req.getParameter("answerId"));
        System.out.println("questionId = " + req.getParameter("questionId"));
        try{
            qid = Integer.parseInt(req.getParameter("questId"));
        }
        catch (Exception e){

        }

        int ans = Integer.parseInt(req.getParameter("answerId"));
        int que = Integer.parseInt(req.getParameter("questionId"));
        if(ans %2 == 0){
            req.setAttribute("lose", Create.questRepository.getById(qid).lose(que));
            req.getRequestDispatcher("/WEB-INF/lose.jsp").forward(req, resp);
            return;
        }

        if ( Create.questRepository.getById(qid).getQuestionById(que+1 ) == null){
            req.setAttribute("win", Create.questRepository.getById(qid).getWin());
            req.getRequestDispatcher("/WEB-INF/win.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("question", Create.questRepository.getById(qid).getQuestionById(que+1 ));

        req.getRequestDispatcher("/WEB-INF/game.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("question", Create.questRepository.getById(Integer.parseInt(req.getParameter("id"))).getQuestionById(0));
        req.setAttribute("questId",Integer.parseInt(req.getParameter("id")) );
        req.getRequestDispatcher("/WEB-INF/game.jsp").forward(req, resp);
    }
}

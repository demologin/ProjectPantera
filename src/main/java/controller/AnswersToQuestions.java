package controller;

import entity.*;
import repository.Repository;
import services.RepositoryService;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "answersToQuestions", value = "/answers")
public class AnswersToQuestions extends HttpServlet {

    Repository repository;
    RepositoryService repositoryService;
    Question question;

    public void init() {
        try {
            repository = Repository.getRepository();
            repositoryService = new RepositoryService(repository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        String name = session.getAttribute("name").toString();

        String n = request.getParameter("n");
        Answer answer = repositoryService.getAnswerById(n);

        if (answer.getResult() == Result.WIN) {
            String textWin = answer.getResultText();
            request.setAttribute("textWin", textWin);
            request.getServletContext().getRequestDispatcher("/win.jsp").forward(request, response);
        } else if (answer.getResult() == Result.LOSE) {
            String textLose = answer.getResultText();
            request.setAttribute("textLose", textLose);
            request.getServletContext().getRequestDispatcher("/lose.jsp").forward(request, response);
        } else {
            question = repositoryService.getQuestionById(answer.getIdQuestion());

            Answer answer1 = repositoryService.getAnswerById(question.getAnswersId().get(0));
            Answer answer2 = repositoryService.getAnswerById(question.getAnswersId().get(1));

            request.setAttribute("name", name);
            request.setAttribute("answer1", answer1.getAnswer());
            request.setAttribute("answer2", answer2.getAnswer());
            request.setAttribute("question", question.getQuestion());
            request.setAttribute("idAnswer1", answer1.getId());
            request.setAttribute("idAnswer2", answer2.getId());

            request.getServletContext().getRequestDispatcher("/qa.jsp").forward(request, response);
        }
    }

}
package controller;

import entity.Answer;
import entity.Question;
import repository.Repository;
import services.RepositoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "greeting", value = "/greeting")
public class Greeting extends HttpServlet {

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
        question = repositoryService.getQuestionById("1");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirectionRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String name = req.getParameter("yourname");

        HttpSession session = req.getSession();
        session.setAttribute("count", 0);
        session.setAttribute("name", name);

        redirectionRequest(req, resp);
    }

    private void redirectionRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Answer answer1 = repositoryService.getAnswerById(question.getAnswersId().get(0));
        Answer answer2 = repositoryService.getAnswerById(question.getAnswersId().get(1));

        req.setAttribute("answer1", answer1.getAnswer());
        req.setAttribute("answer2", answer2.getAnswer());
        req.setAttribute("question", question.getQuestion());
        req.setAttribute("idAnswer1", answer1.getId());
        req.setAttribute("idAnswer2", answer2.getId());

        req.getServletContext().getRequestDispatcher("/qa.jsp").forward(req, resp);
    }
}

package com.javarush.zyibin.controllers;

import com.javarush.zyibin.session.SessionUtils;
import com.javarush.zyibin.state.InterviewState;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(QuestionServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET / question");

        HttpSession session = req.getSession(false);

        if (!SessionUtils.hasInterview(session)) {
            log.debug("No active interview in session, redirecting to /start");
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        InterviewState state = SessionUtils.getInterviewState(session);

        if (state.isFinished()) {
            log.info("Interview finished, redirecting to /result");
            resp.sendRedirect(req.getContextPath() + "/result");
            return;
        }
        log.debug("Displaying question {} of {}", state.getCurrentIndex(), state.getTotalQuestions());

        req.setAttribute("topics", state.getTopics());
        req.setAttribute("question", state.getCurrentQuestion());
        req.setAttribute("questionNumber", state.getCurrentIndex() + 1);
        req.setAttribute("totalQuestions", state.getTotalQuestions());

        req.getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("POST /question");

        HttpSession session = req.getSession(false);

        if (!SessionUtils.hasInterview(session)) {
            log.debug("No active interview in session, redirecting to /start");
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        String answerIndexParam = req.getParameter("answerIndex");

        if (answerIndexParam == null) {
            log.debug("No answer selected, redirecting back to question");
            resp.sendRedirect(req.getContextPath() + "/question");
            return;
        }
        int selectedIndex = Integer.parseInt(answerIndexParam);
        InterviewState state = SessionUtils.getInterviewState(session);

        if (selectedIndex == state.getCurrentQuestion().getCorrectAnswerIndex()) {
            log.debug("Correct answer selected");
            state.incrementScore();
        } else {
            log.debug("Incorrect answer selected");
        }

        state.moveToNextQuestion();

        log.debug("Moving to next question, current index is now {}", state.getCurrentIndex());


        resp.sendRedirect(req.getContextPath() + "/question");


    }
}

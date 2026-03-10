package bakhtin;

import bakhtin.Quest.Question;
import bakhtin.Quest.Question.Answer;
import bakhtin.exceptions.IllegalActionException;
import bakhtin.exceptions.NoActiveQuestException;
import bakhtin.exceptions.NoAnswerGivenException;
import bakhtin.exceptions.NoQuestionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

import static bakhtin.SessionConstants.*;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String actionStr = req.getParameter(SessionConstants.ACTION_PARAM);
        Action action = Action.parse(actionStr);

        HttpSession session = req.getSession();

        switch (action) {
            case ANSWER -> doAnswer(req, resp, session);
            case RESTART -> doRestart(req, resp, session);
            case EXIT -> doExit(req, resp, session);
            default -> throw new IllegalActionException("Invalid action: " + actionStr);
        }
    }

    private void doExit(HttpServletRequest req, HttpServletResponse resp,
            HttpSession session) throws ServletException, IOException {
        session.invalidate();
        req.getRequestDispatcher("/WEB-INF/hello.jsp").forward(req, resp);
    }

    private void doAnswer(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        Object attr = session.getAttribute(CURRENT_QUESTION_ATTR);
        if (!(attr instanceof Question question)) {
            throw new NoQuestionException("Question not found in session");
        }
        String answerId = req.getParameter(ANSWER_ID_PARAM);
        long id;
        if (StringUtils.isNotEmpty(answerId)) {
            id = Long.parseLong(answerId);
        } else {
            throw new NoAnswerGivenException("Answer ID not found in session");
        }

        Question nextQuestion = getNextQuestion(question, id);

        if (nextQuestion.isFinish()) {
            doFinish(req, session, nextQuestion);
        }

        session.setAttribute(CURRENT_QUESTION_ATTR, nextQuestion);
        req.setAttribute(CURRENT_QUESTION_REQUEST_ATTR, nextQuestion);
        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    private void doFinish(HttpServletRequest req, HttpSession session,
            Question nextQuestion) {
        if (nextQuestion.isWin()) {
            req.setAttribute(WIN_ATTR, "true");
        } else {
            req.setAttribute(WIN_ATTR, "false");
        }

        // Увеличиваем счетчик сыгранных игр
        int gamesPlayed = (int) session.getAttribute(GAMES_PLAYED_ATTR);
        session.setAttribute(GAMES_PLAYED_ATTR, gamesPlayed + 1);
    }


    protected Question getNextQuestion(Question question, Long answerId) {
        Answer answer = question.getAnswer(answerId);
        if (answer == null) {
            throw new NoAnswerGivenException("Answer not found for ID: " + answerId);
        }

        Question nextQuestion = answer.getNextQuestion();

        if (nextQuestion == null) {
            throw new NoQuestionException("No next question found for answer ID: " + answerId);
        }
        return nextQuestion;
    }

    protected void doRestart(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        req.removeAttribute(CURRENT_QUESTION_REQUEST_ATTR);
        req.removeAttribute(ANSWER_ID_PARAM);

        Object objQuest = session.getAttribute(QUEST_ATTR);

        if (objQuest == null) {
            throw new NoQuestionException("Quest not found in session");
        }

        Quest quest = (Quest) objQuest;

        Question startQuestion = quest.getStartQuestion();
        session.setAttribute(CURRENT_QUESTION_ATTR, startQuestion);
        req.setAttribute(CURRENT_QUESTION_REQUEST_ATTR, startQuestion);

        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute(FIRST_GET_PROCESSED_ATTR) == null) {
            prepareSession(session, req);
        }
        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    private void prepareSession(HttpSession session, HttpServletRequest request) {
        session.setAttribute(FIRST_GET_PROCESSED_ATTR, true);

        if (session.getAttribute(GAMES_PLAYED_ATTR) == null) {
            session.setAttribute(GAMES_PLAYED_ATTR, 0);
        }

    Quest newQuest = getStartQuest();

        session.setAttribute(QUEST_ATTR, newQuest);
        session.setAttribute(CURRENT_QUESTION_ATTR, newQuest.getStartQuestion());
        request.setAttribute(CURRENT_QUESTION_REQUEST_ATTR, newQuest.getStartQuestion());
    }

    protected Quest getStartQuest() {
        Quest newQuest = Quest.builder().name("base_quest").startQuestion(
                Question.builder().question("Tы потерял память. Принять вызов НЛО?")
                        .answer(Answer.builder().answer("Принять вызов").nextQuestion(
                                        Question.builder().question(
                                                        "Ты принял вызов. Поднимаешься на мостик к капитану?")
                                                .answer(Answer.builder().answer("Подняться на мостик")
                                                        .nextQuestion(Question.builder()
                                                                .question("Ты поднялся на мостик. Ты кто?")
                                                                .answer(Answer.builder()
                                                                        .answer("Рассказать правду о себе")
                                                                        .nextQuestion(Question.builder()
                                                                                .question(
                                                                                        "Тебя вернули домой. Победа!")
                                                                                .win(true).build()).build())
                                                                .answer(Answer.builder()
                                                                        .answer("Солгать о себе")
                                                                        .nextQuestion(Question.builder()
                                                                                .question(
                                                                                        "Твою ложь разоблачили. Поражение.")
                                                                                .win(false).build())
                                                                        .build()).build()).build())
                                                .answer(Answer.builder()
                                                        .answer("Отказаться подниматься на мостик")
                                                        .nextQuestion(Question.builder().question(
                                                                        "Ты не пошел на переговоры. Поражение.")
                                                                .win(false).build()).build()).build())
                                .build()).answer(Answer.builder().answer("Отклонить вызов")
                                .nextQuestion(Question.builder().question("Ты отклонил вызов. Поражение.")
                                        .win(false).build()).build()).build()).build();
        return newQuest;
    }
}

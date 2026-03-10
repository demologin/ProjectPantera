package bakhtin;

import bakhtin.Quest.Question;
import bakhtin.Quest.Question.Answer;
import bakhtin.exсeptions.IllegalActionException;
import bakhtin.exсeptions.NoActiveQuestException;
import bakhtin.exсeptions.NoAnswerGivenException;
import bakhtin.exсeptions.NoQuestionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String actionStr = req.getParameter("action");
        Actions action = getAction(actionStr);

        HttpSession session = req.getSession();

        if (action == Actions.ANSWER) {
            Object attr = session.getAttribute("currentQuestion");
            if (!(attr instanceof Question question)) {
                throw new NoQuestionException("Question not found in session");
            }
            String answerId = req.getParameter("answerId");
            Long id;

            if (answerId != null && !answerId.isEmpty() && answerId.matches("\\d+")) {
                id = Long.parseLong(answerId);
            } else {
                throw new NoAnswerGivenException("Answer ID not found in session");
            }

            Question nextQuestion = getNextQuestion(question, id);

            if (nextQuestion.isTerminal()) {
                if (nextQuestion.isWin()) {
                    req.setAttribute("win", "true");
                } else {
                    req.setAttribute("win", "false");
                }

                // Увеличиваем счетчик сыгранных игр
                int gamesPlayed = (int) session.getAttribute("gamesPlayed");
                session.setAttribute("gamesPlayed", gamesPlayed + 1);
                req.setAttribute("gamesPlayed", gamesPlayed + 1);
            }

            req.setAttribute("currentQuestion", nextQuestion);
            session.setAttribute("currentQuestion", nextQuestion);
            req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);

        } else if (action == Actions.RESTART) {
            restart(req, resp, session);

        } else if (action == Actions.EXIT) {
            session.invalidate();
            req.getRequestDispatcher("/WEB-INF/hello.jsp").forward(req, resp);
        } else {
            throw new IllegalActionException("Invalid action: " + actionStr);
        }
    }

    protected Actions getAction(String actionStr) {
        Actions action = null;
        if (actionStr != null) {
            try {
                action = Actions.valueOf(actionStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalActionException("Action not found for: " + actionStr);
            }
        }
        return action;
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

    protected void restart(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        session.removeAttribute("currentQuestion");
        session.removeAttribute("answerID");

        Quest quest = (Quest) session.getAttribute("quest");

        if (quest == null) {
            throw new NoQuestionException("Quest not found in session");
        }

        Question startQuestion = quest.getStartQuestion();
        session.setAttribute("currentQuestion", startQuestion);
        req.setAttribute("currentQuestion", startQuestion);

        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getSession().getAttribute("firstGetProcessed") == null) {
            req.getSession().setAttribute("firstGetProcessed", true);

            // Инициализация счетчика игр
            if (req.getSession().getAttribute("gamesPlayed") == null) {
                req.getSession().setAttribute("gamesPlayed", 0);
            }

            Quest newQuest = getStartQuest();

            req.setAttribute("quest", newQuest);

            Question startQuestion = newQuest.getStartQuestion();
            HttpSession session = req.getSession();
            session.setAttribute("quest", newQuest);
            session.setAttribute("currentQuestion", startQuestion);
        }

        HttpSession session = req.getSession();
        Quest.Question currentQuestion = (Quest.Question) session.getAttribute("currentQuestion");

        if (currentQuestion == null) {
            Quest quest = (Quest) session.getAttribute("quest");
            if (quest == null) {
                throw new NoActiveQuestException("Quest not found in session");
            }
            currentQuestion = quest.getStartQuestion();
        }

        session.setAttribute("currentQuestion", currentQuestion);

        req.setAttribute("currentQuestion", currentQuestion);
        req.setAttribute("gamesPlayed", session.getAttribute("gamesPlayed"));
        req.getRequestDispatcher("/WEB-INF/quest.jsp").forward(req, resp);
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

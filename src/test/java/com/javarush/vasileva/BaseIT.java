package com.javarush.vasileva;

import com.javarush.vasileva.config.Config;
import com.javarush.vasileva.config.Winter;
import com.javarush.vasileva.entity.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class BaseIT {
    protected final HttpServletRequest req;
    protected final HttpServletResponse resp;
    protected final HttpSession session;
    protected final Config config;
    protected final ServletConfig servletConfig;
    protected final ServletContext servletContext;
    protected final Logger LOGGER;

    protected User testAdmin;
    protected User testUser;
    protected User testGuest;

    protected Game testGame;
    protected GameState testGameState;

    protected Quest testQuest;
    protected Quest testQuestWithoutId;

    protected Question testQuestion1;
    protected Question testQuestion2;
    protected Question testQuestion3;

    protected Answer testAnswer1;
    protected Answer testAnswer2;

    protected String testJson;
    protected String testJsonWithoutId;

    protected UserStats testUserStats;

    public BaseIT() {
        config = Winter.find(Config.class);
        config.fillRepository();

        servletConfig = Mockito.mock(ServletConfig.class);
        servletContext = Mockito.mock(ServletContext.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);

        req = Mockito.mock(HttpServletRequest.class);
        resp = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);

        LOGGER = Mockito.mock(Logger.class);

        testAdmin = User.builder()
                .id(1L)
                .login("testAdmin")
                .email("admin@test.com")
                .password("testAdmin")
                .role(Role.ADMIN)
                .build();

        testUser = User.builder()
                .id(2L)
                .login("testUser")
                .email("user@test.com")
                .password("testUser")
                .role(Role.USER)
                .build();

        testGuest = User.builder()
                .id(3L)
                .login("testGuest")
                .email("guest@test.com")
                .password("testGuest")
                .role(Role.GUEST)
                .build();

        testQuest = Quest.builder()
                .id(1L)
                .title("testQuest")
                .description("testQuest")
                .text("testQuest")
                .startQuestionId(1L)
                .build();

        testQuestWithoutId = Quest.builder()
                .title("testQuest")
                .description("testQuest")
                .text("testQuest")
                .startQuestionId(1L)
                .build();

        testQuestion1 = Question.builder()
                .generatedId(1L)
                .quest(testQuest)
                .label("1")
                .text("testQuestion1")
                .answers(new ArrayList<>())
                .build();

        testQuestion2 = Question.builder()
                .generatedId(2L)
                .quest(testQuest)
                .label("+8")
                .text("testQuestion2")
                .build();

        testQuestion3 = Question.builder()
                .generatedId(3L)
                .quest(testQuest)
                .label("-9")
                .text("testQuestion3")
                .build();

        testAnswer1 = Answer.builder()
                .id(1L)
                .question(testQuestion1)
                .text("testAnswer1")
                .nextQuestionLabel("+8")
                .build();

        testAnswer2 = Answer.builder()
                .id(2L)
                .question(testQuestion1)
                .text("testAnswer2")
                .nextQuestionLabel("-9")
                .build();

        testQuestion1.getAnswers().add(testAnswer1);
        testQuestion1.getAnswers().add(testAnswer2);

        testQuest.setQuestions(List.of(testQuestion1, testQuestion2, testQuestion3));
        testQuestWithoutId.setQuestions(List.of(testQuestion1, testQuestion2, testQuestion3));

        testGameState = GameState.builder()
                .currentQuest(testQuest)
                .currentQuestion(testQuestion1)
                .user(testUser)
                .isCompleted(false)
                .build();

        testGame = Game.builder()
                .id(1L)
                .quest(testQuest)
                .user(testUser)
                .currentQuestionId(testQuestion1.getGeneratedId())
                .gameState(testGameState)
                .build();

        testJson = """
            {
              "id": 1,
              "title": "testQuest",
              "description": "testQuest",
              "text": "testQuest",
              "questions": [
                {
                  "label": "1",
                  "text": "testQuestion1",
                  "answers": [
                    {
                      "nextQuestionLabel": "+8",
                      "text": "testAnswer1"
                    },
                    {
                      "nextQuestionLabel": "-9",
                      "text": "testAnswer2"
                    }
                  ]
                },
                {
                      "label": "+8",
                      "text": "testQuestion2"
                },
                {
                      "label": "-9",
                      "text": "testQuestion2"
                }
              ]
            }""";

        testJsonWithoutId = """
            {
              "title": "testQuest",
              "description": "testQuest",
              "text": "testQuest",
              "questions": [
                {
                  "label": "1",
                  "text": "testQuestion1",
                  "answers": [
                    {
                      "nextQuestionLabel": "+8",
                      "text": "testAnswer1"
                    },
                    {
                      "nextQuestionLabel": "-9",
                      "text": "testAnswer2"
                    }
                  ]
                },
                {
                      "label": "+8",
                      "text": "testQuestion2"
                },
                {
                      "label": "-9",
                      "text": "testQuestion2"
                }
              ]
            }""";

        testUserStats = UserStats.builder()
                .id(1L)
                .user(testUser)
                .total(3)
                .wins(2)
                .losses(1)
                .build();
    }
}

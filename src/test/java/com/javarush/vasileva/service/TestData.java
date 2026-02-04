package com.javarush.vasileva.service;

import com.javarush.vasileva.entity.*;

import java.util.List;

public final class TestData {
    private TestData() {
    }

    // QUEST
    public static final Long VALID_QUEST_ID = 1L;
    public static final Long NON_EXISTENT_QUEST_ID = 999L;
    public static final String NON_EXISTENT_QUEST_ID_STR = "999";
    public static final String NULL_QUEST_ID_STR = null;
    public static final String EMPTY_QUEST_ID_STR = "";
    public static final String QUEST_TITLE = "Test Quest";

    // USER
    public static final Long NON_EXISTENT_USER_ID = 999L;
    public static final Long VALID_USER_ID = 1L;
    public static final String VALID_USER_LOGIN = "testUser";
    public static final String VALID_USER_EMAIL = "test@email.com";
    public static final String VALID_USER_PASSWORD = "testPassword";
    public static final String INVALID_USER_EMAIL = "invalid@email.com";
    public static final String INVALID_USER_PASSWORD = "invalidPassword";
    public static final String EMPTY_USER_ID_STR = "";
    public static final String NULL_USER_ID_STR = null;

    // QUESTION
    public static final Long VALID_QUESTION_ID = 1L;
    public static final Long NON_EXISTENT_QUESTION_ID = 999L;
    public static final String VALID_LABEL = "Q1";
    public static final String INVALID_LABEL = "XYZ";

    // GAME
    public static final Long VALID_GAME_ID = 100L;
    public static final Long NON_EXISTENT_GAME_ID = 99999L;

    // ANSWER
    public static final Long VALID_ANSWER_ID = 1L;
    public static final Long NON_EXISTENT_ANSWER_ID = 999L;
    public static final String VALID_ANSWER_TEXT = "Правильный ответ";

    /*    ======================================= USER =================================== */
    public static User createValidUser() {
        return User.builder()
                .id(VALID_USER_ID)
                .login(VALID_USER_LOGIN)
                .email(VALID_USER_EMAIL)
                .password(VALID_USER_PASSWORD)
                .role(Role.USER)
                .build();
    }

    /*    ======================================= QUEST =================================== */

    public static Quest createValidQuest() {
        return Quest.builder()
                .id(VALID_QUEST_ID)
                .title(QUEST_TITLE)
                .description("Test Quest Description")
                .build();
    }

    public static Quest createQuestWithId(Long id) {
        return Quest.builder()
                .id(id)
                .title("Quest #" + id)
                .build();
    }

    public static List<Quest> createMultipleQuests() {
        return List.of(
                createQuestWithId(1L),
                createQuestWithId(2L),
                createQuestWithId(3L)
        );
    }

    /*    ======================================= QUESTION =================================== */

    public static Question createQuestionWithAnswers() {
        Question question = Question.builder()
                .generatedId(VALID_QUESTION_ID)
                .label(VALID_LABEL)
                .questId(VALID_QUEST_ID)
                .text("Какой цвет неба?")
                .build();
        List<Answer> answers = List.of(
                createAnswer(1L, "Синий"),
                createAnswer(2L, "Голубой"));
        question.setAnswers(answers);
        return question;
    }

    public static Question createFinalQuestion() {
        return Question.builder()
                .generatedId(2L)
                .label("END")
                .questId(VALID_QUEST_ID)
                .text("Это финальный вопрос.")
                .answers(null)
                .build();
    }

    public static Question createSimpleQuestion() {
        return Question.builder()
                .generatedId(3L)
                .label("Q3")
                .questId(VALID_QUEST_ID)
                .text("Простой вопрос.")
                .build();
    }

    public static List<Question> createMultipleQuestions() {
        return List.of(
                createQuestionWithAnswers(),
                createFinalQuestion(),
                createSimpleQuestion()
        );
    }

    /*    ======================================= ANSWER =================================== */

    public static Answer createAnswer(Long id, String text) {
        return Answer.builder()
                .id(id)
                .text(text)
                .build();
    }

    public static Answer createValidAnswer() {
        return Answer.builder()
                .id(VALID_ANSWER_ID)
                .text(VALID_ANSWER_TEXT)
                .build();
    }

    public static List<Answer> createMultipleAnswers() {
        return List.of(
                createValidAnswer(),
                createAnswer(2L, "Второй ответ"),
                createAnswer(3L, "Третий ответ")
        );
    }

    /*    ======================================= GAME =================================== */

    public static GameState createInitialGameState() {
        Question currentQuestion = Question.builder()
                .generatedId(VALID_QUESTION_ID)
                .text("Первый вопрос квеста?")
                .build();
        return GameState.builder()
                .currentQuestion(currentQuestion)
                .isCompleted(false)
                .build();
    }

    public static GameState createNextGameState() {
        Question nextQuestion = Question.builder()
                .generatedId(2L)
                .text("Следующий вопрос?")
                .build();
        return GameState.builder()
                .currentQuestion(nextQuestion)
                .isCompleted(false)
                .build();
    }

    public static Game createGame(Long questId, Long userId, Long currentQuestionId, GameState state) {
        return Game.builder()
                .questId(questId)
                .userId(userId)
                .currentQuestionId(currentQuestionId)
                .gameState(state)
                .build();
    }

    public static Game createSavedGame() {
        Game game = createGame(VALID_QUEST_ID, VALID_USER_ID, 1L, createInitialGameState());
        game.setId(VALID_GAME_ID);
        return game;
    }
}

package bakhtin;

/**
 * Константы для атрибутов сессии, запроса и параметров запроса
 */
public final class SessionConstants {

    // Параметры запроса
    public static final String ACTION_PARAM = "action";
    public static final String ANSWER_ID_PARAM = "answerId";

    // Атрибуты сессии
    public static final String QUEST_ATTR = "quest";
    public static final String CURRENT_QUESTION_ATTR = "currentQuestion";
    public static final String FIRST_GET_PROCESSED_ATTR = "firstGetProcessed";
    public static final String GAMES_PLAYED_ATTR = "gamesPlayed";

    // Атрибуты запроса
    public static final String CURRENT_QUESTION_REQUEST_ATTR = "currentQuestion";
    public static final String WIN_ATTR = "win";
    public static final String ERROR_MESSAGE_ATTR = "errorMessage";

    private SessionConstants() {
        // Приватный конструктор для предотвращения создания экземпляров
    }
}
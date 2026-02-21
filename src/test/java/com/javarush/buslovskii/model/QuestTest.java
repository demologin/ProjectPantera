package com.javarush.buslovskii.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestTest {

    private QuestManager questManager;

    @BeforeEach
    void setUp() {
        questManager = QuestManager.getInstance();
        resetQuestStats();
    }

    @AfterEach
    void tearDown() {
        resetQuestStats();
    }

    private void resetQuestStats() {
        try {
            java.lang.reflect.Field statsField = QuestManager.class.getDeclaredField("questStats");
            statsField.setAccessible(true);
            java.util.Map<String, Integer> stats = (java.util.Map<String, Integer>) statsField.get(questManager);
            stats.clear();
        } catch (Exception ignored) {
        }
    }

    @Test
    void testQuestManagerInitialization() {
        List<Quest> quests = questManager.getAllQuests();
        assertNotNull(quests, "Quests list should not be null");
        assertFalse(quests.isEmpty(), "Should have at least 1 quest");
        assertTrue(quests.size() >= 3, "Should have at least 3 quests");
    }

    @Test
    void testGetQuestById() {
        Quest treasureQuest = questManager.getQuest("treasure-hunt");
        assertNotNull(treasureQuest, "Treasure hunt quest should exist");
        assertEquals("Поиск сокровищ в замке", treasureQuest.getTitle(), "Treasure hunt quest title");

        Quest spaceQuest = questManager.getQuest("space-adventure");
        assertNotNull(spaceQuest, "Space adventure quest should exist");
        assertEquals("Sci-Fi/Выживание", spaceQuest.getGenre(), "Space adventure quest genre");

        Quest detectiveQuest = questManager.getQuest("detective-story");
        assertNotNull(detectiveQuest, "Detective story quest should exist");
        assertEquals(5, detectiveQuest.getDifficultyLevel(), "Detective story difficulty");
    }

    @Test
    void testGetNonExistentQuest() {
        Quest nonExistent = questManager.getQuest("non-existent");
        assertNull(nonExistent, "Non-existent quest should return null");
    }

    @Test
    void testTreasureHuntQuestQuestions() {
        Quest quest = questManager.getQuest("treasure-hunt");
        assertNotNull(quest, "Treasure hunt quest should exist");

        Question startQuestion = quest.getStartQuestion();
        assertNotNull(startQuestion, "Start question should exist");
        assertEquals(1, startQuestion.getId(), "Start question ID should be 1");
        assertNotNull(startQuestion.getText(), "Start question text should not be null");
        assertNotNull(startQuestion.getOption1(), "Option 1 should not be null");
        assertNotNull(startQuestion.getOption2(), "Option 2 should not be null");

        Map<Integer, Question> questions = quest.getQuestions();
        assertNotNull(questions, "Questions map should not be null");
        assertFalse(questions.isEmpty(), "Questions map should not be empty");
        assertTrue(questions.size() >= 10, "Should have multiple questions");
    }

    @Test
    void testSpaceAdventureQuestQuestions() {
        Quest quest = questManager.getQuest("space-adventure");
        assertNotNull(quest, "Space adventure quest should exist");

        Question startQuestion = quest.getStartQuestion();
        assertNotNull(startQuestion, "Start question should exist");
        assertEquals("Ваш космический корабль терпит крушение на неизвестной планете. Что делать?",
                startQuestion.getText(),
                "Start question text should match");
    }

    @Test
    void testDetectiveStoryQuestQuestions() {
        Quest quest = questManager.getQuest("detective-story");
        assertNotNull(quest, "Detective story quest should exist");

        Question startQuestion = quest.getStartQuestion();
        assertNotNull(startQuestion, "Start question should exist");
        assertTrue(startQuestion.getText().contains("детектив"),
                "Start question text should contain detective theme"
        );
    }

    @Test
    void testTreasureHuntVictoryConditions() {
        Quest quest = questManager.getQuest("treasure-hunt");

        // Проверяем победные вопросы
        assertTrue(quest.isVictory(7), "Question 7 should be victory");
        assertTrue(quest.isVictory(8), "Question 8 should be victory");
        assertTrue(quest.isVictory(9), "Question 9 should be victory");
        assertTrue(quest.isVictory(12), "Question 12 should be victory");

        assertFalse(quest.isVictory(10), "Question 10 should not be victory");
        assertFalse(quest.isVictory(11), "Question 11 should not be victory");

        assertNotNull("Victory message for question 7 should exist",
                quest.getVictoryMessage(7));
        assertNotNull("Defeat message for question 10 should exist",
                quest.getDefeatMessage(10));

        assertFalse(quest.getVictoryMessage(7).isEmpty(),
                "Victory message should not be empty");
        assertFalse(quest.getDefeatMessage(10).isEmpty(),
                "Defeat message should not be empty");
    }

    @Test
    void testSpaceAdventureVictoryConditions() {
        Quest quest = questManager.getQuest("space-adventure");

        assertTrue(quest.isVictory(7), "Question 7 should be victory");
        assertTrue(quest.isVictory(9), "Question 9 should be victory");

        assertFalse(quest.isVictory(6), "Question 6 should not be victory");
        assertFalse(quest.isVictory(8), "Question 8 should not be victory");
        assertFalse(quest.isVictory(10), "Question 10 should not be victory");

        assertEquals("Вы успешно вернулись на Землю и стали героем!",
                quest.getVictoryMessage(7), "Victory message for question 7");
        assertEquals("Корабль уничтожен. Вы погибли.",
                quest.getDefeatMessage(6), "Defeat message for question 6"
        );
    }

    @Test
    void testDetectiveStoryVictoryConditions() {
        Quest quest = questManager.getQuest("detective-story");

        assertTrue(quest.isVictory(7), "Question 7 should be victory");
        assertTrue(quest.isVictory(9), "Question 9 should be victory");
        assertTrue(quest.isVictory(12), "Question 12 should be victory");

        assertFalse(quest.isVictory(8), "Question 8 should not be victory");
        assertFalse(quest.isVictory(10), "Question 10 should not be victory");
        assertFalse(quest.isVictory(11), "Question 11 should not be victory");
    }

    @Test
    void testQuestStats() {
        String questId = "detective-story";

        int initialPlays = questManager.getQuestPlays(questId);
        assertEquals(0, initialPlays, "Initial plays should be 0");

        questManager.incrementQuestPlays(questId);
        assertEquals(1, questManager.getQuestPlays(questId), "Quest plays should increment to 1");

        questManager.incrementQuestPlays(questId);
        assertEquals(2, questManager.getQuestPlays(questId), "Quest plays should increment to 2");

        assertEquals(0, questManager.getQuestPlays("treasure-hunt"), "Other quest plays should be 0");
    }

    @Test
    void testGetAllQuestStats() {
        questManager.incrementQuestPlays("treasure-hunt");
        questManager.incrementQuestPlays("treasure-hunt");
        questManager.incrementQuestPlays("space-adventure");

        Map<String, Integer> allStats = questManager.getAllQuestStats();
        assertNotNull(allStats, "Stats map should not be null");

        assertEquals(2, allStats.get("treasure-hunt").intValue(), "Treasure hunt plays");
        assertEquals(1, allStats.get("space-adventure").intValue(), "Space adventure plays");

        // Детективный квест должен быть 0 или отсутствовать
        Integer detectivePlays = allStats.get("detective-story");
        assertTrue(detectivePlays == null || detectivePlays == 0,
                "Detective story plays should be 0 or null");
    }

    @Test
    void testFilterQuestsByGenre() {
        List<Quest> horrorQuests = questManager.getQuestsByGenre("Хоррор/Приключения");
        assertNotNull(horrorQuests, "Horror quests list should not be null");
        assertFalse(horrorQuests.isEmpty(), "Should find horror quests");

        boolean foundTreasureHunt = false;
        for (Quest q : horrorQuests) {
            if ("treasure-hunt".equals(q.getId())) {
                foundTreasureHunt = true;
                break;
            }
        }
        assertTrue(foundTreasureHunt, "Treasure hunt should be in horror genre");

        List<Quest> sciFiQuests = questManager.getQuestsByGenre("Sci-Fi/Выживание");
        assertNotNull(sciFiQuests, "Sci-Fi quests list should not be null");
        assertFalse(sciFiQuests.isEmpty(), "Should find sci-fi quests");

        List<Quest> nonExistentGenre = questManager.getQuestsByGenre("Non-existent");
        assertNotNull(nonExistentGenre, "Non-existent genre should return empty list");
        assertTrue(nonExistentGenre.isEmpty(), "Non-existent genre should return empty list");
    }

    @Test
    void testFilterQuestsByDifficulty() {
        List<Quest> difficulty3Quests = questManager.getQuestsByDifficulty(3);
        assertNotNull(difficulty3Quests, "Difficulty 3 quests list should not be null");
        assertFalse(difficulty3Quests.isEmpty(), "Should find difficulty 3 quests");

        boolean foundTreasureHunt = false;
        for (Quest q : difficulty3Quests) {
            if ("treasure-hunt".equals(q.getId())) {
                foundTreasureHunt = true;
                break;
            }
        }
        assertTrue(foundTreasureHunt, "Treasure hunt should be difficulty 3");

        List<Quest> difficulty5Quests = questManager.getQuestsByDifficulty(5);
        assertNotNull(difficulty5Quests, "Difficulty 5 quests list should not be null");
        assertFalse(difficulty5Quests.isEmpty(), "Should find difficulty 5 quests");

        List<Quest> nonExistentDifficulty = questManager.getQuestsByDifficulty(10);
        assertNotNull(nonExistentDifficulty, "Non-existent difficulty should return empty list");
        assertTrue(nonExistentDifficulty.isEmpty(), "Non-existent difficulty should return empty list");
    }

    @Test
    void testGameState() {
        GameState gameState = new GameState();

        assertEquals(1, gameState.getCurrentQuestionId(), "Initial question should be 1");
        assertFalse(gameState.isGameOver(), "Game should not be over initially");
        assertFalse(gameState.isVictory(), "Game should not be victory initially");
        assertEquals(0, gameState.getGamesPlayed(), "Games played should start at 0");
        assertEquals(0, gameState.getWins(), "Wins should start at 0");
        assertEquals(0, gameState.getLosses(), "Losses should start at 0");

        gameState.setPlayerName("TestPlayer");
        assertEquals("TestPlayer", gameState.getPlayerName(),"Player name should be set correctly");

        gameState.setCurrentQuestId("test-quest");
        assertEquals("test-quest", gameState.getCurrentQuestId(),"Quest ID should be set correctly");

        gameState.addWin();
        gameState.addWin();
        assertEquals(2, gameState.getWins(), "Wins should be 2");

        gameState.addLoss();
        assertEquals(1, gameState.getLosses(), "Losses should be 1");

        gameState.incrementGamesPlayed();
        assertEquals(1, gameState.getGamesPlayed(), "Games played should be 1");

        gameState.setGameOver(true);
        assertTrue(gameState.isGameOver(), "Game should be over");

        gameState.setVictory(true);
        assertTrue(gameState.isVictory(), "Game should be victory");
    }

    @Test
    void testQuestionModel() {
        Question question = new Question(1, "Test question", "Option A", "Option B", 2, 3);

        assertEquals(1, question.getId(), "ID should be 1");
        assertEquals("Test question", question.getText(), "Text should match");
        assertEquals("Option A", question.getOption1(), "Option 1 should match");
        assertEquals("Option B", question.getOption2(), "Option 2 should match");
        assertEquals(2, question.getNextIdOption1(), "Next ID for option 1 should be 2");
        assertEquals(3, question.getNextIdOption2(), "Next ID for option 2 should be 3");
        assertFalse(question.isFinal(), "Should not be final by default");
        assertNull(question.getVictoryMessage(), "Victory message should be null");
        assertNull(question.getDefeatMessage(), "Defeat message should be null");

        Question finalQuestion = new Question(2, "Final", "Restart", "Exit", 1, 1, true, "You win!", "You lose!");

        assertTrue(finalQuestion.isFinal(), "Should be final");
        assertEquals("You win!", finalQuestion.getVictoryMessage(), "Victory message should match");
        assertEquals("You lose!", finalQuestion.getDefeatMessage(), "Defeat message should match");
    }


    @Test
    void testTreasureHuntGamePathToVictory() {
        Quest quest = questManager.getQuest("treasure-hunt");
        Map<Integer, Question> questions = quest.getQuestions();

        int currentId = 1;

        Question q1 = questions.get(currentId);
        currentId = q1.getNextIdOption2();
        assertEquals(3, currentId, "Should be at question 3");

        Question q3 = questions.get(currentId);
        currentId = q3.getNextIdOption1();
        assertEquals(6, currentId, "Should be at question 6");

        Question q6 = questions.get(currentId);
        currentId = q6.getNextIdOption2();
        assertEquals(12, currentId, "Should be at question 12");

        Question finalQuestion = questions.get(currentId);
        assertTrue(finalQuestion.isFinal(), "Should be final question");
        assertTrue(quest.isVictory(currentId), "Should be victory");
    }

    @Test
    void testTreasureHuntGamePathToDefeat() {
        Quest quest = questManager.getQuest("treasure-hunt");
        Map<Integer, Question> questions = quest.getQuestions();

        int currentId = 1;

        Question q1 = questions.get(currentId);
        currentId = q1.getNextIdOption1();
        assertEquals(2, currentId, "Should be at question 2");

        Question q2 = questions.get(currentId);
        currentId = q2.getNextIdOption2();
        assertEquals(5, currentId, "Should be at question 5");

        Question q5 = questions.get(currentId);
        currentId = q5.getNextIdOption2();
        assertEquals(10, currentId, "Should be at question 10");

        Question finalQuestion = questions.get(currentId);
        assertTrue(finalQuestion.isFinal(), "Should be final question");
        assertFalse(quest.isVictory(currentId), "Should not be victory");
    }

    @Test
    void testSpaceAdventureGamePaths() {
        Quest quest = questManager.getQuest("space-adventure");
        Map<Integer, Question> questions = quest.getQuestions();

        int victoryId = 1;
        victoryId = questions.get(victoryId).getNextIdOption1();
        victoryId = questions.get(victoryId).getNextIdOption1();
        victoryId = questions.get(victoryId).getNextIdOption1();

        assertTrue(quest.isVictory(victoryId), "Should be victory");

        int defeatId = 1;
        defeatId = questions.get(defeatId).getNextIdOption2();
        defeatId = questions.get(defeatId).getNextIdOption2();

        assertFalse(quest.isVictory(defeatId), "Should not be victory");
    }
}
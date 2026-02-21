package com.javarush.buslovskii.model.quests;

import com.javarush.buslovskii.model.Quest;
import com.javarush.buslovskii.model.Question;

import java.util.HashMap;
import java.util.Map;

public class SpaceAdventureQuest implements Quest {
    private static final long serialVersionUID = 1L;

    private Map<Integer, Question> questions;

    public SpaceAdventureQuest() {
        initializeQuestions();
    }

    private void initializeQuestions() {
        questions = new HashMap<>();

        questions.put(1, new Question(1,
                "Ваш космический корабль терпит крушение на неизвестной планете. Что делать?",
                "Исследовать поверхность",
                "Попытаться починить корабль",
                2, 3));

        questions.put(2, new Question(2,
                "Вы выходите на поверхность. Видите странные сооружения вдалеке.",
                "Направиться к сооружениям",
                "Вернуться к кораблю",
                4, 3));

        questions.put(3, new Question(3,
                "Ремонт корабля требует редких кристаллов. Они есть в пещере неподалеку.",
                "Идти в пещеру",
                "Продолжить ремонт без кристаллов",
                5, 6));

        questions.put(4, new Question(4,
                "Вы находите заброшенную базу пришельцев. Там есть топливо!",
                "Взять топливо",
                "Поискать что-то еще",
                7, 8));

        questions.put(5, new Question(5,
                "В пещере вы находите кристаллы, но просыпается огромный паук!",
                "Сражаться с пауком",
                "Убегать",
                9, 10));

        questions.put(6, new Question(6,
                "Без кристаллов двигатель взрывается...",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Корабль уничтожен. Вы погибли."));

        questions.put(7, new Question(7,
                "С топливом вы взлетаете и возвращаетесь на Землю!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Вы успешно вернулись на Землю и стали героем!",
                null));

        questions.put(8, new Question(8,
                "Вы натыкаетесь на ловушку и попадаете в плен к пришельцам.",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Вас взяли в плен. Игра окончена."));

        questions.put(9, new Question(9,
                "Вы победили паука и взяли кристаллы. Корабль починен!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Вы починили корабль и улетели домой! Победа!",
                null));

        questions.put(10, new Question(10,
                "Паук догоняет вас и...",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Паук оказался быстрее. Вы погибли."));
    }

    @Override
    public String getId() {
        return "space-adventure";
    }

    @Override
    public String getTitle() {
        return "Космическое приключение";
    }

    @Override
    public String getDescription() {
        return "Вы - капитан космического корабля, потерпевшего крушение на неизвестной планете. " +
                "Сможете ли вы выжить и вернуться домой?";
    }

    @Override
    public String getGenre() {
        return "Sci-Fi/Выживание";
    }

    @Override
    public int getDifficultyLevel() {
        return 4;
    }

    @Override
    public String getBackgroundImage() {
        return "/images/space.jpg";
    }

    @Override
    public Map<Integer, Question> getQuestions() {
        return questions;
    }

    @Override
    public Question getStartQuestion() {
        return questions.get(1);
    }

    @Override
    public boolean isVictory(int questionId) {
        return questionId == 7 || questionId == 9;
    }

    @Override
    public String getVictoryMessage(int questionId) {
        Question q = questions.get(questionId);
        return q != null ? q.getVictoryMessage() : "Миссия выполнена!";
    }

    @Override
    public String getDefeatMessage(int questionId) {
        Question q = questions.get(questionId);
        return q != null ? q.getDefeatMessage() : "Миссия провалена.";
    }
}
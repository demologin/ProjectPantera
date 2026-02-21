package com.javarush.buslovskii.model.quests;

import com.javarush.buslovskii.model.Quest;
import com.javarush.buslovskii.model.Question;

import java.util.HashMap;
import java.util.Map;

public class TreasureHuntQuest implements Quest {
    private static final long serialVersionUID = 1L;

    private Map<Integer, Question> questions;

    public TreasureHuntQuest() {
        initializeQuestions();
    }

    private void initializeQuestions() {
        questions = new HashMap<>();

        questions.put(1, new Question(1,
                "Вы стоите перед входом в заброшенный замок. Ночь, луна освещает древние стены. Что вы сделаете?",
                "Войти через главный вход",
                "Найти тайный вход",
                2, 3));

        questions.put(2, new Question(2,
                "Вы входите в главный зал. Слышен странный шорох. На стене висит старый меч.",
                "Взять меч и исследовать звук",
                "Игнорировать меч и пойти дальше",
                4, 5));

        questions.put(3, new Question(3,
                "Вы нашли потайную дверь за кустами. Она ведет в подвал замка.",
                "Спуститься в подвал",
                "Вернуться к главному входу",
                6, 2));

        questions.put(4, new Question(4,
                "Вы взяли меч и пошли на звук. Это оказался призрак старого рыцаря!",
                "Сразиться с призраком",
                "Попытаться поговорить",
                7, 8));

        questions.put(5, new Question(5,
                "Вы пошли дальше без оружия. В темноте вы слышите шаги...",
                "Спрятаться",
                "Бежать",
                9, 10));

        questions.put(6, new Question(6,
                "В подвале вы нашли сундук с сокровищами! Но вход завалило камнями.",
                "Попытаться разобрать завал",
                "Искать другой выход",
                11, 12));

        questions.put(7, new Question(7,
                "Вы победили призрака! Он рассыпался в прах, оставив после себя древний амулет.",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Вы нашли древний амулет и выбрались из замка! Сокровища ваши!",
                null));

        questions.put(8, new Question(8,
                "Призрак оказался дружелюбным! Он показал вам тайный ход к сокровищам.",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Вы нашли золото и подружились с призраком! Победа!",
                null));

        questions.put(9, new Question(9,
                "Вы спрятались, но это был всего лишь кот. Вы нашли выход с мешком монет!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Вы нашли сокровища и благополучно выбрались!",
                null));

        questions.put(10, new Question(10,
                "Вы побежали и упали в яму с водой. Выбраться не удалось...",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Вы утонули в подземной реке. Игра окончена."));

        questions.put(11, new Question(11,
                "Вы разобрали завал, но камни упали и придавили вас...",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Вы погибли под завалом. Игра окончена."));

        questions.put(12, new Question(12,
                "Вы нашли старый туннель, ведущий прямо к выходу! Сокровища ваши!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Вы нашли выход с сокровищами! Поздравляем!",
                null));
    }

    @Override
    public String getId() {
        return "treasure-hunt";
    }

    @Override
    public String getTitle() {
        return "Поиск сокровищ в замке";
    }

    @Override
    public String getDescription() {
        return "Отправляйтесь в заброшенный замок на поиски легендарных сокровищ. " +
                "Вас ждут встречи с призраками, ловушки и загадочные подземелья.";
    }

    @Override
    public String getGenre() {
        return "Хоррор/Приключения";
    }

    @Override
    public int getDifficultyLevel() {
        return 3;
    }

    @Override
    public String getBackgroundImage() {
        return "/images/castle.jpg";
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
        return questionId == 7 || questionId == 8 || questionId == 9 || questionId == 12;
    }

    @Override
    public String getVictoryMessage(int questionId) {
        Question q = questions.get(questionId);
        return q != null ? q.getVictoryMessage() : "Поздравляем! Вы победили!";
    }

    @Override
    public String getDefeatMessage(int questionId) {
        Question q = questions.get(questionId);
        return q != null ? q.getDefeatMessage() : "К сожалению, вы проиграли.";
    }
}
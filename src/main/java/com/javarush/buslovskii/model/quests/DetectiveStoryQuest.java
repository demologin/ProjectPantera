package com.javarush.buslovskii.model.quests;

import com.javarush.buslovskii.model.Quest;
import com.javarush.buslovskii.model.Question;

import java.util.HashMap;
import java.util.Map;

public class DetectiveStoryQuest implements Quest {
    private static final long serialVersionUID = 1L;

    private Map<Integer, Question> questions;

    public DetectiveStoryQuest() {
        initializeQuestions();
    }

    private void initializeQuestions() {
        questions = new HashMap<>();

        questions.put(1, new Question(1,
                "Вы - известный детектив. Вас вызывают на место убийства миллионера. С чего начнете?",
                "Осмотреть тело",
                "Поговорить с прислугой",
                2, 3));

        questions.put(2, new Question(2,
                "На теле найдены странные следы укуса. Похоже на вампира!",
                "Искать вампира",
                "Искать другие улики",
                4, 5));

        questions.put(3, new Question(3,
                "Прислуга говорит, что видели таинственного незнакомца в плаще.",
                "Искать незнакомца",
                "Проверить комнату убитого",
                6, 5));

        questions.put(4, new Question(4,
                "Вы находите логово вампира в подвале!",
                "Войти с распятием",
                "Войти с колом",
                7, 8));

        questions.put(5, new Question(5,
                "В комнате убитого найден дневник с зашифрованными записями.",
                "Расшифровать записи",
                "Показать эксперту",
                9, 10));

        questions.put(6, new Question(6,
                "Незнакомец оказывается братом убитого. Он предлагает взятку, чтобы вы закрыли дело.",
                "Взять взятку",
                "Арестовать его",
                11, 12));

        questions.put(7, new Question(7,
                "Вампир боится распятия. Вы заставляете его признаться в убийстве!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Дело раскрыто! Убийца - вампир, и он обезврежен!",
                null));

        questions.put(8, new Question(8,
                "Кол оказался недостаточно острым. Вампир атакует...",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Вампир убивает вас. Дело остается нераскрытым."));

        questions.put(9, new Question(9,
                "В дневнике зашифровано имя убийцы - это дворецкий!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Дворецкий арестован! Дело раскрыто!",
                null));

        questions.put(10, new Question(10,
                "Эксперт теряет улики. Дело закрыто из-за отсутствия доказательств.",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Убийца остается на свободе. Дело провалено."));

        questions.put(11, new Question(11,
                "Взятка оказывается фальшивой, и вас арестовывают за коррупцию.",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                null,
                "Вы в тюрьме, а убийца на свободе."));

        questions.put(12, new Question(12,
                "Брат убийцы сознается в преступлении из ревности!",
                "Начать заново",
                "Выбрать другой квест",
                1, 1, true,
                "Преступник сознался! Дело раскрыто!",
                null));
    }

    @Override
    public String getId() {
        return "detective-story";
    }

    @Override
    public String getTitle() {
        return "Детективная история";
    }

    @Override
    public String getDescription() {
        return "Раскройте убийство миллионера в старом особняке. Под подозрением все: " +
                "от прислуги до таинственного вампира!";
    }

    @Override
    public String getGenre() {
        return "Детектив/Мистика";
    }

    @Override
    public int getDifficultyLevel() {
        return 5;
    }

    @Override
    public String getBackgroundImage() {
        return "/images/mansion.jpg";
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
        return questionId == 7 || questionId == 9 || questionId == 12;
    }

    @Override
    public String getVictoryMessage(int questionId) {
        Question q = questions.get(questionId);
        return q != null ? q.getVictoryMessage() : "Дело раскрыто!";
    }

    @Override
    public String getDefeatMessage(int questionId) {
        Question q = questions.get(questionId);
        return q != null ? q.getDefeatMessage() : "Дело провалено.";
    }
}
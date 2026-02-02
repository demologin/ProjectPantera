package com.javarush.bekk.config;

import com.javarush.bekk.entity.Answer;
import com.javarush.bekk.entity.Question;
import com.javarush.bekk.entity.Role;
import com.javarush.bekk.entity.User;
import com.javarush.bekk.repository.QuestionRepository;
import com.javarush.bekk.service.QuestionService;
import com.javarush.bekk.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Config {

    private final UserService userService;
private final QuestionRepository questionRepository;
    public void fillEmptyRepository() {
        if (userService.get(1L).isEmpty()){
            User admin = buildUser("Carl", "admin", Role.ADMIN);
            userService.create(admin);
            User alisa = buildUser("Alisa", "qwerty", Role.USER);
            userService.create(alisa);
            User bob = buildUser("Bob", "123", Role.GUEST);
            userService.create(bob);
        }
    }

    private static User buildUser(String name, String password, Role role) {
        return  User.builder()
                .login(name)
                .password(password)
                .role(role)
                .build();
    }

    public void fillRepository() {
        Question question1 = buildQuestion(1L, "S — Single Responsibility Principle (Принцип единственной ответственности)");
        question1.getAnswers().add(new Answer(1L, question1.getId(), "Каждый класс должен иметь только одну причину для изменения, " +
                "то есть выполнять только одну задачу или отвечать за одну ответственность. Это повышает сплочённость (cohesion) и упрощает поддержку кода",
                question1.getId() + 1));
        question1.getAnswers().add(new Answer(2L, question1.getId(), "Каждый класс должен иметь только одну причину для изменения, " +
                "то есть выполнять только одну задачу или отвечать за одну ответственность. Это повышает сплочённость (cohesion) и упрощает поддержку кода",
                question1.getId() + 1));
        questionRepository.create(question1);

        Question question2 = buildQuestion(2L, "O — Open/Closed Principle (Принцип открытости/закрытости)");
        question2.getAnswers().add(new Answer(1L, question2.getId(), "Классы должны быть открыты для расширения (добавления нового функционала), " +
                "но закрыты для модификации (изменения существующего кода). Это достигается через абстракции (интерфейсы, абстрактные классы) и полиморфизм",
                question2.getId() + 1));
        question2.getAnswers().add(new Answer(2L, question2.getId(), "Классы должны быть закрыты для расширения (добавления нового функционала), " +
                "но открыты для модификации (изменения существующего кода)",
                question2.getId() + 1));
        questionRepository.create(question2);

        Question question3 = buildQuestion(3L, "Liskov Substitution Principle (Принцип подстановки Барбары Лисков)");
        question3.getAnswers().add(new Answer(1L, question3.getId(), "Объекты базового класса должны быть заменяемы объектами " +
                "производных классов без изменения поведения программы. Это гарантирует, что подклассы не нарушают контракты, заданные базовым классом",
                question3.getId() + 1));
        question3.getAnswers().add(new Answer(2L, question3.getId(), "Объекты базового класса не должны быть заменяемы объектами производных классов " +
                "без изменения поведения программы. Это гарантирует, что подклассы не нарушают контракты, заданные базовым классом",
                question3.getId() + 1));
        questionRepository.create(question3);

        Question question4 = buildQuestion(4L, "Interface Segregation Principle (Принцип разделения интерфейсов)");
        question4.getAnswers().add(new Answer(1L, question4.getId(), "Клиенты не должны быть вынуждены реализовывать интерфейсы, которые они не используют. " +
                "Интерфейсы должны быть узкоспециализированными, чтобы классы реализовывали только необходимый функционал",
                question4.getId() + 1));
        question4.getAnswers().add(new Answer(2L, question4.getId(), "Клиенты должны реализовывать интерфейсы, которые они не используют. " +
                "Интерфейсы должны быть универсальными, чтобы классы реализовывали универсальный функционал",
                question4.getId() + 1));
        questionRepository.create(question4);

        Question question5 = buildQuestion(5L, "D — Dependency Inversion Principle (Принцип инверсии зависимостей)");
        question5.getAnswers().add(new Answer(1L, question5.getId(), "Модули высокого уровня не должны зависеть от модулей низкого уровня; " +
                "оба должны зависеть от абстракций. Абстракции не должны зависеть от деталей реализации, а детали — от абстракций. Это снижает связанность (coupling)",
                question5.getId() + 1));
        question5.getAnswers().add(new Answer(2L, question5.getId(), "Модули высокого уровня должны зависеть от модулей низкого уровня. " +
                "Это повышает связанность и упрощает написание программы",
                question5.getId() + 1));
        questionRepository.create(question5);
    }

    private static Question buildQuestion(Long questionId, String text) {
        return Question.builder()
                .id(questionId)
                .text(text)
                .build();
    }

}

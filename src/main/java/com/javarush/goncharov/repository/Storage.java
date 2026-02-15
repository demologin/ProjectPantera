package com.javarush.goncharov.repository;

import com.javarush.goncharov.model.*;
import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Storage {
    private static Storage instance;
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Message> messages = new ConcurrentHashMap<>();
    private final Map<Long, Quest> quests = new ConcurrentHashMap<>();
    private final Map<Long, Question> questions = new ConcurrentHashMap<>();
    private final Map<Long, Answer> answers = new ConcurrentHashMap<>();
    private final Map<Long, Game> games = new ConcurrentHashMap<>();
//    private final Map<Long, Game> games = new TreeMap<>();

    private Storage() {
        users.put(1L, new User(1L, "Admin", "123", Role.ADMIN, "admin@test.ru"));
        users.put(2L, new User(2L, "Dima", "123", Role.MODERATOR, "dima@test.ru"));
        users.put(3L, new User(3L, "Lex", "123", Role.GAMEDEV, "lexus@test.ru"));
        messages.put(1L, new Message(1L, "Дмитрий Гончаров", "dimagoncharov21@gmail.com",
                Topic.EDUCATION, "1 Клиент-серверная архитектура На заре появления интернета распространение получила клиент-серверная архитектура, хотя существовали и другие. Смысл ее заключается в том, что все участники сети делятся на две логические части: клиент и сервер. Задача сервера (server, от serve — служить) — обслуживать запросы клиентов. Сервер делает большую часть работы, хранит все нужные данные и следит за их целостностью. И хотя есть компьютеры, которые называют серверы, обычно под понятиями “клиент” и “сервер” подразумевается ПО. Задача клиента — жить в свое удовольствие. Когда клиенту нужны какие-нибудь данные от сервера, он отправляет ему запрос. Через некоторое время он получает ответ от сервера и может делать с полученными данными что-то важное. Инициатором запросов всегда выступает клиент. Режим общения всегда проходит в виде запрос-ответ. Это в каком-то смысле синоним понятия “клиент-сервер”. А какие же бывают альтернативы? Ну, во-первых, одноранговые сети, где все участники равны (их еще называют peer-to-peer сетями). Если вы со своим другом переписывайтесь в чате или с помощью СМС, то это как раз пример одноранговой сети. В чем же отличие? Вы можете написать сообщение и не получить на него ответа, а затем отправить новое, и так далее. Ваш друг может быть инициатором диалога. Любая из сторон может написать первой. Вся информация о диалоге храниться у обеих сторон, никто не обязан отвечать.", false));
        messages.put(2L, new Message(2L, "Дмитрий Гончаров", "dimagoncharov21@gmail.com",
                Topic.SPORTS, "Преимущества клиент-серверной архитектуры:\n" +
                "Надежность. Клиенты могут находиться где угодно, даже на ненадежных платформах. Windows на вашем компьютере может “слететь”, iPhone могут украсть, а данные, которые хранятся в облаке, никуда не денутся.\n" +
                "\n" +
                "Слабые и дешевые клиенты. Если вам нужно сделать монтаж видео на телефоне, то вы загружаете его на сервер и выполняете на серверных мощностях. Клиент может быть дешевым инструментом.\n" +
                "\n" +
                "Сбалансированная нагрузка. Каждый клиент имеет индивидуальный график использования, который может быть очень скачкообразным. На сервер приходят запросы от тысяч клиентов, его нагрузка усредняется и поэтому лучше прогнозируема.", false));
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
}
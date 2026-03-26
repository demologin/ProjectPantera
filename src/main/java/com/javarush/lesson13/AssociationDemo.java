package com.javarush.lesson13;

import com.javarush.khmelov.config.Config;
import com.javarush.khmelov.config.LiqubaseInit;
import com.javarush.khmelov.config.SessionCreator;
import com.javarush.khmelov.entity.Quest;
import com.javarush.khmelov.entity.Question;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.repository.AnswerRepository;
import com.javarush.khmelov.repository.QuestRepository;
import com.javarush.khmelov.repository.QuestionRepository;
import com.javarush.khmelov.repository.UserRepository;
import com.javarush.khmelov.service.QuestService;
import com.javarush.khmelov.service.UserService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.Proxy;
import java.util.Collection;

public class AssociationDemo {

    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        Transaction tx = session.beginTransaction();
        try (session; sessionCreator) {
            Quest quest = session.get(Quest.class, 1L);
            System.out.println(quest);
            System.out.println("=".repeat(100));
            System.out.println("Many to One, Author=" + quest.getAuthor());

            System.out.println("=".repeat(100));
            User user = session.get(User.class, 1L);
            System.out.println("One to Many, Quests size=" + user.getQuests().size());
            Collection<Question> questions = quest.getQuestions();
            System.out.println("One to Many, Questions size=" + questions.size());
            Question oneQuestion = questions.stream().findFirst().orElseThrow();
            System.out.println("One to Many, Answer size=" + oneQuestion.getAnswers().size());

            System.out.println("=".repeat(100));
            //System.out.println("One to One, UserInfo=" + user.getUserInfo());

            System.out.println("=".repeat(100));
            System.out.println("Many to Many, User play Quests (In Game)=" + user.getQuestsInGame());
            System.out.println("Many to Many, Quest Players (In Game)=" + quest.getUsers());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    static {
        AssociationDemo.init();
    }

    public static void init() {
        //stupid init (like tomcat without NanoSpring)
        SessionCreator sessionCreator = magicSessionCreator();
        try (sessionCreator) {
            UserRepository userRepository = new UserRepository(sessionCreator);
            QuestRepository questRepository = new QuestRepository(sessionCreator);
            QuestionRepository questionRepository = new QuestionRepository(sessionCreator);
            AnswerRepository answerRepository = new AnswerRepository(sessionCreator);
            UserService userService = new UserService(userRepository);
            QuestService questService = new QuestService(userRepository, questRepository, questionRepository, answerRepository);
            Config config = new Config(userService, questService, new LiqubaseInit());
            config.fillEmptyRepository();
        }
    }

    private static SessionCreator magicSessionCreator() {
        //stupid Transactional Mode
        return new SessionCreator() {
            private Session magicSession;
            private Session session;

            @Override
            public void close() {
                session.close();
                super.close();
            }

            @Override
            public synchronized Session getSession() {
                if (magicSession == null) {
                    session = super.getSession();
                    magicSession = (Session) Proxy.newProxyInstance(
                            Session.class.getClassLoader(),
                            new Class[]{Session.class},
                            (proxy, method, args) -> {
                                if (!method.getName().equals("close")) {
                                    return method.invoke(session, args);
                                } else {
                                    System.out.println("Skip Method: " + method.getName());
                                    return null;
                                }
                            });
                }
                return magicSession;
            }
        };
    }
}

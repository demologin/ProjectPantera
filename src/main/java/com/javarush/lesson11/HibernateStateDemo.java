package com.javarush.lesson11;

import com.javarush.khmelov.entity.Role;
import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.config.SessionCreator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.SessionStatistics;


public class HibernateStateDemo {
    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (sessionCreator;
             session) {

            show(session);
            User user = session.find(User.class, 1);
            System.out.println(user);
            show(session);
            user.setLogin("Carl1");
            System.out.println(user);
            show(session);
            session.flush();
            show(session);
            session.flush();
            show(session);

            User newUser = new User();
            newUser.setLogin("Carl2");
            newUser.setPassword("password2");
            newUser.setRole(Role.GUEST);
            show(session);

            session.persist(newUser);
            show(session);
            session.flush();
            show(session);
            session.remove(newUser);
            show(session);
            session.flush();
            show(session);

        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    private static void show(Session session) {
        String name = session.toString();
        SessionStatistics statistics = session.getStatistics();
        boolean sessionDirty = session.isDirty();
        String line = "=".repeat(50);
        System.out.printf("""
                %s
                name=%s
                stat=%s
                dirty=%s
                %s
                """, line, name, statistics, sessionDirty, line);
    }
}

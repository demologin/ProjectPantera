package com.javarush.lesson09;

import com.javarush.khmelov.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.Properties;

public class SessionCreator implements AutoCloseable {

    private final SessionFactory sessionFactory;

    public SessionCreator() {
        Configuration configuration = new Configuration();
        configuration.configure();
        //configuration.configure("hibernate.cfg.xml");
        //Properties properties = configuration.getProperties();
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        sessionFactory.close();
    }

    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        try (sessionCreator;
             Session session = sessionCreator.getSession()
        ) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.find(User.class, 1L);
                System.out.println(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }

}

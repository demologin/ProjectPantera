package com.javarush.vasileva;

import com.javarush.vasileva.entity.Role;
import com.javarush.vasileva.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class SessionCreator implements AutoCloseable {

    private final SessionFactory sessionFactory;

    public SessionCreator() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }


    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

    public static void main(String[] args) throws Exception {
        SessionCreator sessionCreator = new SessionCreator();
        try (sessionCreator;
             Session session = sessionCreator.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = session.find(User.class, 1);
                System.out.println(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
            }
        }
    }
}

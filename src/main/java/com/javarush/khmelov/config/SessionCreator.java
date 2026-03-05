package com.javarush.khmelov.config;

import com.javarush.khmelov.entity.User;
import com.javarush.lesson12.hibernate.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class SessionCreator implements AutoCloseable {

    private final SessionFactory sessionFactory;

    public SessionCreator() {
        Configuration configuration = new Configuration();
        configuration.configure();
        //configuration.configure("hibernate.cfg.xml");
        //Properties properties = configuration.getProperties();
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(User.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
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

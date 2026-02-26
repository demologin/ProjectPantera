package com.javarush.lesson10;

import com.javarush.khmelov.entity.User;
import com.javarush.lesson09.SessionCreator;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class HqlUserDemo {

    private final SessionCreator sessionCreator;


    public List<User> getAllUsers() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<User> userQuery = session.createNamedQuery(User.GET_ALL, User.class);
            List<User> result = userQuery.list();
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(long startId, long endId) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<User> userQuery = session.createNamedQuery(User.BETWEEN_START_AND_END, User.class);
            userQuery.setParameter("startId", startId);
            userQuery.setParameter("endId", endId);
            List<User> result = userQuery.list();
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        SessionCreator sessionCreator = new SessionCreator();
        HqlUserDemo hqlUserDemo = new HqlUserDemo(sessionCreator);
        List<User> allUsers = hqlUserDemo.getAllUsers();
        allUsers.forEach(System.out::println);

        List<User> users = hqlUserDemo.getAllUsers(2, 3);
        users.forEach(System.out::println);
    }
}

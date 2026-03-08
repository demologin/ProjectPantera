package com.javarush.vasileva.repository;

import com.javarush.vasileva.SessionCreator;
import com.javarush.vasileva.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserRepo implements Repository<User> {

    private final SessionCreator sessionCreator;

    public UserRepo(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            User user = session.find(User.class, id);
            System.out.println(user);
            transaction.commit();
            return Optional.of(user);
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(user);
            System.out.println("user created: " + user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(user);
            System.out.println("user updated: " + user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(user);
            System.out.println("user deleted: " + user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}

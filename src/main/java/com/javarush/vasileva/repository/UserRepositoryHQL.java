package com.javarush.vasileva.repository;

import com.javarush.vasileva.SessionCreator;
import com.javarush.vasileva.entity.User;
import jakarta.persistence.NamedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepositoryHQL implements Repository<User> {

    private final SessionCreator sessionCreator;

    public UserRepositoryHQL(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    @Override
    public List<User> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<User> query = session.createNamedQuery(User.USER_GET_ALL, User.class);
            List<User> users = query.list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
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

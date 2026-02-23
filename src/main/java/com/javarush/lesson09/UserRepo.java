package com.javarush.lesson09;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.repository.Repository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class UserRepo implements Repository<User> {

    private final SessionCreator sessionCreator;

    public UserRepo(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

    //TODO HQL
    @Override
    public Collection<User> getAll() {
        return List.of();
    }

    //TODO Criteria API
    @Override
    public Stream<User> find(User pattern) {
        return Stream.empty();
    }

    @Override
    public User get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            User user = session.find(User.class, id);
            transaction.commit();
            return user;
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
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}

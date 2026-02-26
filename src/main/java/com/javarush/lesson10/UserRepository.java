package com.javarush.lesson10;

import com.javarush.khmelov.entity.User;
import com.javarush.khmelov.repository.Repository;
import com.javarush.lesson09.SessionCreator;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class UserRepository implements Repository<User> {

    private final SessionCreator sessionCreator;

    public UserRepository(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }


    @Override
    public Collection<User> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<User> userQuery = session.createNamedQuery(User.GET_ALL, User.class);
            List<User> users = userQuery.list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }


    /* session->cb->cq->root
     * c <- filter fields and add cb.equals(root.get(name), value)
     * cq.select(root).where(predicates);
     * result <- session.createQuery(cq).list(); */
    @Override
    public Stream<User> find(User pattern) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(User.class);
            var root = criteriaQuery.from(User.class);
            criteriaQuery.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Field[] fields = pattern.getClass().getDeclaredFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && field.trySetAccessible()) {
                    Object value = field.get(pattern);
                    String fieldName = field.getName();
                    if (value != null && !field.isAnnotationPresent(Transient.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(fieldName), value));
                    }
                }
            }
            Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
            criteriaQuery.where(predicatesArray);
            Query<User> userQuery = session.createQuery(criteriaQuery);
            List<User> users = userQuery.list();
            transaction.commit();
            return users.stream();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
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

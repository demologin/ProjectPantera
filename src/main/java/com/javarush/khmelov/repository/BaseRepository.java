package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.AbstractEntity;
import com.javarush.khmelov.config.SessionCreator;
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

public class BaseRepository<T extends AbstractEntity> implements Repository<T> {

    private final SessionCreator sessionCreator;
    private final Class<T> entityClass;

    public BaseRepository(SessionCreator sessionCreator, Class<T> entityClass) {
        this.sessionCreator = sessionCreator;
        this.entityClass = entityClass;
    }


    @Override
    public Collection<T> getAll() {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            Query<T> entityQuery = session.createQuery("select u from %s u".formatted(entityClass.getSimpleName()), entityClass);
            List<T> entitys = entityQuery.list();
            transaction.commit();
            return entitys;
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
    public Stream<T> find(T pattern) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(entityClass);
            var root = criteriaQuery.from(entityClass);
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
            Query<T> entityQuery = session.createQuery(criteriaQuery);
            List<T> entitys = entityQuery.list();
            transaction.commit();
            return entitys.stream();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public T get(long id) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            T entity = session.find(entityClass, id);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(T entity) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T entity) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T entity) {
        Session session = sessionCreator.getSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}

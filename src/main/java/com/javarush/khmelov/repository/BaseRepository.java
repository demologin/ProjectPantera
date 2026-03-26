package com.javarush.khmelov.repository;

import com.javarush.khmelov.entity.AbstractEntity;
import com.javarush.khmelov.exception.AppException;
import com.javarush.khmelov.config.SessionCreator;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
public class BaseRepository<Entity extends AbstractEntity> implements Repository<Entity> {

    private final SessionCreator sessionCreator;

    private final Class<Entity> entityClass;

    @Override
    public Collection<Entity> getAll() {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                List<Entity> list = session.createQuery("SELECT e FROM %s e".formatted(entityClass.getName()), entityClass).list();
                tx.commit();
                return list;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException(e);
            }
        }
    }


    @Override
    /* session->cb->cq->root
     * c <- filter fields and add cb.equals(root.get(name), value)
     * cq.select(root).where(predicates);
     * result <- session.createQuery(cq).list(); */
    public Stream<Entity> find(Entity pattern) {
        Session session = sessionCreator.getSession();
        try (session) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            JpaCriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root<Entity> root = criteriaQuery.from(entityClass);
            Field[] fields = pattern.getClass().getDeclaredFields();
            List<Predicate> predicates = new ArrayList<>();
            for (Field field : fields) {
                if (field.trySetAccessible()) {
                    String name = field.getName();
                    Object value = field.get(pattern);
                    if (isPredacate(field, value)) {
                        Predicate predicate = criteriaBuilder.equal(root.get(name), value);
                        predicates.add(predicate);
                    }
                }
            }
            criteriaQuery.select(root);
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
            Query<Entity> query = session.createQuery(criteriaQuery);
            List<Entity> list = query.list();
            return list.stream();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isPredacate(Field field, Object value) {
        return Objects.nonNull(value)
               && !field.isAnnotationPresent(Transient.class)
               && !field.isAnnotationPresent(OneToMany.class)
               && !field.isAnnotationPresent(ManyToOne.class)
               && !field.isAnnotationPresent(OneToOne.class)
               && !field.isAnnotationPresent(ManyToMany.class);
    }

    @Override
    public Entity get(long id) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                Entity entity = session.find(entityClass, id);
                tx.commit();
                return entity;
            } catch (Exception e) {
                tx.rollback();
                throw new AppException("not found Entity with id " + id, e);
            }
        }
    }

    @Override
    public void create(Entity entity) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException("error while creating entity", e);
            }
        }
    }

    @Override
    public void update(Entity entity) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException("error while creating entity", e);
            }
        }
    }

    @Override
    public void delete(Entity entity) {
        Session session = sessionCreator.getSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(entity);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new AppException("error while creating entity", e);
            }
        }
    }
}

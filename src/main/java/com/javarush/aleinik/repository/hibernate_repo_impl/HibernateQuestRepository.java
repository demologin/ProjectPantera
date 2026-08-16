package com.javarush.aleinik.repository.hibernate_repo_impl;

import com.javarush.aleinik.model.Quest;
import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateQuestRepository implements QuestRepository {

    private final SessionFactory sessionFactory;

    public HibernateQuestRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Quest> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "from Quest",
                    Quest.class
            ).getResultList();
        }
    }

    @Override
    public Quest findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Quest.class, id);
        }
    }

    @Override
    public Quest save(Quest quest) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(quest);
            transaction.commit();
            return quest;
        } catch (RuntimeException exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }

    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Quest quest = session.get(Quest.class, id);

            if (quest != null) {
                session.remove(quest);
            }

            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw exception;
        }
    }

}

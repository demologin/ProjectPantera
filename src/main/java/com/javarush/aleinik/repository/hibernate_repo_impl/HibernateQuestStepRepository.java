package com.javarush.aleinik.repository.hibernate_repo_impl;

import com.javarush.aleinik.model.QuestStep;
import com.javarush.aleinik.repository.QuestStepRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateQuestStepRepository implements QuestStepRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_STEP_BY_QUEST_AND_STEP_ID = """
            select distinct qs
            from QuestStep qs
            left join fetch qs.choices
            where qs.quest.id = :questId
            and qs.stepId = :stepId
            """;

    private static final String FIND_ALL_BY_QUEST_ID_WITH_CHOICES = """
            SELECT DISTINCT qs
            FROM QuestStep qs
            LEFT JOIN FETCH qs.choices
            WHERE qs.quest.id = :questId
            """;

    public HibernateQuestStepRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public QuestStep findStepByQuestId(Long questId, Long stepId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            FIND_STEP_BY_QUEST_AND_STEP_ID,
                            QuestStep.class
                    )
                    .setParameter("questId", questId)
                    .setParameter("stepId", stepId)
                    .uniqueResult();
        }
    }

    @Override
    public List<QuestStep> findAll() {
        return List.of();
    }

    @Override
    public QuestStep findById(Long aLong) {
        return null;
    }

    @Override
    public QuestStep save(QuestStep questStep) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(questStep);
            transaction.commit();
            return questStep;
        } catch (RuntimeException exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw exception;
        }
    }

    @Override
    public void deleteById(Long aLong) {

    }

    public List<QuestStep> findAllByQuestIdWithChoices(Long questId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            FIND_ALL_BY_QUEST_ID_WITH_CHOICES,
                            QuestStep.class
                    )
                    .setParameter("questId", questId)
                    .getResultList();

        }
    }
}

package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContestRepositoryImpl implements ContestRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContestRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createContest(Contest contest) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(contest);
            session.getTransaction().commit();
        }
    }

    @Override
    public Contest getContestById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Contest contest = session.get(Contest.class, id);
            if (contest == null) {
                throw new EntityNotFoundException("Contest", id);
            }
            return contest;
        }
    }

    @Override
    public List<Contest> getAllContests() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("FROM Contest ", Contest.class);
            return query.getResultList();
        }
    }

    @Override
    public void updateContest(Contest contest) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            for (Photo photo : contest.getPhotoSet()) {
                session.update(photo);
            }

            session.update(contest);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Contest> getFinishedContests() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query =
                    session.createQuery("from Contest where timeLimitPhase2 < :date", Contest.class);
            query.setParameter("date", LocalDateTime.now());
            return query.getResultList();

        }
    }



    @Override
    public List<Contest> getOpenContest() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("FROM Contest where isOpen = true", Contest.class);
            return query.getResultList();
        }
    }

    @Override
    public List<Contest> getClosedContests() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("FROM Contest where isOpen = false", Contest.class);
            return query.getResultList();
        }
    }

    @Override
    public List<Contest> getContestsInFirstPhase() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query =
                    session.createQuery("from Contest where timeLimitPhase1 > :date", Contest.class);
            query.setParameter("date", LocalDateTime.now());
            return query.getResultList();

        }
    }

    @Override
    public List<Contest> getContestsInSecondPhase() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query =
                    session.createQuery("from Contest where timeLimitPhase1 < :date and timeLimitPhase2 > :date"
                            , Contest.class);
            query.setParameter("date", LocalDateTime.now());
            return query.getResultList();

        }
    }

    @Override
    public List<Contest> getUserContests(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("select c from Contest c, " +
                    "in(c.participantsSet) partSet where partSet.id=:id and c.timeLimitPhase2 > :date", Contest.class);
            query.setParameter("id", userId);
            query.setParameter("date", LocalDateTime.now());
            return query.getResultList();
        }
    }


    @Override
    public List<Contest> getCurrentUserFinishedContests(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query =
                    session.createQuery("select c from Contest c , " +
                            "in(c.participantsSet) partSet where partSet.id=:id and c.finished=true", Contest.class);
            query.setParameter("id", userId);
            return query.getResultList();
        }
    }

    @Override
    public List<Contest> getJudgingContests(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query =
                    session.createQuery("select c from Contest c, in (c.jurySet) juryset where juryset.id=:id",
                            Contest.class);
            query.setParameter("id", userId);
            return query.getResultList();
        }
    }

    @Override
    public List<Contest> getAllAvailableContests(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("from Contest c " +
                    "where c.id not in (SELECT distinct cc.id FROM Contest cc ,in(cc.jurySet) jurySet where jurySet.id=:id) " +
                    "and c.id not in(SELECT distinct ccc.id FROM Contest ccc ,in(ccc.participantsSet) partSet where partSet.id=:id) " +
                    "and c.finished=false " +
                    "and c.isOpen=true", Contest.class);
            query.setParameter("id", userId);

            return query.getResultList();
        }
    }

    @Override
    public boolean canUserSubmitPhoto(int userId, int contestId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("from Contest c " +
                    "where c.id in (SELECT distinct ccc.id FROM Contest ccc ,in(ccc.participantsSet) partSet where partSet.id=:id) " +
                    "and c.timeLimitPhase2 > :date " +
                    "and id not in (select contest.id from Photo where user.id =:id) " +
                    "and c.id =:contestId ", Contest.class);
            query.setParameter("id", userId);
            query.setParameter("contestId", contestId);
            query.setParameter("date", LocalDateTime.now());
            List<Contest> result = query.getResultList();
            return !result.isEmpty();
        }
    }

    @Override
    public boolean hasUserSubmitPhotoToContest(int userId, int contestId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("from Contest c " +
                    "where c.id in (SELECT distinct ccc.id FROM Contest ccc ,in(ccc.participantsSet) partSet where partSet.id=:id) " +
                    "and c.timeLimitPhase2 > :date " +
                    "and id in (select contest.id from Photo where user.id =:id) " +
                    "and c.id =:contestId ", Contest.class);
            query.setParameter("id", userId);
            query.setParameter("contestId", contestId);
            query.setParameter("date", LocalDateTime.now());
            List<Contest> result = query.getResultList();
            return !result.isEmpty();
        }
    }

    @Override
    public List<Contest> getFinishingContests() {
        try (Session session = sessionFactory.openSession()) {
            Query<Contest> query = session.createQuery("from Contest " +
                    "where timeLimitPhase2 < :date " +
                    "and finished = false ", Contest.class);
            query.setParameter("date", LocalDateTime.now());
            return query.getResultList();
        }
    }
}
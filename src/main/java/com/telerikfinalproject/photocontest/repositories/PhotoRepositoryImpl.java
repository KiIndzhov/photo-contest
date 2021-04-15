package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.repositories.contracts.PhotoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PhotoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Photo> getAllPhotos() {
        try (Session session = sessionFactory.openSession()) {
            Query<Photo> query = session.createQuery("FROM Photo", Photo.class);
            return query.getResultList();
        }
    }

    @Override
    public Photo getPhotoById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Photo photo = session.get(Photo.class, id);
            if (photo == null) {
                throw new EntityNotFoundException("Photo", id);
            }
            return photo;
        }
    }

    @Override
    public void createPhoto(Photo photo) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(photo);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updatePhoto(Photo photo) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(photo);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Photo> getAllWinnerPhotos() {
        try (Session session = sessionFactory.openSession()) {
            Query<Photo> query = session.createQuery("from Photo p " +
                            "where p.id in (select distinct c.id from Contest c where timeLimitPhase2 < :date) "
//                    "and p.id in (select distinct pp.id, sum(reviewSet.score) as totalScore from Photo pp, in (pp.reviewSet) reviewSet order by reviewSet.score desc)"
                    , Photo.class);
            query.setParameter("date", LocalDateTime.now());
            return query.getResultList();
        }
    }

    @Override
    public List<Photo> getAllPhotosByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Photo> query = session.createQuery("FROM Photo p where user.id = :id ", Photo.class);
            query.setParameter("id", userId);
            return query.getResultList();
        }
    }


    public List<Photo> getAllPhotosInAContest(int contestId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Photo> query = session.createQuery("FROM Photo where contest.id =:contestId", Photo.class);
            query.setParameter("contestId", contestId);
            List<Photo> result = query.getResultList();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("No photos in this contest");
            }
            return result;
        }
    }
}

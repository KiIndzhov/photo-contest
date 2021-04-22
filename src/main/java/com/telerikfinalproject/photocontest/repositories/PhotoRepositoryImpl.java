package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.repositories.contracts.PhotoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<Photo> getAllPhotosByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Photo> query = session.createQuery("FROM Photo p where user.id = :id ", Photo.class);
            query.setParameter("id", userId);
            return query.getResultList();
        }
    }
}

package com.telerikfinalproject.photocontest.repositories;

import com.telerikfinalproject.photocontest.exceptions.EntityNotFoundException;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.repositories.contracts.ReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ReviewRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Review> getAllReviews() {
        try (Session session = sessionFactory.openSession()) {
            Query<Review> query = session.createQuery("FROM Review", Review.class);
            return query.getResultList();
        }
    }

    @Override
    public Review getReviewById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Review review = session.get(Review.class, id);
            if (review == null) {
                throw new EntityNotFoundException("Review", id);
            }
            return review;
        }
    }

    @Override
    public Review updateReview(Review review) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(review);
            session.getTransaction().commit();
            return review;
        }
    }

    @Override
    public Review getReviewByJuryIdAndPhotoId(int juryId, int photoId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Review> query = session.createQuery("FROM Review " +
                    "where user.id=:juryId and photo.id =:photoId", Review.class);
            query.setParameter("juryId", juryId);
            query.setParameter("photoId", photoId);
            List<Review> result = query.getResultList();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("Review not found.");
            }
            return result.get(0);
        }
    }

    @Override
    public List<Review> getPhotoReviews(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Review> query = session.createQuery("FROM Review where photo.id=:id", Review.class);
            query.setParameter("id", id);
            List<Review> reviews = query.getResultList();
            if (reviews.isEmpty()) {
                throw new EntityNotFoundException("No Reviews for given picture");
            }
            return reviews;
        }
    }
}

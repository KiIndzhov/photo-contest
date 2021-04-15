package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DateTimeException;
import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.repositories.contracts.ReviewRepository;
import com.telerikfinalproject.photocontest.services.contracts.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.getAllReviews();
    }

    @Override
    public Review getReviewById(int id) {
        return reviewRepository.getReviewById(id);
    }

    @Override
    public Review updateReview(Review review, User user) {
        if (!review.getUser().equals(user)) {
            throw new UnauthorisedException("You cannot give reviews.");
        }

        LocalDateTime startPhase2 = review.getPhoto().getContest().getTimeLimitPhase1();
        LocalDateTime endPhase2 = review.getPhoto().getContest().getTimeLimitPhase2();

        if (LocalDateTime.now().isBefore(startPhase2) || LocalDateTime.now().isAfter(endPhase2)) {
            throw new DateTimeException();
        }

        review.setEdited(true);
        return reviewRepository.updateReview(review);
    }

    @Override
    public List<Review> getPhotoReviews(int photoId) {
        return reviewRepository.getPhotoReviews(photoId);
    }

    @Override
    public Review getReviewByJuryIdAndPhotoId(int juryId, int photoId) {
        return reviewRepository.getReviewByJuryIdAndPhotoId(juryId, photoId);
    }

    @Override
    public void updateReview(Review review) {
        reviewRepository.updateReview(review);
    }
}

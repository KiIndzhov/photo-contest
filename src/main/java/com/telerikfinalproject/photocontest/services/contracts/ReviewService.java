package com.telerikfinalproject.photocontest.services.contracts;

import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();

    Review getReviewById(int id);

    Review updateReview(Review review, User user);

    List<Review> getPhotoReviews(int photoId);

    Review getReviewByJuryIdAndPhotoId(int juryId, int photoId);

    void updateReview(Review review);
}

package com.telerikfinalproject.photocontest.repositories.contracts;

import com.telerikfinalproject.photocontest.models.Review;

import java.util.List;

public interface ReviewRepository {
    List<Review> getAllReviews();

    Review getReviewById(int id);

    Review updateReview(Review review);

    Review getReviewByJuryIdAndPhotoId(int juryId, int photoId);

    List<Review> getPhotoReviews(int photoId);
}

package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DateTimeException;
import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.repositories.contracts.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.telerikfinalproject.photocontest.services.Helpers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTests {

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewServiceImpl reviewService;

    @Test
    public void getAllReviews_Should_Call_Repository_WhenValid() {
        reviewService.getAllReviews();

        verify(reviewRepository, times(1)).getAllReviews();
    }

    @Test
    public void getReviewById_Should_Call_Repository_WhenValid() {
        reviewService.getReviewById(Mockito.anyInt());

        verify(reviewRepository, times(1)).getReviewById(Mockito.anyInt());
    }

    @Test
    public void updateReview_Should_Throw_WhenUnauthorized() {
        Review review = createReview();
        User newUser = createUser();
        newUser.setId(2);
        newUser.getCredential().setUsername("newUsername");

        reviewService.updateReview(review);

        Assertions.assertThrows(UnauthorisedException.class,
                () -> reviewService.updateReview(review, newUser));
    }

    @Test
    public void updateReview_Should_Throw_WhenWrongPhase() {
        Review review = createReview();
        Contest contest = createContest();
        User user = createUser();
        review.getPhoto().setContest(contest);

        review.getPhoto().getContest().setTimeLimitPhase1(LocalDateTime.of(2020, 5, 10, 5,30));
        review.getPhoto().getContest().setTimeLimitPhase2(LocalDateTime.of(2020, 5, 10, 8,30));
        reviewService.updateReview(review);

        Assertions.assertThrows(DateTimeException.class,
                () -> reviewService.updateReview(review, user));
    }

    @Test
    public void updateReview_Should_Call_Repository_When_Valid() {
        Review review = createReview();
        User user = createUser();

        reviewService.updateReview(review, user);

        verify(reviewRepository, times(1)).updateReview(review);
    }

    @Test
    public void getPhotoReviews_Should_Call_Repository_WhenValid() {
        reviewService.getPhotoReviews(Mockito.anyInt());

        verify(reviewRepository, times(1)).getPhotoReviews(Mockito.anyInt());
    }

    @Test
    public void getReviewByJuryIdAndPhotoId_Should_Call_Repository_WhenValid() {
        reviewService.getReviewByJuryIdAndPhotoId(Mockito.anyInt(), Mockito.anyInt());

        verify(reviewRepository, times(1)).getReviewByJuryIdAndPhotoId(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void updateReview_Should_Call_Repository_WhenValid() {
        Review review = createReview();
        reviewService.updateReview(review);

        verify(reviewRepository, times(1)).updateReview(review);
    }
}

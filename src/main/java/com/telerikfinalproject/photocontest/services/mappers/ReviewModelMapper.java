package com.telerikfinalproject.photocontest.services.mappers;

import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewDto;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewOutputDto;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewUpdateDto;
import com.telerikfinalproject.photocontest.services.contracts.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewModelMapper {

    private static final String NOT_SUITABLE = "Not Suitable for this contest";
    private final ReviewService reviewService;

    @Autowired
    public ReviewModelMapper(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public Review reviewDtoToReview(ReviewUpdateDto reviewDto, int id) {
        Review review = reviewService.getReviewById(id);
        review.setScore(reviewDto.getScore());
        review.setComment(reviewDto.getComment());
        return review;
    }

    // TODO: 10/04/2021 da iztriem li gorniq metod i da go podmenim s rest controllera s dolniq?

    public Review reviewDtoToReview(ReviewDto reviewDto){
        Review review = reviewService.getReviewByJuryIdAndPhotoId(reviewDto.getJuryId(), reviewDto.getPhotoId());
        if(reviewDto.isSuitable()){
            review.setScore(0);
            review.setComment(NOT_SUITABLE);
        } else {
            review.setScore(reviewDto.getScore());
            review.setComment(reviewDto.getComment());
        }
        review.setEdited(true);
        return review;

    }

    public ReviewOutputDto reviewToOutputDto(Review review){
        ReviewOutputDto outputDto = new ReviewOutputDto();
        outputDto.setJuryName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        outputDto.setComment(review.getComment());
        outputDto.setScore(review.getScore());
        return outputDto;
    }
}

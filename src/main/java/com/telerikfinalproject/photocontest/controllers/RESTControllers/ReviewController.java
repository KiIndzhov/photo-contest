package com.telerikfinalproject.photocontest.controllers.RESTControllers;

import com.telerikfinalproject.photocontest.models.Credential;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewUpdateDto;
import com.telerikfinalproject.photocontest.services.contracts.ReviewService;
import com.telerikfinalproject.photocontest.services.mappers.ReviewModelMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.telerikfinalproject.photocontest.controllers.utils.Constants.*;

@Api(tags = {"reviews"})
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewModelMapper mapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ReviewController(ReviewService reviewService, ReviewModelMapper mapper, AuthenticationHelper authenticationHelper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ApiOperation(value = ALL_REVIEWS, response = List.class)
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @ApiOperation(value = REVIEW_BY_ID, response = Review.class)
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    @ApiOperation(value = GIVE_REVIEW, response = Review.class)
    @PostMapping("/{id}")
    public Review updateReview(@PathVariable int id,
                               @Valid @RequestBody ReviewUpdateDto reviewDto,
                               @RequestHeader HttpHeaders headers) {
        Credential credential = authenticationHelper.getUserCredentials(headers);
        User user = authenticationHelper.getUserByCredentials(credential);
        Review review = mapper.reviewDtoToReview(reviewDto, id);
        reviewService.updateReview(review, user);
        return review;
    }
}

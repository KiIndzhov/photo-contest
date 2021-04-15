package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.telerikfinalproject.photocontest.services.Helpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledServiceTests {

    private Contest contest;

    @Mock
    ContestService contestService;
    @Mock
    UserService userService;

    @InjectMocks
    ScheduledService scheduledService;

    @BeforeEach
    private void makeContest() {
        contest = createContest();
        User user = createUser(1);
        Photo photo = createPhoto(1);
        Review review = createReview();
        photo.getReviewSet().add(review);
        photo.setUser(user);
        contest.getPhotoSet().add(photo);
    }


    @Test
    public void updateRankingPoints_Should_Call_Repository_When_Valid() {
        List<Contest> contestList = new ArrayList<>(Arrays.asList(createContest(), createContest()));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();
        verify(contestService,times(2)).updateContest(createContest());

    }

    @Test
    public void updateRankingPoints_Should_Change_Finished_Field_ToTrue() {
        Contest contest = createContest();
        contest.setFinished(false);
        List<Contest> contestList = new ArrayList<>(Arrays.asList(createContest(),contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertTrue(contest.isFinished());
    }

    @Test
    public void getAllWinners_Should_Add_To_firstPlacePhotos_When_Empty(){
        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(0).getUser().getScore(),51);


    }

    @Test
    public void getAllWinners_Should_Add_3Points_When_Contest_Invitational(){
        contest.setOpen(false);
        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(0).getUser().getScore(),53);


    }

    @Test
    public void getAllWinners_Should_Add_To_FirstPlace_When_TopPhotos_Have_Same_Points(){
        User user2 = createUser();
        Photo photo2 = createPhoto(2);
        Review review2 = createReview();
        photo2.getReviewSet().add(review2);
        photo2.setUser(user2);
        contest.getPhotoSet().add(photo2);

        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(0).getUser().getScore(),contest.getPhotoSet().get(1).getUser().getScore());


    }

    @Test
    public void getAllWinners_Should_Add_To_SecondPlace_When_SecondPicture_Has_LessPOints(){
        User user2 = createUser();
        Photo photo2 = createPhoto(2);
        Review review2 = createReview();
        photo2.getReviewSet().add(review2);
        photo2.setUser(user2);
        contest.getPhotoSet().add(photo2);
        contest.getPhotoSet().get(0).getReviewSet().get(0).setScore(10);

        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(1).getUser().getScore(),36);


    }

    @Test
    public void getAllWinners_Should_Add_To_SecondPlace_When_Pictures_Have_Same_Points(){
        User user2 = createUser();
        Photo photo2 = createPhoto(2);
        Review review2 = createReview();
        photo2.getReviewSet().add(review2);
        photo2.setUser(user2);
        contest.getPhotoSet().add(photo2);

        User user3 = createUser();
        Photo photo3 = createPhoto(3);
        Review review3 = createReview();
        photo3.getReviewSet().add(review3);
        photo3.setUser(user3);
        contest.getPhotoSet().add(photo3);

        contest.getPhotoSet().get(0).getReviewSet().get(0).setScore(10);

        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(1).getUser().getScore(),contest.getPhotoSet().get(2).getUser().getScore());


    }

    @Test
    public void getAllWinners_Should_Add_To_ThirdPlace_When_Less_Points_Than_FirstAndSecond(){
        User user2 = createUser();
        Photo photo2 = createPhoto(2);
        Review review2 = createReview();
        photo2.getReviewSet().add(review2);
        photo2.setUser(user2);
        contest.getPhotoSet().add(photo2);

        User user3 = createUser();
        Photo photo3 = createPhoto(3);
        Review review3 = createReview();
        photo3.getReviewSet().add(review3);
        photo3.setUser(user3);
        contest.getPhotoSet().add(photo3);

        contest.getPhotoSet().get(0).getReviewSet().get(0).setScore(10);
        contest.getPhotoSet().get(1).getReviewSet().get(0).setScore(5);


        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(2).getUser().getScore(),21);


    }

    @Test
    public void getAllWinners_Should_Add_To_ThirdPlace_When_Same_Points(){
        User user2 = createUser();
        Photo photo2 = createPhoto(2);
        Review review2 = createReview();
        photo2.getReviewSet().add(review2);
        photo2.setUser(user2);
        contest.getPhotoSet().add(photo2);

        User user3 = createUser();
        Photo photo3 = createPhoto(3);
        Review review3 = createReview();
        photo3.getReviewSet().add(review3);
        photo3.setUser(user3);
        contest.getPhotoSet().add(photo3);

        User user4 = createUser();
        Photo photo4 = createPhoto(4);
        Review review4 = createReview();
        photo4.getReviewSet().add(review4);
        photo4.setUser(user4);
        contest.getPhotoSet().add(photo4);

        contest.getPhotoSet().get(0).getReviewSet().get(0).setScore(10);
        contest.getPhotoSet().get(1).getReviewSet().get(0).setScore(5);


        List<Contest> contestList = new ArrayList<>(Collections.singletonList(contest));
        when(contestService.getFinishingContests()).thenReturn(contestList);

        scheduledService.updateRankingPoints();

        assertEquals(contest.getPhotoSet().get(2).getUser().getScore(),contest.getPhotoSet().get(3).getUser().getScore());


    }
}

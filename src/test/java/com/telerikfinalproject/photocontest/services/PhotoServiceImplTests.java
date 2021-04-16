package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestRepository;
import com.telerikfinalproject.photocontest.repositories.contracts.PhotoRepository;
import com.telerikfinalproject.photocontest.services.contracts.ReviewService;
import com.telerikfinalproject.photocontest.services.mappers.ReviewModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.telerikfinalproject.photocontest.services.Helpers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhotoServiceImplTests {

    @Mock
    PhotoRepository photoRepository;
    @Mock
    ContestRepository contestRepository;
    @Mock
    ReviewService reviewService;

    @InjectMocks
    PhotoServiceImpl photoService;

    @Test
    public void getAllPhotos_Should_Call_Repository_WhenValid() {
        photoService.getAllPhotos();

        verify(photoRepository, times(1)).getAllPhotos();

    }

    @Test
    public void getPhotoById_Should_Call_Repository_WhenValid() {
        photoService.getPhotoById(anyInt());

        verify(photoRepository, times(1)).getPhotoById(anyInt());

    }

    @Test
    public void createPhoto_Should_Call_Repository_When_Valid() {
        Set<User> judgeSet = new HashSet<>();
        Contest contest = createContest();
        contest.setJurySet(judgeSet);
        User user = createUser();
        Photo photo = createPhoto();
        photo.setUser(user);
        photo.setContest(contest);
        contest.getParticipantsSet().add(user);
        contest.getPhotoSet().add(photo);

        when(contestRepository.getContestById(anyInt())).thenReturn(contest);

        photoService.createPhoto(photo);

        verify(photoRepository, times(1)).createPhoto(photo);
    }

    @Test
    public void createPhoto_Should_ThrowException_When_User_Not_In_Contest() {
        Photo photo = createPhoto();

        assertThrows(UnauthorisedException.class, ()->photoService.createPhoto(photo));
    }

    @Test
    public void createPhoto_Should_Add_Reviews_To_Photo_When_Valid(){
        Contest contest = createContest();
        User user = createUser();
        User judge = createUser(2);
        contest.getJurySet().add(judge);
        Photo photo = createPhoto();
        photo.setUser(user);
        photo.setContest(contest);
        contest.getParticipantsSet().add(user);
        contest.getPhotoSet().add(photo);

        when(contestRepository.getContestById(anyInt())).thenReturn(contest);

        photoService.createPhoto(photo);

        assertEquals(2,photo.getReviewSet().get(0).getUser().getId());
    }

    @Test
    public void getAllWinnerPhotos_Should_Call_Repository_When_Valid(){
        photoService.getAllWinnerPhotos();

        verify(contestRepository,times(1)).getFinishedContests();
    }

    @Test
    public void getRandomWinningPhotos_Should_Return_List_Size_Input_Amount_When_Valid(){
        Contest contest = createContest();
        contest.getPhotoSet().add(createPhoto(1));
        Contest contest2 = createContest();
        contest2.getPhotoSet().add(createPhoto(2));
        Contest contest3 = createContest();
        contest3.getPhotoSet().add(createPhoto(3));
        List<Contest> contests = new ArrayList<>(Arrays.asList(contest,contest2,contest3));

        when(contestRepository.getFinishedContests()).thenReturn(contests);


        assertEquals(2,photoService.getRandomWinningPhotos(2).size());
    }

    @Test
    public void getRandomWinningPhotos_Should_Return_Smaller_List_When_NotEnoughtPhotos(){
        Contest contest = createContest();
        contest.getPhotoSet().add(createPhoto(1));
        Contest contest2 = createContest();
        contest2.getPhotoSet().add(createPhoto(2));
        Contest contest3 = createContest();
        contest3.getPhotoSet().add(createPhoto(3));
        List<Contest> contests = new ArrayList<>(Arrays.asList(contest,contest2,contest3));

        when(contestRepository.getFinishedContests()).thenReturn(contests);


        assertEquals(3,photoService.getRandomWinningPhotos(6).size());
    }

    @Test
    public void getRandomWinningPhotos_Should_Return_Smaller_List_When_Photo_Is_Null(){
        Contest contest = createContest();
        Contest contest2 = createContest();
        contest2.getPhotoSet().add(createPhoto(2));
        Contest contest3 = createContest();
        contest3.getPhotoSet().add(createPhoto(3));
        List<Contest> contests = new ArrayList<>(Arrays.asList(contest,contest2,contest3));

        when(contestRepository.getFinishedContests()).thenReturn(contests);


        assertEquals(2,photoService.getRandomWinningPhotos(3).size());
    }

    @Test
    public void updateJuryList_Should_Add_Reviews_To_Photo(){
        Photo photo = createPhoto();
        Contest contest = createContest();
        User judge = createUser();
        contest.getJurySet().add(judge);
        contest.getPhotoSet().add(photo);
        photo.setContest(contest);

        when(reviewService.getPhotoReviews(anyInt())).thenReturn(new ArrayList<Review>());

        photoService.updateJuryList(photo);

        assertEquals(1,photo.getReviewSet().size());
    }

    @Test
    public void updateJuryList_Should_Remove_Reviews_From_Photo(){
        Photo photo = createPhoto();
        Contest contest = createContest();
        contest.getPhotoSet().add(photo);
        photo.setContest(contest);

        when(reviewService.getPhotoReviews(anyInt())).thenReturn(new ArrayList<Review>(Arrays.asList(createReview(),createReview())));

        photoService.updateJuryList(photo);

        assertEquals(0,photo.getReviewSet().size());
    }

    @Test
    public void canUserSubmitPhoto_Should_Call_Repository_When_Valid(){
        photoService.canUserSubmitPhoto(anyInt(),anyInt());

        verify(contestRepository,times(1)).canUserSubmitPhoto(anyInt(),anyInt());
    }

    @Test
    public void  hasUserSubmitPhotoToContest_Should_Call_Repository_When_Valid(){
        photoService.hasUserSubmitPhotoToContest(anyInt(),anyInt());

        verify(contestRepository,times(1)).hasUserSubmitPhotoToContest(anyInt(),anyInt());
    }

    @Test
    public void getPhotoReviews_Should_Call_Repository_When_Valid(){
        when(photoRepository.getPhotoById(anyInt())).thenReturn(createPhoto());

        photoService.getPhotoReviews(anyInt());

        verify(photoRepository,times(1)).getPhotoById(anyInt());
    }

    @Test
    public void  getAllPhotosByUserId_Should_Call_Repository_When_Valid(){
        photoService.getAllPhotosByUserId(anyInt());

        verify(photoRepository,times(1)).getAllPhotosByUserId(anyInt());
    }

}

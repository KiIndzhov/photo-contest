package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewOutputDto;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestRepository;
import com.telerikfinalproject.photocontest.repositories.contracts.PhotoRepository;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.contracts.ReviewService;
import com.telerikfinalproject.photocontest.services.mappers.ReviewModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final ContestRepository contestRepository;
    private final ReviewService reviewService;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, ContestRepository contestRepository, ReviewService reviewService) {
        this.photoRepository = photoRepository;
        this.contestRepository = contestRepository;
        this.reviewService = reviewService;
    }

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.getAllPhotos();
    }

    @Override
    public Photo getPhotoById(int id) {
        return photoRepository.getPhotoById(id);
    }

    @Override
    public Photo createPhoto(Photo photo) {
        Contest contest = photo.getContest();
        User user = photo.getUser();
        if (!contest.getParticipantsSet().contains(user)) {
            throw new UnauthorisedException("You are not part of the contest");
        }
        List<Review> reviewSet = new ArrayList<>();
        Set<User> judgeSet = contestRepository.getContestById(contest.getId()).getJurySet();

        for (User judge : judgeSet) {
            Review review = new Review();
            review.setPhoto(photo);
            review.setScore(3);
            review.setUser(judge);
            review.setEdited(false);
            reviewSet.add(review);
        }

        photo.setReviewSet(reviewSet);
        photoRepository.createPhoto(photo);
        return photo;
    }

    @Override
    public void updateJuryList(Photo photo) {
        List<User> oldJuryUserLIst = reviewService.getPhotoReviews(photo.getId()).stream().map(Review::getUser).collect(Collectors.toList());
        List<User> newJuryUserList = new ArrayList<>(photo.getContest().getJurySet());
        for (User user : newJuryUserList) {
            if (!oldJuryUserLIst.contains(user)) {
                Review review = new Review();
                review.setPhoto(photo);
                review.setScore(3);
                review.setUser(user);
                review.setEdited(false);
                photo.getReviewSet().add(review);
            }
        }
        for (User user : oldJuryUserLIst) {
            if (!newJuryUserList.contains(user)) {
                Review review = reviewService.getReviewByJuryIdAndPhotoId(user.getId(), photo.getId());
                photo.getReviewSet().remove(review);
            }
        }
    }
    @Override
    public List<Photo> getAllWinnerPhotos() {
        return contestRepository.getFinishedContests().stream().map(Contest::getWinnerPhoto).collect(Collectors.toList());
    }

    @Override
    public List<Photo> getRandomWinningPhotos(int amount) {
        List<Photo> getWinningPhotos = getAllWinnerPhotos();
        Random rand = new Random();
        List<Photo> randomPhotoList = new ArrayList<>();
        if(getWinningPhotos.size() < amount){
            amount = getWinningPhotos.size();
        }
        while(randomPhotoList.size() < amount){
            Photo randomPhoto = getWinningPhotos.get(rand.nextInt(getWinningPhotos.size()));
            if(randomPhotoList.contains(randomPhoto)){
                continue;}
            if(randomPhoto == null){
                amount--;
                continue;
            }
            randomPhotoList.add(randomPhoto);
        }
        return randomPhotoList;
    }
    @Override
    public boolean canUserSubmitPhoto(int userId, int contestId) {
        return contestRepository.canUserSubmitPhoto(userId, contestId);
    }
    @Override
    public boolean hasUserSubmitPhotoToContest(int userId, int contestId) {
        return contestRepository.hasUserSubmitPhotoToContest(userId, contestId);
    }


    @Override
    public List<Review> getPhotoReviews(int id) {
        return (photoRepository.getPhotoById(id)).getReviewSet();

    }

    @Override
    public List<Photo> getAllPhotosByUserId(int userId) {
        return photoRepository.getAllPhotosByUserId(userId);
    }


}

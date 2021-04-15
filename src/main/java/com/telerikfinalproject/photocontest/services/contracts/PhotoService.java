package com.telerikfinalproject.photocontest.services.contracts;

import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewOutputDto;

import java.util.List;

public interface PhotoService {
    List<Photo> getAllPhotos();

    Photo getPhotoById(int id);

    Photo createPhoto(Photo photo);

    void updateJuryList(Photo photo);

    boolean canUserSubmitPhoto(int userId, int contestId);

    boolean hasUserSubmitPhotoToContest(int userId, int contestId);

    List<Photo> getAllWinnerPhotos();

    List<Photo> getRandomWinningPhotos(int amount);

    List<ReviewOutputDto> getPhotoReviews(int id);

    List<Photo> getAllPhotosByUserId(int userId);
}

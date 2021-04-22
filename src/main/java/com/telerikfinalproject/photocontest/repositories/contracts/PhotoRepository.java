package com.telerikfinalproject.photocontest.repositories.contracts;

import com.telerikfinalproject.photocontest.models.Photo;

import java.util.List;

public interface PhotoRepository {
    List<Photo> getAllPhotos();

    Photo getPhotoById(int id);

    void createPhoto(Photo photo);

    List<Photo> getAllPhotosByUserId(int userId);
}

package com.telerikfinalproject.photocontest.services.mappers;

import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.dtomodels.PhotoCreateDto;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.ImageService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoModelMapper {

    private final ImageService imageService;
    private final UserService userService;
    private final ContestService contestService;

    @Autowired
    public PhotoModelMapper(ImageService imageService, UserService userService, ContestService contestService) {
        this.imageService = imageService;
        this.userService = userService;
        this.contestService = contestService;
    }

    public Photo dtoToPhoto(PhotoCreateDto photoCreateDto) {
        Photo photo = new Photo();
        photo.setTitle(photoCreateDto.getTitle());
        photo.setStory(photoCreateDto.getStory());
        photo.setUser(userService.getUserById(photoCreateDto.getUserId()));
        photo.setContest(contestService.getContestById(photoCreateDto.getContestId()));
        photo.setFilePath(imageService.saveImage(photoCreateDto.getPhotoFile(), photo.getUser().getFirstName()));

        return photo;
    }
}

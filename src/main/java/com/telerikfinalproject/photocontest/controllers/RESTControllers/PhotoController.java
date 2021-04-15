package com.telerikfinalproject.photocontest.controllers.RESTControllers;

import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.dtomodels.PhotoCreateDto;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.mappers.PhotoModelMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.telerikfinalproject.photocontest.controllers.utils.Constants.*;

@Api(tags = {"photos"})
@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    private final PhotoService photoService;
    private final PhotoModelMapper mapper;

    @Autowired
    public PhotoController(PhotoService photoService, PhotoModelMapper mapper) {
        this.photoService = photoService;
        this.mapper = mapper;
    }

    @ApiOperation(value = ALL_PHOTOS, response = List.class)
    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @ApiOperation(value = PHOTO_BY_ID, response = Photo.class)
    @GetMapping("{id}")
    public Photo getPhotoById(@PathVariable int id) {
        return photoService.getPhotoById(id);
    }

    @ApiOperation(value = UPLOAD_PHOTO, response = Photo.class)
    @PostMapping(consumes = {"multipart/form-data", "application/json"})
    public void createPhoto(@RequestPart("photoCreateDto") PhotoCreateDto photoCreateDto,
                            @RequestParam("image") MultipartFile image) {

        photoCreateDto.setPhotoFile(image);
        Photo photo = mapper.dtoToPhoto(photoCreateDto);
        photoService.createPhoto(photo);
    }

    @ApiOperation(value = ALLOWED_TO_SUBMIT, response = Boolean.class)
    @GetMapping("{userId}/contest-submit/{contestId}")
    public boolean canUserSubmitPhoto(@PathVariable int userId, @PathVariable int contestId) {
        return photoService.canUserSubmitPhoto(userId, contestId);
    }

    @ApiOperation(value = ALREADY_SUBMITTED, response = Boolean.class)
    @GetMapping("{userId}/contest-check/{contestId}")
    public boolean hasUserSubmitPhotoToContest(@PathVariable int userId, @PathVariable int contestId) {
        return photoService.hasUserSubmitPhotoToContest(userId, contestId);
    }

    @ApiOperation(value = WINNER_PHOTOS , response = Photo.class)
    @GetMapping("/winners")
    public List<Photo> getAllWinnerPhotos() {
        return photoService.getAllWinnerPhotos();
    }

}

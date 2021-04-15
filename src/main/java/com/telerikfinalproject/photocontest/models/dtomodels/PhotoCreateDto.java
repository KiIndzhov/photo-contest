package com.telerikfinalproject.photocontest.models.dtomodels;

import org.springframework.web.multipart.MultipartFile;

public class PhotoCreateDto {

    private MultipartFile photoFile;

    private String title;

    private String story;

    private int userId;

    private int contestId;

    public PhotoCreateDto() {
    }

    public PhotoCreateDto(String story, int userId, int contestId) {
        this.story = story;
        this.userId = userId;
        this.contestId = contestId;
    }

    public MultipartFile getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(MultipartFile photoFile) {
        this.photoFile = photoFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }
}

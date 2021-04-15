package com.telerikfinalproject.photocontest.models.dtomodels;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class ReviewDto {

    @NotNull
    @Range(min = 1, max = 10, message = "Score should be between 1 and 10")
    private int score;

    private String comment;

    @NotNull
    private int juryId;

    @NotNull
    private boolean suitable;

    @NotNull
    private boolean edited;

    @NotNull
    private int photoId;

    public ReviewDto() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getJuryId() {
        return juryId;
    }

    public void setJuryId(int juryId) {
        this.juryId = juryId;
    }

    public boolean isSuitable() {
        return suitable;
    }

    public void setSuitable(boolean suitable) {
        this.suitable = suitable;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

}

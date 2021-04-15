package com.telerikfinalproject.photocontest.models.dtomodels;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReviewUpdateDto {

    @NotNull
    @Min(0)
    @Max(10)
    private int score;

    private String comment;


    public ReviewUpdateDto() {
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
}

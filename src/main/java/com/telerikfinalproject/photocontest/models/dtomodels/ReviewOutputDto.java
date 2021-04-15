package com.telerikfinalproject.photocontest.models.dtomodels;

public class ReviewOutputDto {

    private String comment;

    private String juryName;

    private int score;

    public ReviewOutputDto() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getJuryName() {
        return juryName;
    }

    public void setJuryName(String juryName) {
        this.juryName = juryName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

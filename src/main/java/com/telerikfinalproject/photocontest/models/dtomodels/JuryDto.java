package com.telerikfinalproject.photocontest.models.dtomodels;

public class JuryDto {

    private int userID;

    private String name;

    private boolean isOrganiser;

    private int score;

    public JuryDto() {
    }

    public JuryDto(int userID, String name, boolean isOrganiser, int score) {
        this.userID = userID;
        this.name = name;
        this.isOrganiser = isOrganiser;
        this.score = score;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOrganiser() {
        return isOrganiser;
    }

    public void setOrganiser(boolean organiser) {
        isOrganiser = organiser;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

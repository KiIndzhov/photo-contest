package com.telerikfinalproject.photocontest.models.dtomodels;

import com.telerikfinalproject.photocontest.models.utils.Rank;
import com.telerikfinalproject.photocontest.models.utils.UserRole;

public class UserOutputDto {

    private static final int PHOTO_ENTHUSIAST_MIN_SCORE = 50;
    private static final int PHOTO_MASTER_MIN_SCORE = 150;
    private static final int PHOTO_DICTATOR_MIN_SCORE = 1000;
    private int id;

    private String firstName;

    private String lastName;

    private String role;

    private int score;

    public UserOutputDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRank() {
        if (getRole().equals(UserRole.ORGANIZER.toString())) {
            return Rank.ORGANIZER.toString();
        }
        if (getScore() <= 50) {
            return Rank.JUNKIE.toString();
        }
        if (getScore() <= 150) {
            return Rank.ENTHUSIAST.toString();

        }
        if (getScore() <= 1000) {
            return Rank.MASTER.toString();
        }
        return Rank.DICTATOR.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPointsUntilNextRank() {
        if (getScore() < PHOTO_ENTHUSIAST_MIN_SCORE) {
            return String.valueOf(PHOTO_ENTHUSIAST_MIN_SCORE + 1 - getScore());
        } else if (getScore() < PHOTO_MASTER_MIN_SCORE) {
            return String.valueOf(PHOTO_MASTER_MIN_SCORE + 1 - getScore());
        } else if (getScore() < PHOTO_DICTATOR_MIN_SCORE) {
            return String.valueOf(PHOTO_DICTATOR_MIN_SCORE + 1 - getScore());
        } else {
            return Rank.DICTATOR.toString();
        }
    }
}

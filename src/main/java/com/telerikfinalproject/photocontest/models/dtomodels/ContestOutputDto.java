package com.telerikfinalproject.photocontest.models.dtomodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikfinalproject.photocontest.models.Photo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ContestOutputDto {

    private int id;

    private String title;

    private String category;

    private LocalDate daysPhase1;

    private LocalDateTime hoursPhase2;

    private boolean isOpen;

    @JsonIgnore
    private List<Photo> photoList;

    private String coverPhoto;

    private String winningPhoto;

    private List<Integer> juryList;

    private List<Integer> paricipantList;

    private boolean finished;

    public ContestOutputDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDaysPhase1() {
        return daysPhase1;
    }

    public void setDaysPhase1(LocalDate daysPhase1) {
        this.daysPhase1 = daysPhase1;
    }

    public LocalDateTime getHoursPhase2() {
        return hoursPhase2;
    }

    public void setHoursPhase2(LocalDateTime hoursPhase2) {
        this.hoursPhase2 = hoursPhase2;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinningPhoto() {
        return winningPhoto;
    }

    public void setWinningPhoto(String winningPhoto) {
        this.winningPhoto = winningPhoto;
    }

    public List<Integer> getJuryList() {
        return juryList;
    }

    public void setJuryList(List<Integer> juryList) {
        this.juryList = juryList;
    }

    public List<Integer> getParicipantList() {
        return paricipantList;
    }

    public void setParicipantList(List<Integer> paricipantList) {
        this.paricipantList = paricipantList;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

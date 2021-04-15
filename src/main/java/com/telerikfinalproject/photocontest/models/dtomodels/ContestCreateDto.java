package com.telerikfinalproject.photocontest.models.dtomodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Set;

public class ContestCreateDto {

    @NotNull
    @Size(min = 2, message = "Contest title should be at least 2 symbols.")
    private String title;

    @NotNull
    private int category;

    //    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date daysPhase1;

    @NotNull
    @Range(min = 1, max = 24, message = "Should be between 1 and 24 hours")
    private int hoursPhase2;

    private MultipartFile coverPhoto;

    //    @NotNull
    private Set<Integer> juryList;

    private Set<Integer> participantsSet;

    private boolean isOpen;

    public ContestCreateDto() {
    }

    public ContestCreateDto(String title, int category, Date daysPhase1, int hoursPhase2, boolean isOpen) {
        this.title = title;
        this.category = category;
        this.daysPhase1 = daysPhase1;
        this.hoursPhase2 = hoursPhase2;
        this.isOpen = isOpen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Date getDaysPhase1() {
        return daysPhase1;
    }

    public void setDaysPhase1(Date daysPhase1) {
        this.daysPhase1 = daysPhase1;
    }

    public int getHoursPhase2() {
        return hoursPhase2;
    }

    public void setHoursPhase2(int hoursPhase2) {
        this.hoursPhase2 = hoursPhase2;
    }

    public MultipartFile getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(MultipartFile coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public boolean getIsOpen() {
        return isOpen;
    }


    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    public Set<Integer> getJuryList() {
        return juryList;
    }

    public void setJuryList(Set<Integer> juryList) {
        this.juryList = juryList;
    }

    public Set<Integer> getParticipantsSet() {
        return participantsSet;
    }

    public void setParticipantsSet(Set<Integer> participantsSet) {
        this.participantsSet = participantsSet;
    }
}

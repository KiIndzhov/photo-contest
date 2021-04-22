package com.telerikfinalproject.photocontest.services.mappers;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestCreateDto;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestOutputDto;
import com.telerikfinalproject.photocontest.services.contracts.ContestCategoryService;
import com.telerikfinalproject.photocontest.services.contracts.ImageService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ContestModelMapper {

    private final ContestCategoryService contestCategoryService;
    private final ImageService imageService;
    private final UserService userService;

    @Autowired
    public ContestModelMapper(ContestCategoryService contestCategoryService, ImageService imageService, UserService userService) {
        this.contestCategoryService = contestCategoryService;
        this.imageService = imageService;
        this.userService = userService;
    }


    public Contest dtoToContest(ContestCreateDto contestCreateDto) {
        Contest contest = new Contest();
        contest.setTitle(contestCreateDto.getTitle());
        contest.setCategory(contestCategoryService.getCategoryById(contestCreateDto.getCategory()));
        contest.setTimeLimitPhase1(LocalDateTime.of(contestCreateDto.getDaysPhase1().toLocalDate(), LocalTime.of(0, 0)));
        contest.setTimeLimitPhase2(contest.getTimeLimitPhase1().plusHours(contestCreateDto.getHoursPhase2()));
        contest.setOpen(contestCreateDto.getIsOpen());
        contest.setCoverPhotoPath(imageService.saveImage(contestCreateDto.getCoverPhoto(), contestCreateDto.getTitle()));
        contest.setFinished(false);

        if (contestCreateDto.getJuryList() == null) {
            contest.setJurySet(new HashSet<>());
        }
        contest.setJurySet(contestCreateDto.getJuryList().stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet()));
        if (contestCreateDto.getParticipantsSet() == null) {
            contest.setParticipantsSet(new HashSet<>());
        } else {
            contest.setParticipantsSet(contestCreateDto.getParticipantsSet().stream()
                    .map(userService::getUserById)
                    .collect(Collectors.toSet()));
        }
        return contest;
    }

    public ContestOutputDto contestToDto(Contest contest) {
        ContestOutputDto outputContest = new ContestOutputDto();
        outputContest.setCategory(contest.getCategory().getCategory());
        outputContest.setDaysPhase1(contest.getTimeLimitPhase1().toLocalDate());
        outputContest.setHoursPhase2(contest.getTimeLimitPhase2());
        outputContest.setOpen(contest.getOpen());
        outputContest.setTitle(contest.getTitle());
        outputContest.setPhotoList(contest.getPhotoSet());
        outputContest.setCoverPhoto(contest.getCoverPhotoPath());
        outputContest.setJuryList(contest.getJurySet().stream().map(User::getId).collect(Collectors.toList()));
        outputContest.setParicipantList(contest.getParticipantsSet().stream().map(User::getId).collect(Collectors.toList()));
        outputContest.setFinished(contest.isFinished());
        if (contest.getWinnerPhoto() == null) {
            outputContest.setWinningPhoto(contest.getCoverPhotoPath());
        } else {
            outputContest.setWinningPhoto(contest.getWinnerPhoto().getFilePath());
        }
        outputContest.setId(contest.getId());

        return outputContest;

    }
}

package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestOutputDto;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestRepository;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.ImageService;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final PhotoService photoService;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository, ImageService imageService, UserService userService, PhotoService photoService) {
        this.contestRepository = contestRepository;
        this.imageService = imageService;
        this.userService = userService;
        this.photoService = photoService;
    }

    @Override
    public void create(Contest contest) {
        List<User> organiserJury = userService.getAllOrganisers();
        contest.getJurySet().addAll(organiserJury);
        if (contest.getTimeLimitPhase1().isBefore(LocalDateTime.now())) {
            throw new DateTimeException("Contest cannot be set in the past");
        }
        if (contest.getTimeLimitPhase1().isAfter(LocalDateTime.now().plusMonths(1))) {
            throw new DateTimeException("Length of contest cannot be more than one month");
        }
        contestRepository.createContest(contest);
    }

    @Override
    public Contest getContestById(int id) {
        return contestRepository.getContestById(id);
    }

    @Override
    public Resource getCoverPhotoByContestId(int id) {
        return imageService.getImage(contestRepository.getContestById(id).getCoverPhotoPath());
    }

    @Override
    public List<Contest> getAllContests() {
        return contestRepository.getAllContests();
    }

    @Override
    public void addUserToContest(int id, int userId) {
        User user = userService.getUserById(userId);
        Contest contest = getContestById(id);
        if (!contest.getOpen()) {
            throw new UnauthorisedException("You cannot add new users to a closed contest");
        }
        if (contest.getJurySet().contains(user)) {
            throw new UnauthorisedException("You cannot add a jury to the contest");
        }
        if (contest.getParticipantsSet().contains(user)) {
            throw new DuplicateEntityException("User is already in the competition");
        }
        contest.getParticipantsSet().add(user);

        contestRepository.updateContest(contest);
    }


    @Override
    public List<Contest> getFilteredContests(String filter) {
        switch (filter) {
            case "all":
                return contestRepository.getAllContests();
            case "open":
                return contestRepository.getOpenContest();
            case "closed":
                return contestRepository.getClosedContests();
            case "finished":
                return contestRepository.getFinishedContests();
            case "firstPhase":
                return contestRepository.getContestsInFirstPhase();
            case "secondPhase":
                return contestRepository.getContestsInSecondPhase();
            default:
                throw new IllegalArgumentException("No such filter");
        }
    }

    @Override
    public List<Contest> getFilteredContestsByUserId(String filter, int userId) {
        switch (filter) {
            case "participant":
                return contestRepository.getUserContests(userId);
            case "finished":
                return contestRepository.getCurrentUserFinishedContests(userId);
            case "jury":
                return contestRepository.getJudgingContests(userId);
            case "available":
                return contestRepository.getAllAvailableContests(userId);
            default:
                throw new IllegalArgumentException("No such filter");
        }
    }

    @Override
    public List<Contest> getFinishingContests() {
        return contestRepository.getFinishingContests();
    }

    @Override
    public void updateContest(Contest contest) {
        contestRepository.updateContest(contest);
    }

    @Override
    public List<Contest> getContestsInFirstPhase() {
        return contestRepository.getContestsInFirstPhase();
    }

    @Override
    public void updateJuryAndParticipants(Contest contest, List<Integer> newJurySet, List<Integer> newParticipantsSet) {
        updateJurySet(contest, newJurySet);
        updateParticipantsSet(contest, newParticipantsSet);

        List<Photo> photoList = contest.getPhotoSet();
        for (Photo photo : photoList) {
            photoService.updateJuryList(photo);
        }
        contestRepository.updateContest(contest);
    }

    @Override
    public List<List<Photo>> getRankingContests(ContestOutputDto contest) {
        List<Photo> firstPlace = new ArrayList<>();
        List<Photo> secondPlace = new ArrayList<>();
        List<Photo> thirdPlace = new ArrayList<>();
        List<Photo> unranked = new ArrayList<>();
        List<List<Photo>> contests = new ArrayList<>(Arrays.asList(firstPlace,secondPlace,thirdPlace,unranked));
        for (Photo photo : contest.getPhotoList()) {
            if(firstPlace.isEmpty()){
                firstPlace.add(photo);
                continue;
            }
            if(firstPlace.get(0).getPoints()==photo.getPoints()){
                firstPlace.add(photo);
                continue;
            }
            if(secondPlace.isEmpty()){
                secondPlace.add(photo);
                continue;
            }
            if(secondPlace.get(0).getPoints()==photo.getPoints()){
                secondPlace.add(photo);
                continue;
            }
            if(thirdPlace.isEmpty()){
                thirdPlace.add(photo);
                continue;
            }
            if(thirdPlace.get(0).getPoints()==photo.getPoints()){
                thirdPlace.add(photo);
                continue;
            }
            unranked.add(photo);
        }

        return contests;
    }

    private void updateJurySet(Contest contest, List<Integer> newJurySet) {
        if (!newJurySet.isEmpty()) {
            contest.setJurySet(new HashSet<>(newJurySet.stream().map(userService::getUserById).collect(Collectors.toList())));
        } else {
            contest.setJurySet(new HashSet<>());
        }
        contest.getJurySet().addAll(userService.getAllOrganisers());
    }

    private void updateParticipantsSet(Contest contest, List<Integer> newParticipantSet) {
        if (!newParticipantSet.isEmpty()) {
            contest.setParticipantsSet(new HashSet<>(newParticipantSet.stream().map(userService::getUserById).collect(Collectors.toList())));
        } else {
            contest.setParticipantsSet(new HashSet<>());
        }
    }

}

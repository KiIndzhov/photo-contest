package com.telerikfinalproject.photocontest.services.contracts;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestOutputDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ContestService {

    void create(Contest contest);

    Contest getContestById(int id);

    Resource getCoverPhotoByContestId(int id);

    List<Contest> getAllContests();

    void addUserToContest(int id, int userId);

    List<Contest> getFilteredContests(String filter);

    List<Contest> getFilteredContestsByUserId(String filter, int userId);

    List<Contest> getFinishingContests();

    void updateContest(Contest contest);

    List<Contest> getContestsInFirstPhase();

    void updateJuryAndParticipants(Contest contest, List<Integer> juryList, List<Integer> paricipantList);

    List<List<Photo>> getRankingContests(ContestOutputDto contest);
}

package com.telerikfinalproject.photocontest.repositories.contracts;

import com.telerikfinalproject.photocontest.models.Contest;

import java.util.List;

public interface ContestRepository {

    void createContest(Contest contest);

    Contest getContestById(int id);

    List<Contest> getAllContests();

    void updateContest(Contest contest);

    List<Contest> getFinishedContests();

    List<Contest> getUserContests(int id);

    List<Contest> getJudgingContests(int id);

    List<Contest> getOpenContest();

    List<Contest> getClosedContests();

    List<Contest> getContestsInFirstPhase();

    List<Contest> getContestsInSecondPhase();

    List<Contest> getCurrentUserFinishedContests(int userId);

    List<Contest> getAllAvailableContests(int userId);

    boolean canUserSubmitPhoto(int userId, int contestId);

    boolean hasUserSubmitPhotoToContest(int userId, int contestId);

    List<Contest> getFinishingContests();

}

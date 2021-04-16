package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.telerikfinalproject.photocontest.services.utils.Constants.*;

@Service
@Transactional
public class ScheduledService {

    private final ContestService contestService;
    private final UserService userService;

    @Autowired
    public ScheduledService(ContestService contestService, UserService userService) {
        this.contestService = contestService;
        this.userService = userService;
    }

    @Scheduled(fixedRate = TEN_MINUTES)
    public void updateRankingPoints() {

        List<Contest> contestsToBeFinished = contestService.getFinishingContests();

        for (Contest contest : contestsToBeFinished) {

            List<Photo> firstPlacePhotos = new ArrayList<>();
            List<Photo> secondPlacePhotos = new ArrayList<>();
            List<Photo> thirdPlacePhotos = new ArrayList<>();

            getAllWinnersLists(contest, firstPlacePhotos, secondPlacePhotos, thirdPlacePhotos);
            updateUserPoints(firstPlacePhotos, secondPlacePhotos, thirdPlacePhotos);

            contest.setFinished(true);
            contestService.updateContest(contest);
        }
    }

    private void getAllWinnersLists(Contest contest,
                                    List<Photo> firstPlacePhotos,
                                    List<Photo> secondPlacePhotos,
                                    List<Photo> thirdPlacePhotos) {

        for (Photo photo : contest.getPhotoSet()) {

            addParticipationPointsToAllContestParticipants(contest, photo);

            if (firstPlacePhotos.isEmpty()) {
                firstPlacePhotos.add(photo);
                continue;
            }
            if (photo.getPoints() == firstPlacePhotos.get(0).getPoints()) {
                firstPlacePhotos.add(photo);
                continue;
            }
            if (secondPlacePhotos.isEmpty()) {
                secondPlacePhotos.add(photo);
                continue;
            }
            if (photo.getPoints() == secondPlacePhotos.get(0).getPoints()) {
                secondPlacePhotos.add(photo);
                continue;
            }
            if (thirdPlacePhotos.isEmpty()) {
                thirdPlacePhotos.add(photo);
                continue;
            }
            if (photo.getPoints() == thirdPlacePhotos.get(0).getPoints()) {
                thirdPlacePhotos.add(photo);
            }
        }
    }

    private void addParticipationPointsToAllContestParticipants(Contest contest, Photo photo) {
        if (!contest.getOpen()) {
            addPoints(photo.getUser(), INVITATIONAL_CONTEST_POINTS);
        } else {
            addPoints(photo.getUser(), OPEN_CONTEST_POINTS);
        }
    }

    private void updateUserPoints(List<Photo> firstPlacePhotos,
                                  List<Photo> secondPlacePhotos,
                                  List<Photo> thirdPlacePhotos) {

        int firstPlace = FIRST_PLACE_POINTS;
        int secondPlace = SECOND_PLACE_POINTS;
        int thirdPlace = THIRD_PLACE_POINTS;

        if (!firstPlacePhotos.isEmpty() && !secondPlacePhotos.isEmpty()) {
            if ((firstPlacePhotos.get(0).getPoints() / secondPlacePhotos.get(0).getPoints()) >= 2) {
                firstPlace = FIRST_PLACE_DOUBLE_POINTS;
            }
        }
        if (firstPlacePhotos.size() > 1) {
            firstPlace = SHARED_FIRST_PLACE_POINTS;
        }
        if (secondPlacePhotos.size() > 1) {
            secondPlace = SHARED_SECOND_PLACE_POINTS;
        }
        if (thirdPlacePhotos.size() > 1) {
            thirdPlace = SHARED_THIRD_PLACE_POINTS;
        }

        for (Photo firstPlacePhoto : firstPlacePhotos) {
            addPoints(firstPlacePhoto.getUser(), firstPlace);
        }
        for (Photo secondPlacePhoto : secondPlacePhotos) {
            addPoints(secondPlacePhoto.getUser(), secondPlace);
        }
        for (Photo thirdPlacePhoto : thirdPlacePhotos) {
            addPoints(thirdPlacePhoto.getUser(), thirdPlace);
        }
    }

    private void addPoints(User user, int points) {
        user.setScore(user.getScore() + points);
        userService.updateUser(user);
    }
}

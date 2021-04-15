package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.models.*;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class Helpers {

    public static ContestCategory createCategory() {
        ContestCategory category = new ContestCategory();
        category.setId(1);
        category.setCategory("testCategory");
        return category;
    }

    public static Contest createContest() {
        Contest contest = new Contest();
        contest.setId(1);
        contest.setTitle("Title");
        contest.setCategory(createCategory());
        contest.setJurySet(new HashSet<>());
        contest.setParticipantsSet(new HashSet<>());
        contest.setPhotoSet(new ArrayList<>());
        contest.setFinished(false);
        contest.setOpen(true);
        contest.setCoverPhotoPath("testPath");
        contest.setTimeLimitPhase1(LocalDateTime.now());
        contest.setTimeLimitPhase2(LocalDateTime.now());
        return contest;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Firstname");
        user.setLastName("LastName");
        user.setScore(1);
        user.setCredential(createCredentials());
        return user;
    }

    public static User createUser(int id) {
        User user = new User();
        user.setId(id);
        user.setFirstName("Firstname");
        user.setLastName("LastName");
        user.setScore(0);
        user.setCredential(createCredentials());
        return user;
    }

    public static Credential createCredentials() {
        Credential credential = new Credential();
        credential.setId(1);
        credential.setUsername("UserName");
        credential.setPassword("password");
        credential.setUserRole(UserRole.ORGANIZER);
        return credential;
    }

    public static Photo createPhoto(){
        Photo photo = new Photo();
        photo.setId(1);
        photo.setTitle("title");
        photo.setStory("story");
        photo.setContest(createContest());
        photo.setReviewSet(new ArrayList<>());
        photo.setUser(createUser());
        photo.setFilePath("filePath");
        return photo;
    }

    public static Photo createPhoto(int id){
        Photo photo = new Photo();
        photo.setId(id);
        photo.setTitle("title");
        photo.setStory("story");
        photo.setContest(createContest());
        photo.setReviewSet(new ArrayList<>());
        photo.setUser(createUser());
        photo.setFilePath("filePath");
        return photo;
    }

    public static Review createReview(){
        Review review = new Review();
        review.setId(1);
        review.setUser(createUser());
        review.setScore(3);
        review.setComment("NoComment");
        review.setEdited(false);
        review.setPhoto(createPhoto());
        return review;
    }
}

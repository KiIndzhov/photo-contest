package com.telerikfinalproject.photocontest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "photos")
public class Photo implements Comparable<Photo> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "title")
    private String title;

    @Column(name = "story")
    private String story;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "photo", cascade = CascadeType.ALL)
    private List<Review> reviewSet;

    public Photo() {
    }

    public Photo(int id, String filePath, String story, User user, Contest contest) {
        this.id = id;
        this.filePath = filePath;
        this.story = story;
        this.user = user;
        this.contest = contest;
    }

    public Photo(String filePath, String story, User user, Contest contest) {
        this.filePath = filePath;
        this.story = story;
        this.user = user;
        this.contest = contest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(List<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }

    @Transient
    public int getPoints() {
        int points = 0;
        for (Review review : getReviewSet()) {
            points = points + review.getScore();
        }
        return points;

    }

    @JsonIgnore
    @Transient
    public Review getByJuryId(int id) {
        for (Review review : getReviewSet()) {
            if (review.getUser().getId() == id) {
                return review;
            }
        }
        return null;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo = (Photo) o;
        return id == photo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Photo o) {
        return Integer.compare(this.getPoints(), o.getPoints());
    }
}


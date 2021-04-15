package com.telerikfinalproject.photocontest.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "contests")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ContestCategory category;

    @Column(name = "time_limit_phase_1")
    private LocalDateTime timeLimitPhase1;

    @Column(name = "time_limit_phase_2")
    private LocalDateTime timeLimitPhase2;

    @Column(name = "cover_photo")
    private String coverPhotoPath;

    @Column(name = "is_open")
    private Boolean isOpen;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contest", targetEntity = Photo.class)
    @Fetch(FetchMode.SELECT)
    private List<Photo> photoSet;


    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "contests_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participantsSet;

    //TODO if this should be ManyToMany or OneToMany
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(
            name = "contests_juries",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> jurySet;

    private boolean finished;

    public Contest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContestCategory getCategory() {
        return category;
    }

    public void setCategory(ContestCategory category) {
        this.category = category;
    }

    public LocalDateTime getTimeLimitPhase1() {
        return timeLimitPhase1;
    }

    public void setTimeLimitPhase1(LocalDateTime timeLimitPhase1) {
        this.timeLimitPhase1 = timeLimitPhase1;
    }

    public LocalDateTime getTimeLimitPhase2() {
        return timeLimitPhase2;
    }

    public void setTimeLimitPhase2(LocalDateTime timeLimitPhase2) {
        this.timeLimitPhase2 = timeLimitPhase2;
    }

    public String getCoverPhotoPath() {
        return coverPhotoPath;
    }

    public void setCoverPhotoPath(String coverPhotoPath) {
        this.coverPhotoPath = coverPhotoPath;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public List<Photo> getPhotoSet() {
        Collections.sort(this.photoSet);
        Collections.reverse(this.photoSet);
        return photoSet;
    }

    public void setPhotoSet(List<Photo> photoSet) {
        this.photoSet = photoSet;
    }

    public Set<User> getJurySet() {
        return jurySet;
    }

    public void setJurySet(Set<User> jurySet) {
        this.jurySet = jurySet;
    }

    public Set<User> getParticipantsSet() {
        return participantsSet;
    }

    public void setParticipantsSet(Set<User> participantsSet) {
        this.participantsSet = participantsSet;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Transient
    public Photo getWinnerPhoto() {
        if (getPhotoSet().isEmpty()) {
            return null;
        }
        return getPhotoSet().get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contest contest = (Contest) o;
        return id == contest.id && title.equals(contest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}

package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.exceptions.UnauthorisedException;
import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestRepository;
import com.telerikfinalproject.photocontest.services.contracts.ImageService;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.telerikfinalproject.photocontest.services.Helpers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContestServiceImplTests {

    @Mock
    ContestRepository contestRepository;
    @Mock
    ImageService imageService;
    @Mock
    UserService userService;
    @Mock
    PhotoService photoService;

    @InjectMocks
    ContestServiceImpl contestService;

    @Test
    public void create_Should_Call_Repository_WhenValid() {
        Contest contest = createContest();
        contest.setTimeLimitPhase1(LocalDateTime.now().plusDays(1));

        contestService.create(contest);

        verify(contestRepository, times(1)).createContest(contest);
    }

    @Test
    public void create_Should_ThrowException_When_Phase1_Is_BeforeNow() {
        Contest contest = createContest();
        contest.setTimeLimitPhase1(LocalDateTime.now().minusDays(1));

        assertThrows(DateTimeException.class, () -> contestService.create(contest));
    }

    @Test
    public void create_Should_ThrowException_When_Phase1_Is_AfterOneMounth() {
        Contest contest = createContest();
        contest.setTimeLimitPhase1(LocalDateTime.now().plusMonths(1).plusDays(1));

        assertThrows(DateTimeException.class, () -> contestService.create(contest));
    }

    @Test
    public void getCoverPhotoByContestId_Should_Call_Repository_When_valid() {
        when(contestRepository.getContestById(Mockito.anyInt())).thenReturn(createContest());

        contestService.getCoverPhotoByContestId(Mockito.anyInt());

        verify(imageService, times(1)).getImage(createContest().getCoverPhotoPath());
    }

    @Test
    public void getAllContests_Should_Call_Repository_When_Valid() {
        contestService.getAllContests();

        verify(contestRepository, times(1)).getAllContests();
    }

    @Test
    public void addUserToContest_Should_Call_Repository_WhenValid() {
        when(userService.getUserById(1)).thenReturn(createUser());
        when(contestService.getContestById(1)).thenReturn(createContest());

        contestService.addUserToContest(1, 1);

        verify(contestRepository, times(1)).updateContest(createContest());
    }

    @Test
    public void addUserToContest_Should_ThrowException_When_ContestInvitational() {
        when(userService.getUserById(1)).thenReturn(createUser());
        Contest contest = createContest();
        contest.setOpen(false);
        when(contestService.getContestById(1)).thenReturn(contest);

        assertThrows(UnauthorisedException.class, () -> contestService.addUserToContest(1, 1));
    }

    @Test
    public void addUserToContest_Should_ThrowException_When_User_Is_Jury() {
        User user = createUser();
        when(userService.getUserById(1)).thenReturn(user);
        Contest contest = createContest();
        contest.getJurySet().add(user);
        when(contestService.getContestById(1)).thenReturn(contest);

        assertThrows(UnauthorisedException.class, () -> contestService.addUserToContest(1, 1));
    }

    @Test
    public void addUserToContest_Should_ThrowException_When_User_Is_Already_Participant() {
        User user = createUser();
        when(userService.getUserById(1)).thenReturn(user);
        Contest contest = createContest();
        contest.getParticipantsSet().add(user);
        when(contestService.getContestById(1)).thenReturn(contest);

        assertThrows(DuplicateEntityException.class, () -> contestService.addUserToContest(1, 1));
    }

    @Test
    public void getFilteredContests_Should_ThrowException_When_InvalidFilter() {
        assertThrows(IllegalArgumentException.class, () -> contestService.getFilteredContests("WrongFilter"));
    }

    @Test
    public void getFilteredContest_should_Call_Repository_When_Filter_all() {

        contestService.getFilteredContests("all");

        verify(contestRepository, times(1)).getAllContests();
    }

    @Test
    public void getFilteredContest_should_Call_Repository_When_Filter_open() {

        contestService.getFilteredContests("open");

        verify(contestRepository, times(1)).getOpenContest();
    }

    @Test
    public void getFilteredContest_should_Call_Repository_When_Filter_closed() {

        contestService.getFilteredContests("closed");

        verify(contestRepository, times(1)).getClosedContests();
    }

    @Test
    public void getFilteredContest_should_Call_Repository_When_Filter_finished() {

        contestService.getFilteredContests("finished");

        verify(contestRepository, times(1)).getFinishedContests();
    }

    @Test
    public void getFilteredContest_should_Call_Repository_When_Filter_firstPhase() {

        contestService.getFilteredContests("firstPhase");

        verify(contestRepository, times(1)).getContestsInFirstPhase();
    }

    @Test
    public void getFilteredContest_should_Call_Repository_When_Filter_secondPhase() {

        contestService.getFilteredContests("secondPhase");

        verify(contestRepository, times(1)).getContestsInSecondPhase();
    }

    @Test
    public void getFilteredContestsByUserId_Should_ThrowException_When_InvalidFilter() {
        assertThrows(IllegalArgumentException.class, () -> contestService.getFilteredContestsByUserId("WrongFilter", 1));
    }

    @Test
    public void getFilteredContestByUserId_should_Call_Repository_When_Filter_participant() {

        contestService.getFilteredContestsByUserId("participant", 1);

        verify(contestRepository, times(1)).getUserContests(1);
    }

    @Test
    public void getFilteredContestByUserId_should_Call_Repository_When_Filter_finished() {

        contestService.getFilteredContestsByUserId("finished", 1);

        verify(contestRepository, times(1)).getCurrentUserFinishedContests(1);
    }

    @Test
    public void getFilteredContestByUserId_should_Call_Repository_When_Filter_jury() {

        contestService.getFilteredContestsByUserId("jury", 1);

        verify(contestRepository, times(1)).getJudgingContests(1);
    }

    @Test
    public void getFilteredContestByUserId_should_Call_Repository_When_Filter_available() {

        contestService.getFilteredContestsByUserId("available", 1);

        verify(contestRepository, times(1)).getAllAvailableContests(1);
    }

    @Test
    public void getFinishedContests_Should_Call_Repository_When_Valid() {
        contestService.getFinishingContests();

        verify(contestRepository, times(1)).getFinishingContests();
    }

    @Test
    public void updateContest_Should_Call_Repository_When_Valid() {
        contestService.updateContest(createContest());

        verify(contestRepository, times(1)).updateContest(createContest());
    }

    @Test
    public void getContestsInFirstPhase() {
        contestService.getContestsInFirstPhase();

        verify(contestRepository, times(1)).getContestsInFirstPhase();
    }

    @Test
    public void updateJuryAndParticipants_Should_Add_Organiser_When_EmptyJurySet() {
        Contest contest = createContest();
        List<Integer> juryList = new ArrayList<>();
        List<Integer> participantsList = new ArrayList<>();
        when(userService.getAllOrganisers()).thenReturn(new ArrayList<User>(Arrays.asList(createUser(1), createUser(2), createUser(3))));

        contestService.updateJuryAndParticipants(contest, juryList, participantsList);

        assertEquals(3, contest.getJurySet().size());
    }

    @Test
    public void updateJuryAndParticipants_Should_Add_JurySet_When_NotEmptyJurySet() {
        Contest contest = createContest();
        List<Integer> juryList = new ArrayList<>();
        juryList.add(4);
        juryList.add(5);
        when(userService.getUserById(4)).thenReturn(createUser(4));
        when(userService.getUserById(5)).thenReturn(createUser(5));
        List<Integer> participantsList = new ArrayList<>();
        when(userService.getAllOrganisers()).thenReturn(new ArrayList<User>(Arrays.asList(createUser(1), createUser(2), createUser(3))));

        contestService.updateJuryAndParticipants(contest, juryList, participantsList);

        assertEquals(5, contest.getJurySet().size());
    }

    @Test
    public void updateJuryAndParticipants_Should_Delete_JurySet_When_NotEmptyJurySet() {
        Contest contest = createContest();
        contest.getJurySet().addAll(Arrays.asList(createUser(4), createUser(5), createUser(6), createUser(7)));
        List<Integer> juryList = new ArrayList<>();
        juryList.add(4);
        when(userService.getUserById(4)).thenReturn(createUser(4));
        List<Integer> participantsList = new ArrayList<>();
        when(userService.getAllOrganisers()).thenReturn(new ArrayList<User>(Arrays.asList(createUser(1), createUser(2), createUser(3))));

        contestService.updateJuryAndParticipants(contest, juryList, participantsList);

        assertEquals(4, contest.getJurySet().size());
    }

    @Test
    public void updateJuryAndParticipants_Should_Return_Empty_PartSet_When_EmptyPartSet() {
        Contest contest = createContest();
        contest.getParticipantsSet().addAll(Arrays.asList(createUser(1), createUser(2)));
        List<Integer> jurySet = new ArrayList<>();
        List<Integer> partSet = new ArrayList<>();
        contestService.updateJuryAndParticipants(contest, jurySet, partSet);

        assertEquals(0, contest.getParticipantsSet().size());
    }

    @Test
    public void updateJuryAndParticipants_Should_Add_To_PartSet_When_PartSet_NotEmpty() {
        Contest contest = createContest();
        List<Integer> jurySet = new ArrayList<>();
        List<Integer> partSet = new ArrayList<>();
        partSet.add(4);
        partSet.add(5);
        when(userService.getUserById(4)).thenReturn(createUser(4));
        when(userService.getUserById(5)).thenReturn(createUser(5));
        contestService.updateJuryAndParticipants(contest, jurySet, partSet);

        assertEquals(2, contest.getParticipantsSet().size());
    }

    @Test
    public void updateJuryAndParticipants_Should_Call_Repository_WhenValid() {
        Contest contest = createContest();
        List<Integer> jurySet = new ArrayList<>();
        List<Integer> partSet = new ArrayList<>();
        contestService.updateJuryAndParticipants(contest, jurySet, partSet);

        verify(contestRepository, times(1)).updateContest(contest);
    }

    @Test
    public void updateJuryAndParticipants_Should_Call_PhotoRepository_For_Every_Photo() {
        Contest contest = createContest();
        contest.getPhotoSet().addAll(Arrays.asList(createPhoto(), createPhoto(), createPhoto()));
        List<Integer> jurySet = new ArrayList<>();
        List<Integer> partSet = new ArrayList<>();
        contestService.updateJuryAndParticipants(contest, jurySet, partSet);

        verify(photoService, times(3)).updateJuryList(createPhoto());
    }


}

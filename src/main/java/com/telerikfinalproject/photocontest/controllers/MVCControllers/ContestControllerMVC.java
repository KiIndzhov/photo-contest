package com.telerikfinalproject.photocontest.controllers.MVCControllers;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.Review;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestOutputDto;
import com.telerikfinalproject.photocontest.models.dtomodels.JuryDto;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.contracts.ReviewService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.mappers.ContestModelMapper;
import com.telerikfinalproject.photocontest.services.mappers.ReviewModelMapper;
import com.telerikfinalproject.photocontest.services.mappers.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contest")
public class ContestControllerMVC {

    private final ContestService contestService;
    private final UserService userService;
    private final ContestModelMapper contestModelMapper;
    private final ReviewModelMapper reviewModelMapper;
    private final ReviewService reviewService;
    private final PhotoService photoService;
    private final UserModelMapper userModelMapper;

    @Autowired
    public ContestControllerMVC(ContestService contestService,
                                UserService userService,
                                ContestModelMapper contestModelMapper,
                                ReviewModelMapper reviewModelMapper,
                                ReviewService reviewService,
                                PhotoService photoService,
                                UserModelMapper userModelMapper) {
        this.contestService = contestService;
        this.userService = userService;
        this.contestModelMapper = contestModelMapper;
        this.reviewModelMapper = reviewModelMapper;
        this.reviewService = reviewService;
        this.photoService = photoService;
        this.userModelMapper = userModelMapper;
    }

    @ModelAttribute("isOrganiser")
    private boolean isOrganiser(HttpSession session) {
        UserOutputDto loggedUser = (UserOutputDto) session.getAttribute("loggedUser");
        return loggedUser.getRole().equals("ORGANIZER");
    }

    @GetMapping("/organiser/{id}/edit")
    public String getContestEditView(@PathVariable int id, Model model, HttpSession session) {
        Contest contest = contestService.getContestById(id);
        ContestOutputDto contestOutputDto = contestModelMapper.contestToDto(contest);
        List<JuryDto> possibleJury = userService.getAllJurors().stream().map(userModelMapper::userToJuryDto).collect(Collectors.toList());
        List<User> possibleParticipants = userService.getAllJunkies();
        boolean isInPhaseOne = contestOutputDto.getDaysPhase1().isAfter(LocalDate.now());
        model.addAttribute("contest", contestOutputDto);
        model.addAttribute("jury", possibleJury);
        model.addAttribute("participants", possibleParticipants);
        model.addAttribute("isInPhaseOne", isInPhaseOne);


        return "organiserEditContest";

    }

    @PostMapping("/organiser/{id}/edit")
    public String editContest(@PathVariable int id, @ModelAttribute("contest") ContestOutputDto contestOutputDto, BindingResult errors, Model model, HttpSession session) {
        Contest contest = contestService.getContestById(contestOutputDto.getId());
        contestService.updateJuryAndParticipants(contest, contestOutputDto.getJuryList(), contestOutputDto.getParicipantList());
        return "redirect:/contest/organiser/" + id + "/edit/";

    }

    @GetMapping("/{id}")
    public String viewContestPage(@PathVariable int id, Model model, HttpSession session) {

        UserOutputDto loggedUser = (UserOutputDto) session.getAttribute("loggedUser");
        ContestOutputDto contest = contestModelMapper.contestToDto(contestService.getContestById(id));

        Set<Integer> userSet = new HashSet<>(contest.getJuryList());
        if (userSet.contains(loggedUser.getId())) {

            ReviewDto reviewToUpdate = new ReviewDto();
            reviewToUpdate.setScore(3);
            boolean canShow = contest.getDaysPhase1().isBefore(LocalDate.now());
            LocalDate timeLeft = contest.getDaysPhase1();

            model.addAttribute("contest", contest);
            model.addAttribute("review", reviewToUpdate);
            model.addAttribute("timeLeft", timeLeft);
            model.addAttribute("canShow", canShow);

            return "contestJuryView";
        }

        userSet = new HashSet<>(contest.getParicipantList());
        if (userSet.contains(loggedUser.getId())) {
            List<List<Photo>> contests = contestService.getRankingContests(contest);
            model.addAttribute("firstPLace",contests.get(0));
            model.addAttribute("secondPLace",contests.get(1));
            model.addAttribute("thirdPLace",contests.get(2));
            model.addAttribute("unranked",contests.get(3));
            model.addAttribute("contest", contest);

            LocalDateTime endPhase2 = contest.getHoursPhase2();
            LocalDate endPhase1 = contest.getDaysPhase1();

            boolean canShow = endPhase2.isBefore(LocalDateTime.now());
            boolean canSubmit = LocalDate.now().isBefore(endPhase1)
                    && !photoService.hasUserSubmitPhotoToContest(loggedUser.getId(), contest.getId());

            model.addAttribute("fullTimeLeft", endPhase2);
            model.addAttribute("submitTimeLeft", endPhase1);
            model.addAttribute("canShow", canShow);
            model.addAttribute("canSubmit", canSubmit);

            return "contestParticipantView";
        }
        return "redirect:/contest/enroll/" + id;
    }

    @PostMapping("/{id}")
    public String submitReview(@Valid @ModelAttribute("review") ReviewDto reviewDto, @PathVariable int id, Model model, HttpSession session) {
        Review review = reviewModelMapper.reviewDtoToReview(reviewDto);
        reviewService.updateReview(review);
        return "redirect:/contest/" + id;
    }

    @GetMapping("/enroll/{id}")
    public String viewEnrolPage(@PathVariable int id, Model model, HttpSession session) {
        ContestOutputDto contest = contestModelMapper.contestToDto(contestService.getContestById(id));
        model.addAttribute("contest", contest);

        return "contestEnrolView";
    }

    @GetMapping("/enroll/{contestId}/{userId}")
    public String enrollContestant(@PathVariable int contestId, @PathVariable int userId) {
        contestService.addUserToContest(contestId, userId);
        return "redirect:/contest/" + contestId;
    }


}

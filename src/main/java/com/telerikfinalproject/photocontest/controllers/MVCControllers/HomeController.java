package com.telerikfinalproject.photocontest.controllers.MVCControllers;

import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.UserDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.mappers.ContestModelMapper;
import com.telerikfinalproject.photocontest.services.mappers.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping()
public class HomeController {

    private static final int RANDOM_PHOTOS = 20;
    private static final int AMOUNT_JUNKIES = 10;
    private final UserService userService;
    private final UserModelMapper userModelMapper;
    private final PhotoService photoService;
    private final ContestService contestService;
    private final ContestModelMapper contestModelMapper;

    @Autowired
    public HomeController(UserService userService, UserModelMapper userModelMapper, PhotoService photoService, ContestService contestService, ContestModelMapper contestModelMapper) {
        this.userService = userService;
        this.userModelMapper = userModelMapper;
        this.photoService = photoService;
        this.contestService = contestService;
        this.contestModelMapper = contestModelMapper;
    }

    @GetMapping
    public String showHomepage(Model model, HttpSession session) {
        UserOutputDto user = (UserOutputDto) session.getAttribute("loggedUser");
        model.addAttribute("winnerPhotos",photoService.getRandomWinningPhotos(RANDOM_PHOTOS));
        model.addAttribute("openContests",contestService.getContestsInFirstPhase().stream().map(contestModelMapper::contestToDto).collect(Collectors.toList()));
        model.addAttribute("topJunkies",userService.getTopJunkies(AMOUNT_JUNKIES).stream().map(userModelMapper::userToDto).collect(Collectors.toList()));
        if (user != null) {
            if (user.getRole().equals(UserRole.ORGANIZER.toString())) {
                return "redirect:/organiser";
            }
            if (user.getRole().equals(UserRole.PHOTO_JUNKIE.toString())) {
                return  "redirect:/junkie";
            }
        }

        return "index";
    }

    @GetMapping("/login")
    public String showLogin(HttpSession session, Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @GetMapping("/about")
    public String showAbout(HttpSession session, Model model) {
        return "about";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult errors, HttpSession session, Model model) {
        if (errors.hasErrors()) {
            return "/login";
        }
        try {
            User newUser = userModelMapper.userDtoToUser(userDto);
            userService.createUser(newUser);
            return "redirect:/";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logoutPage")
    public String showLogout(HttpSession session) {
        return "/logoutPage";
    }

 }

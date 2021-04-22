package com.telerikfinalproject.photocontest.controllers.MVCControllers;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.ContestCategory;
import com.telerikfinalproject.photocontest.models.dtomodels.*;
import com.telerikfinalproject.photocontest.services.contracts.ContestCategoryService;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.mappers.CategoryModelMapper;
import com.telerikfinalproject.photocontest.services.mappers.ContestModelMapper;
import com.telerikfinalproject.photocontest.services.mappers.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/organiser")
public class OrganizerController {

    private final ContestService contestService;
    private final ContestModelMapper contestModelMapper;
    private final ContestCategoryService contestCategoryService;
    private final CategoryModelMapper categoryModelMapper;
    private final UserService userService;
    private final UserModelMapper userModelMapper;

    @Autowired
    public OrganizerController(ContestService contestService,
                               ContestModelMapper contestModelMapper,
                               ContestCategoryService contestCategoryService,
                               CategoryModelMapper categoryModelMapper,
                               UserService userService,
                               UserModelMapper userModelMapper) {
        this.contestService = contestService;
        this.contestModelMapper = contestModelMapper;
        this.contestCategoryService = contestCategoryService;
        this.categoryModelMapper = categoryModelMapper;
        this.userService = userService;
        this.userModelMapper = userModelMapper;
    }

    @GetMapping()
    public String showOrganiserHomepage(Model model, HttpSession session) {
        List<ContestOutputDto> outputDtoList = contestService.getAllContests().stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());
        model.addAttribute("contests", outputDtoList);
        return "organiser";
    }

    @GetMapping("/filter")
    public String showFilteredOrganiserPage(@RequestParam(value = "filter") String filter, Model model, HttpSession session) {
        List<Contest> contests = contestService.getFilteredContests(filter);
        if (contests == null) {
            return "redirect:/organiser/";
        }
        List<ContestOutputDto> outputDtoList = contests.stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());
        model.addAttribute("contests", outputDtoList);
        return "organiser";

    }

    @GetMapping("/create")
    public String showCreateContestPage(Model model, HttpSession session) {
        List<ContestCategory> categories = contestCategoryService.getAllCategories();
        List<JuryDto> possibleJury = userService.getAllJurors().stream()
                .map(userModelMapper::userToJuryDto).collect(Collectors.toList());
        ContestCreateDto contestCreateDto = new ContestCreateDto();
        model.addAttribute("categories", categories);
        model.addAttribute("juries", possibleJury);
        model.addAttribute("contestCreateDto", contestCreateDto);


        return "contestCreate";
    }

    @PostMapping("/create")
    public String createContest(@Valid @ModelAttribute("contestCreateDto") ContestCreateDto contestCreateDto,
                                BindingResult errors,
                                Model model,
                                HttpSession session) {
        model.addAttribute("loggedUser", (UserOutputDto) session.getAttribute("user"));
        List<ContestCategory> categories = contestCategoryService.getAllCategories();
        List<JuryDto> possibleJury = userService.getAllJurors().stream()
                .map(userModelMapper::userToJuryDto)
                .collect(Collectors.toList());
        int id;
        if (errors.hasErrors()) {
            model.addAttribute("categories", categories);
            model.addAttribute("juries", possibleJury);
            return "contestCreate";
        }
        try {
            Contest newContest = contestModelMapper.dtoToContest(contestCreateDto);
            contestService.create(newContest);
            id = newContest.getId();
        } catch (RuntimeException e) {
            model.addAttribute("categories", categories);
            model.addAttribute("juries", possibleJury);
            model.addAttribute("error", e.getMessage());
            return "contestCreate";
        }
        return "redirect:/contest/organiser/" + id + "/edit/";
    }

    @GetMapping("/users")
    public String getUsersView(Model model, HttpSession session) {
        List<UserOutputDto> junkieList = userService.getAllJunkies().stream()
                .map(userModelMapper::userToDto)
                .sorted(Comparator.comparingInt(UserOutputDto::getScore).reversed())
                .collect(Collectors.toList());
        model.addAttribute("junkies", junkieList);

        return "junkieListView";
    }

    @GetMapping("/category")
    public String getCreateCategoryView(Model model, HttpSession session) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("category", categoryDto);

        return "createCategoryView";
    }

    @PostMapping("/category")
    public String createCategory(@Valid @ModelAttribute("category") CategoryDto category,
                                 BindingResult errors,
                                 Model model,
                                 HttpSession session) {
        if (errors.hasErrors()) {
            return "createCategoryView";
        }
        try {
            contestCategoryService.addCategory(categoryModelMapper.dtoToCategory(category));
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "createCategoryView";
        }
        return "redirect:/organiser";
    }
}

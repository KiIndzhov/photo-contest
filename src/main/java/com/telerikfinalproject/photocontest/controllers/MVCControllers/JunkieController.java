package com.telerikfinalproject.photocontest.controllers.MVCControllers;

import com.telerikfinalproject.photocontest.models.Contest;
import com.telerikfinalproject.photocontest.models.dtomodels.ContestOutputDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.mappers.ContestModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/junkie")
public class JunkieController {

    private final ContestService contestService;
    private final ContestModelMapper contestModelMapper;

    @Autowired
    public JunkieController(ContestService contestService, ContestModelMapper contestModelMapper) {
        this.contestService = contestService;
        this.contestModelMapper = contestModelMapper;
    }

    @GetMapping()
    public String showJunkieHomepage(Model model, HttpSession session) {
        int userId = ((UserOutputDto) session.getAttribute("loggedUser")).getId();

        List<ContestOutputDto> outputDtoList = contestService.getFilteredContestsByUserId("available", userId).stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());

        model.addAttribute("contests", outputDtoList);
        model.addAttribute("canEnroll", true);

        return "junkie";
    }

    @GetMapping("/filter")
    public String showFilteredContests(@RequestParam(value = "filter") String filter,
                                       Model model,
                                       HttpSession session) {

        int userId = ((UserOutputDto) session.getAttribute("loggedUser")).getId();
        List<Contest> contests = contestService.getFilteredContestsByUserId(filter, userId);
        if (contests == null) {
            return "redirect:/junkie/";
        }
        List<ContestOutputDto> outputDtoList = contests.stream()
                .map(contestModelMapper::contestToDto)
                .collect(Collectors.toList());

        model.addAttribute("contests", outputDtoList);
        model.addAttribute("canEnroll", false);

        return "junkie";
    }
}

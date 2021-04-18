package com.telerikfinalproject.photocontest.controllers.MVCControllers;

import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.dtomodels.PhotoCreateDto;
import com.telerikfinalproject.photocontest.models.dtomodels.ReviewOutputDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.services.contracts.ContestService;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.mappers.PhotoModelMapper;
import com.telerikfinalproject.photocontest.services.mappers.ReviewModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/photo")
public class PhotoControllerMVC {

    private final PhotoService photoService;
    private final PhotoModelMapper photoModelMapper;
    private final ReviewModelMapper reviewModelMapper;

    @Autowired
    public PhotoControllerMVC(PhotoService photoService, PhotoModelMapper photoModelMapper, ReviewModelMapper reviewModelMapper) {
                this.photoService = photoService;
        this.photoModelMapper = photoModelMapper;
        this.reviewModelMapper = reviewModelMapper;
    }

    @ModelAttribute("isOrganiser")
    private boolean isOrganiser(HttpSession session){
        UserOutputDto loggedUser = (UserOutputDto) session.getAttribute("loggedUser");
        return loggedUser.getRole().equals("ORGANIZER");
    }

    @GetMapping("/submit/{contestId}")
    public String getSubmitPhotoView(@PathVariable int contestId, Model model, HttpSession session){
        UserOutputDto loggedUser = (UserOutputDto) session.getAttribute("loggedUser");

        if(!photoService.canUserSubmitPhoto(loggedUser.getId(), contestId) || photoService.hasUserSubmitPhotoToContest(loggedUser.getId(), contestId)){
            return "redirect:/";
        }
        PhotoCreateDto submitPhoto = new PhotoCreateDto();
        submitPhoto.setContestId(contestId);
        submitPhoto.setUserId(loggedUser.getId());
        model.addAttribute("photo",submitPhoto);

        return "submitPhotoView";
    }

    @PostMapping("/submit/{contestId}")
    public String submitPhoto(@PathVariable int contestId, @ModelAttribute("photo") PhotoCreateDto photoCreateDto, Model model, HttpSession session, BindingResult errors){
        Photo photo = photoModelMapper.dtoToPhoto(photoCreateDto);
        photoService.createPhoto(photo);

        return "redirect:/contest/" + contestId;
    }

    @GetMapping("{id}")
    public String getPhotoCommentsView(@PathVariable int id,Model model,HttpSession session){
        List<ReviewOutputDto> reviews = photoService.getPhotoReviews(id).stream().map(reviewModelMapper::reviewToOutputDto).collect(Collectors.toList());
        Photo photo = photoService.getPhotoById(id);
        model.addAttribute("photo",photo);
        model.addAttribute("reviews",reviews);

        return "photoView";
    }
}

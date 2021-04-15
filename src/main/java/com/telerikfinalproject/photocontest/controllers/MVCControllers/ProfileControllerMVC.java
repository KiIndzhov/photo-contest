package com.telerikfinalproject.photocontest.controllers.MVCControllers;

import com.telerikfinalproject.photocontest.models.Photo;
import com.telerikfinalproject.photocontest.models.User;
import com.telerikfinalproject.photocontest.models.dtomodels.UserOutputDto;
import com.telerikfinalproject.photocontest.models.dtomodels.UserUpdateDto;
import com.telerikfinalproject.photocontest.models.utils.UserRole;
import com.telerikfinalproject.photocontest.services.contracts.PhotoService;
import com.telerikfinalproject.photocontest.services.contracts.UserService;
import com.telerikfinalproject.photocontest.services.mappers.UserModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileControllerMVC {

    private final UserService userService;
    private final PhotoService photoService;
    private final UserModelMapper userModelMapper;

    @Autowired
    public ProfileControllerMVC(UserService userService,
                                PhotoService photoService,
                                UserModelMapper userModelMapper) {
        this.userService = userService;
        this.photoService = photoService;
        this.userModelMapper = userModelMapper;
    }

    @GetMapping("/{userId}")
    public String viewProfilePage(@PathVariable int userId, Model model, HttpSession session) {
        List<Photo> photoList = photoService.getAllPhotosByUserId(userId);
        UserOutputDto user = userModelMapper.userToDto(userService.getUserById(userId));
        boolean isJunkie = userService.getUserById(userId).getCredential().getUserRole().equals(UserRole.PHOTO_JUNKIE);

        model.addAttribute("user", user);
        model.addAttribute("photoList", photoList);
        model.addAttribute("isJunkie", isJunkie);

        return "profileInfo";
    }

    @GetMapping("/update/{userId}")
    public String updateProfilePage(@PathVariable int userId, Model model, HttpSession session) {
        User user = userService.getUserById(userId);
        UserUpdateDto userDto = userModelMapper.userToUpdateDto(user);

        model.addAttribute("user", userDto);

        return "editProfile";
    }

    @PostMapping("/update/{userId}")
    public String updateProfile(@PathVariable int userId,
                                @Valid @ModelAttribute("user") UserUpdateDto userDto,
                                BindingResult errors,
                                Model model,
                                HttpSession session) {
        if (errors.hasErrors()) {
            return "editProfile";
        }
        try {
            User userToBeUpdated = userModelMapper.userDtoToUser(userDto, userId);
            userService.updateUser(userToBeUpdated);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "createCategoryView";
        }

        return "redirect:/profile/" + userId;
    }
}
